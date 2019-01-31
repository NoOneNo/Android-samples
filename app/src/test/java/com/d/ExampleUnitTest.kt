package com.d

import com.d.alg.Document
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    var simple1 = """ {
                          '#idTest1': {
                            'ca1': 'ca1-id-1'
                          },
                          '.class-test1': {
                            'ca1': 'ca1-class-1'
                          },
                          '.class-test2': {
                            'ca1': 'ca1-class-2'
                          },
                          div: {
                            'ca1': 'ca1-tag-1'
                          }
                        } """

    @Test
    fun css() {
        val document = Document()

        // 定义样式节点
        document.addStyleSheet(simple1)

        // 节点绑定
        val node = document.createElement("div")
        document.appendChild(node)

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-tag-1")

        // 设置class
        node.setAttr("class", " class-test1 ")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-class-1")

        // 设置class
        node.setAttr("class", "class-test2 class-test1")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-class-2")

        // 设置id
        node.setAttr("id", "idTest1")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-id-1")

        // 设置style
        node.setStyleProp("ca1", "")

        assertEquals(node.styleProps.size, 1)
        assertFalse(node.styleProps["ca1"].toString().contains("ca1-"))

        // 设置style
        node.setStyleProp("ca1", null)

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "")

        // 删除样式中的CSS属性：ca1，并更新内联样式
        node.inlineStyleProps.clear()
        node.setStyleProp("", null)

        // 设置id
        node.setAttr("id", "")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-class-2")

        // 设置class
        node.setAttr("class", "class-test2")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-class-2")

        // 设置class
        node.setAttr("class", "class-test2 class-test1")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-class-2")

        // 设置class
        node.setAttr("class", "")

        assertEquals(node.styleProps.size, 1)
        assertEquals(node.styleProps["ca1"], "ca1-tag-1")
    }

    var desc1 = """{
  '#idTest1': {
    'ca1': 'ca1-desc-idTest1-1'
  },
  '.class-test1': {
    'ca1': 'ca1-desc-class-test1-1'
  },
  'div': {
    'ca1': 'ca1-desc-div-1'
  },
  'div text': {
    'ca1': 'ca1-desc-div-text-1'
  },
  '.doc-page #idTest1': {
    'ca1': 'ca1-desc-doc-page-idTest1-1'
  },
  '.doc-page .class-test1': {
    'ca1': 'ca1-desc-doc-page-class-test1-1'
  }
}"""

    fun cssDesc() {
        val document = Document()

        // 定义样式节点
        document.addStyleSheet(desc1)

        // 节点绑定
        val nodeParent1 = document.createElement("div")
        document.appendChild(nodeParent1)

        assertEquals(nodeParent1.styleProps.size, 1)
        assertEquals(nodeParent1.styleProps["ca1"], "ca1-desc-div-1")

        // 新增元素
        val nodeText1 = document.createElement("text")
        nodeParent1.appendChild(nodeText1)

        assertEquals(nodeText1.styleProps.size, 1)
        assertEquals(nodeText1.styleProps["ca1"], "ca1-desc-div-text-1")

        // 设置class
        nodeText1.setAttr("class", "class-test1")

        assertEquals(nodeText1.styleProps.size, 1)
        assertEquals(nodeText1.styleProps["ca1"], "ca1-desc-doc-page-class-test1-1")
    }
}
