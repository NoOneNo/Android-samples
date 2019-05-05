/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;


import android.support.v4.util.ArrayMap;
import android.support.v4.util.SimpleArrayMap;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;

public class CSSStyleDeclaration {
    private ArrayMap<String, CSSValue> mProps = new ArrayMap<String, CSSValue>();
    private final CSSRule mOwner;

    public CSSStyleDeclaration(CSSRule cssRule) {
        mOwner = cssRule;
    }

    CSSStyleDeclaration(CSSRule cssRule, JSONObject declaration) throws JSONException {
        mOwner = cssRule;
        parseStyleProps(declaration);
    }

    private void parseStyleProps(JSONObject declaration) throws JSONException {
        Iterator<String> keys = declaration.keys();
        while (keys.hasNext()){
            String key = keys.next();
            if (key.startsWith("_")) { // _meta
                continue;
            }

            CSSValue prop = new CSSValue(key, declaration.get(key));
            setPropertyCSSValue(prop.getKeyWithState(), prop);
        }
    }

    CSSRule getOwnerRule() {
        return mOwner;
    }

    int getLength(){
        return mProps.size();
    }

    CSSValue item(int index) {
        return mProps.valueAt(index);
    }


    void setPropertyCSSValue(String propertyName, CSSValue value) {
        mProps.put(propertyName, value);
    }

    CSSValue getPropertyCSSValue(String propertyName) {
        return mProps.get(propertyName);
    }

    void removeProperty(String propertyName) {
        mProps.remove(propertyName);
    }



    public void setProperty(CSSStyleDeclaration cssStyleDeclaration) {
        mProps.putAll((SimpleArrayMap<? extends String, ? extends CSSValue>) cssStyleDeclaration.mProps);
    }

    void compute(CSSStyleSheet ss) throws JSONException {
        for (int i = 0; i < getLength(); i++) {
            CSSValue item = item(i);

            if (item.key.equals("animationName") && ss.getCSSKeyframesRule() != null) {
                Object o = ss.getCSSKeyframesRule().getAnimation((String) item.value);

                CSSValue cssValue = new CSSValue("animationKeyframes", o, item.state);
                setPropertyCSSValue(cssValue.getKeyWithState(), cssValue);
                continue;
            }

            if (item.key.equals("fontFamily") && ss.getCSSFontFaceRule() != null) {
                Object o = ss.getCSSFontFaceRule().getFontFaces((String) item.value);

                CSSValue cssValue = new CSSValue("fontFamilyDesc", o, item.state);
                setPropertyCSSValue(cssValue.getKeyWithState(), cssValue);
            }
        }
    }

    public Map<String, Map<String, Object>> convertStyleProps() {
        Map<String, Map<String, Object>> props2 = new ArrayMap<>();
        for (CSSValue item : mProps.values()) {
            Map<String, Object> valueMap = props2.get(item.key);
            if (valueMap == null) {
                valueMap = new ArrayMap<>();
                props2.put(item.key, valueMap);
            }
            valueMap.put(item.getState(), item.value);
        }
        return props2;
    }
}
