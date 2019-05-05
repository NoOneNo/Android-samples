/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

public interface Node {
    Node getParent();
    String getCSSId();
    String[] getCSSClass();
    String getTagName();
    CSSStyleSheet getMatchedStyleSheet();
}
