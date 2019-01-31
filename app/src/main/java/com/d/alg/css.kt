package com.d.alg

import org.json.JSONObject

// dom树构建
// 选择器解析
// 优先级计算

// 选择器
// tag, id, class, 后代, 直接后代, 多个选择, 伪类

// styleSheet 可能会被多个文件引用, 所以可以cache或者引用

// 计算当前节点, 计算后代节点

fun parseProp(props:JSONObject): Map<String, Any> {
    val map = HashMap<String, Any>()
    val animationName = props.optString("animationName")
    if(animationName != null) {
        //
        props.remove("animationName")
    }

    val fontFamily = props.optString("fontFamily")
    if(fontFamily != null) {
        //
        props.remove("fontFamily")
    }

    val keys = props.keys()
    keys.forEach {
        map[it] = props.get(it)
    }

    return map
}

fun isDescendantSelector(selectorName:String):Boolean {
    return selectorName.trim().contains(Regex("\\s+"))
}

fun isChildSelector(selectorName:String):Boolean {
    return selectorName.contains(">")
}

fun parseSelector(selectorName:String):Selector {
    if (isChildSelector(selectorName)) {
        val subSelectorNames = selectorName.trim().split(">")
        val subSelectors = arrayListOf<Selector>()
        subSelectorNames.forEach {
            subSelectors.add(parseSelector(it))
        }
        return ChildSelector(subSelectors[0], subSelectors[1])
    }

    if (isDescendantSelector(selectorName)) {
        val subSelectorNames = selectorName.trim().split(Regex("\\s+"))
        val subSelectors = arrayListOf<Selector>()
        subSelectorNames.forEach {
            subSelectors.add(parseSelector(it))
        }
        return DescendantSelector(subSelectors)
    }

    val tagAndPseudo = selectorName.trim().split(":")
    val tag = tagAndPseudo[0]
    val pseudo = if (tagAndPseudo.size > 1) {tagAndPseudo[1]} else{""}
    return SimpleSelector(tag, pseudo)
}

abstract class Selector {
    var score:Long = 0

    open fun calScore(order: Long) {
        score = calScore()*1000000L + order
    }

    abstract fun calScore():Long

    abstract fun match(node: Node?):Boolean
}

class ChildSelector(var parent:Selector, var child: Selector) : Selector() {

    override fun calScore():Long {
        var score = 0L
        score += parent.calScore()
        score += child.calScore()
        return score
    }

    override fun match(node: Node?): Boolean {
        return child.match(node) && parent.match(node!!.parent)
    }
}

class DescendantSelector(var selectors:ArrayList<Selector>)  : Selector() {

    override fun calScore():Long {
        var score = 0L
        selectors.forEach {
            score += it.calScore()
        }
        return score
    }

    override fun match(node: Node?): Boolean {
        val endMatch = selectors[selectors.size-1].match(node)
        if (!endMatch) {
            return false
        }

        return descMatch(selectors, node!!.parent, selectors.size-1)
    }

    private fun descMatch(selectors: ArrayList<Selector>, node: Node?, length:Int):Boolean {
        if (length == 0) {
            return true
        }

        if (node == null) {
            return false
        }

        val endMatch = selectors[length-1].match(node)
        return if (endMatch) {
            descMatch(selectors, node.parent, length-1)
        } else {
            descMatch(selectors, node.parent, length)
        }
    }
}

class SimpleSelector(private var tag:String, var booleanPseudo:String) : Selector() {
    private var isId = false
    private var isClass = false
    private var tagNoPrefix = tag

    init {
        tag = tag.trim()
        if (tag.startsWith("#")) {
            isId = true
            tagNoPrefix = tag.substring(1)
        } else if (tag.startsWith(".")) {
            isClass = true
            tagNoPrefix = tag.substring(1)
        }
    }

    override fun calScore():Long {
        return when {
            isId -> 1000000L
            isClass -> 1000L
            else -> 1
        }
    }

    override fun match(node: Node?): Boolean {
        val tagMatched = when {
            node == null -> false
            isId -> tagNoPrefix == node.id
            isClass -> node.classes.contains(tagNoPrefix)
            else -> tagNoPrefix == node.type
        }

        if (booleanPseudo.isEmpty()) {
            return tagMatched
        }

        return if (tagMatched) {
            node!!.getBooleanAttr(booleanPseudo)
        } else {
            false
        }
    }

    override fun toString(): String {
        return tag
    }
}

class InlineSelector(var node: Node) : Selector() {
    override fun calScore():Long {
        return 1000000000L
    }

    override fun match(node: Node?): Boolean {
        return this.node == node
    }
}

data class Style(var selector:Selector, var prop:Map<String, Any>)

var sStyleSheetCount = 0L

class StyleSheet(json: String) {
    var styles = arrayListOf<Style>()

    init {
        sStyleSheetCount++
        var index = 0
        val jsonObj = JSONObject(json)
        val keys = jsonObj.keys()
        keys.forEach { key ->
            if (key == "@KEYFRAMES") {
                return@forEach
            }
            val prop = parseProp(jsonObj.getJSONObject(key))
            val subSelector = key.trim().split(",")
            subSelector.forEach {
                val s = parseSelector(it)
                s.calScore(sStyleSheetCount + index++)
                val style = Style(s, prop)
                styles.add(style)
            }
        }
    }
}

enum class StyleType(var id:Int) {
    INLINE(0),
    ID(1),
    CLASS(2),
    TAG(3)
}

open class Node(var type:String) {
    var id = ""
    var classes:List<String> = arrayListOf()

    var attributes = HashMap<String, Any>()
    var styleSheet:StyleSheet? = null
    var styleProps = HashMap<String, Any>()
    var inlineStyleProps = HashMap<String, Any>()

    var document: Document? = null
    var parent:Node? = null
    var children = arrayListOf<Node>()

    fun setStyleSheet(json:String) {
        styleSheet = StyleSheet(json)
    }

    fun setAttr(key: String, value:Any) {
        attributes[key] = value
        if (key == "id") {
            id = value as String
            calStyleProps()
        } else if (key == "class") {
            classes = (value as String).trim().split(Regex("\\s+"))
            calStyleProps()
        }
    }

    fun setStyleProp(key: String, value:Any?) {
        if (key.isEmpty()) {
            return
        }
        inlineStyleProps[key] = value ?: ""
        styleProps[key] = value ?: ""
    }

    fun appendChild(node: Node) {
        children.add(node)
        node.assignParent(this)
    }

    private fun assignParent(node: Node) {
        parent = node
        document = node.document
        calStyleProps()

        children.forEach {
            it.assignParent(this)
        }
    }

    fun getBooleanAttr(key:String):Boolean {
        return if (attributes.contains(key)) {
            attributes[key] as Boolean
        } else {
            false
        }
    }

    private fun calStyleProps() {
        val styles = getMatchedStyles()
        styles.sortBy { it.selector.score }
        styleProps.clear()
        styles.forEach {
            styleProps.putAll(it.prop)
        }
        styleProps.putAll(inlineStyleProps)
    }

    private fun getMatchedStyles():ArrayList<Style> {
        val styles = arrayListOf<Style>()
        val styleSheets = getNodeStyleSheetList()
        styleSheets.forEach { stylesheet ->
            stylesheet.styles.forEach{
                if (it.selector.match(this)) {
                    styles.add(it)
                }
            }
        }
        return styles
    }

    private fun getNodeStyleSheetList():ArrayList<StyleSheet> {
        val styleSheets = arrayListOf<StyleSheet>()
        if (document != null) {
            styleSheets.addAll(document!!.styleSheets)
        }

        var cParent = parent
        while (cParent != null) {
            if (cParent.styleSheet != null) {
                styleSheets.add(cParent.styleSheet!!)
            }
            cParent = cParent.parent
        }
        return styleSheets
    }
}

class Document : Node("") {
    var styleSheets = arrayListOf<StyleSheet>()

    init {
        document = this
    }

    fun addStyleSheet(json:String) {
        styleSheets.add(StyleSheet(json))
    }

    fun createElement(type:String):Node {
        return Node(type)
    }
}

















