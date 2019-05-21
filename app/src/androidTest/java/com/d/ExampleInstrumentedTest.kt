package com.d

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.facebook.soloader.SoLoader
import com.facebook.yoga.YogaConfig
import com.facebook.yoga.YogaConstants
import com.facebook.yoga.YogaNode

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.d", appContext.packageName)
    }

    @Test
    fun yoga() {

        val appContext = InstrumentationRegistry.getTargetContext()
        SoLoader.init(appContext, false);

        val yogaNode = YogaNode()
        println("width:" + yogaNode.width.unit)
        println("width:" + yogaNode.width.value)

        yogaNode.setWidth(YogaConstants.UNDEFINED)
        yogaNode.setWidth(100f)

        println("width 100:" + yogaNode.width.unit)
        println("width 100:" + yogaNode.width.value)

        yogaNode.setWidth(YogaConstants.UNDEFINED)
        println("width UNDEFINED:" + yogaNode.width.unit)
        println("width UNDEFINED:" + yogaNode.width.value)

        yogaNode.setWidth(100f)
        yogaNode.setWidthAuto()
        println("setWidthAuto:" + yogaNode.width.unit)
        println("setWidthAuto:" + yogaNode.width.value)


        println("flexGrow:" + yogaNode.flexGrow)
        println("flexShrink:" + yogaNode.flexShrink)
        println("flexBasis:" + yogaNode.flexBasis.unit)
        println("flexBasis:" + yogaNode.flexBasis.value)

        yogaNode.setFlex(10f)
        println("flexGrow:" + yogaNode.flexGrow)
        println("flexShrink:" + yogaNode.flexShrink)
        println("flexBasis:" + yogaNode.flexBasis.unit)
        println("flexBasis:" + yogaNode.flexBasis.value)

        yogaNode.flexGrow = 10f
        println("flexGrow:" + yogaNode.flexGrow)
        println("flexShrink:" + yogaNode.flexShrink)
        println("flexBasis:" + yogaNode.flexBasis.unit)
        println("flexBasis:" + yogaNode.flexBasis.value)

        val child = YogaNode()
        yogaNode.addChildAt(child, 0)

        child.setFlex(10f)
        println("flexGrow:" + child.flexGrow)
        println("flexShrink:" + child.flexShrink)
        println("flexBasis:" + child.flexBasis.unit)
        println("flexBasis:" + child.flexBasis.value)

        yogaNode.setWidth(100f)
        yogaNode.calculateLayout(200f, 200f)
        println("layoutWidth:" + yogaNode.layoutWidth)
        println("layoutHeight:" + yogaNode.layoutHeight)


    }
}
