/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import org.json.JSONException;
import org.json.JSONObject;

class CSSKeyframesRule extends CSSRule {
    private final JSONObject declaration;

    CSSKeyframesRule(JSONObject declaration) {
        this.declaration = declaration;
    }

    @Override
    public int getType() {
        return CSSRule.KEYFRAME_RULE;
    }

    Object getAnimation(String animationName) throws JSONException {
        return declaration.get(animationName);
    }
}
