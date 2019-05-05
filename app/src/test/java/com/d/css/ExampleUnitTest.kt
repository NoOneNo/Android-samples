package com.d.css

import com.d.alg.Document
import org.json.JSONObject
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
        val document = RenderActionNode("body")
        document.setStyleSheet(1, CSSStyleSheet.build(JSONObject(simple1)))

        // 节点绑定
        val node = RenderActionNode("div")
        node.parent = document

        val dec = node.computeFinalStyleProps()
        assertEquals(dec.length, 1)
        assertEquals(dec.getPropertyCSSValue("ca1").value, "ca1-tag-1")
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

//    fun cssDesc() {
//        val document = RenderActionNode("body")
//
//        // 定义样式节点
//        document.styleSheet = StyleSheet.parse(JSONObject(desc1));
//
//        // 节点绑定
//        val nodeParent1 = RenderActionNode("div")
////        document.appendChild(node)
//        nodeParent1.setParent(document);
//
//        assertEquals(nodeParent1.finalStyleProps.size, 1)
//        assertEquals(nodeParent1.finalStyleProps["ca1"], "ca1-desc-div-1")
//
//        // 新增元素
//        val nodeText1 = RenderActionNode("text")
//        nodeParent1.appendChild(nodeText1)
//
//        assertEquals(nodeText1.finalStyleProps.size, 1)
//        assertEquals(nodeText1.finalStyleProps["ca1"], "ca1-desc-div-text-1")
//
//        // 设置class
//        nodeText1.setClass("class-test1")
//
//        assertEquals(nodeText1.finalStyleProps.size, 1)
//        assertEquals(nodeText1.finalStyleProps["ca1"], "ca1-desc-doc-page-class-test1-1")
//    }
}
