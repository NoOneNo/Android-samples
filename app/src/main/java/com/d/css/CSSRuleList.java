/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import java.util.List;

class CSSRuleList {
    private CSSRule[] cssRules;

    CSSRuleList(List<CSSRule> cssRuleList) {
        cssRules = cssRuleList.toArray(new CSSRule[0]);
    }

    CSSRule item(int index) {
        return cssRules[index];
    }

    int length() {
        return cssRules.length;
    }
}
