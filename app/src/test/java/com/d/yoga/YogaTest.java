package com.d.yoga;

import com.facebook.yoga.YogaNode;
import org.junit.Test;

public class YogaTest {


    @Test
    public void test() {
        YogaNode yogaNode = new YogaNode();
        System.out.println("width:" + yogaNode.getWidth());
        System.out.println("height:" + yogaNode.getHeight());
        System.out.println("Layoutwidth:" + yogaNode.getLayoutWidth());
        System.out.println("Layoutheight:" + yogaNode.getLayoutHeight());
    }
}
