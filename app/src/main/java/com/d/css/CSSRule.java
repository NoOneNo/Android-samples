/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

abstract class CSSRule {
    static final int STYLE_RULE = 1;
    static final int FONT_FACE_RULE = 2;
    static final int KEYFRAME_RULE = 3;
    static final int MEDIA_RULE = 4;

    abstract public int getType();
}
