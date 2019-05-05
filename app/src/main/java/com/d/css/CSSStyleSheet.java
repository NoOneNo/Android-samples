/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

// TODO move to jar
public class CSSStyleSheet {
    private static final String TAG = "CSSStyleSheet";
    private static long sStyleSheetCount = 0L;
    private CSSRuleList mCSSRules;
    private CSSKeyframesRule mCSSKeyframesRule;
    private CSSFontFaceRule mCSSFontFaceRule;

    private CSSStyleSheet(JSONObject plain) throws JSONException {
        parseCSSRules(plain);

        for (int i = 0; i < mCSSRules.length(); i++) {
            if (mCSSRules.item(i).getType() == CSSRule.STYLE_RULE) {
                ((CSSStyleRule)mCSSRules.item(i)).compute();
            }
        }
    }

    private void parseCSSRules(JSONObject plain) throws JSONException {
        sStyleSheetCount++;

        List<CSSRule> cssRules = new ArrayList<>();
        Iterator<String> keys = plain.keys();
        int order = 0;
        while (keys.hasNext()){
            String key = keys.next();

            if (key.equals("@KEYFRAMES")) {
                mCSSKeyframesRule = new CSSKeyframesRule(plain.getJSONObject(key));
                continue;
            }

            if (key.equals("@FONT-FACE")) {
                mCSSFontFaceRule = new CSSFontFaceRule(plain.getJSONObject(key));
                continue;
            }

            if (key.startsWith("@")) { // @KEYFRAMES, @FONT-FACE ...
                continue;
            }

            CSSStyleRule cssRule = new CSSStyleRule(this, key, plain.getJSONObject(key));
            cssRule.setOrder(sStyleSheetCount + order++);
            cssRules.add(cssRule);
        }

        mCSSRules = new CSSRuleList(cssRules);
    }

    public static CSSStyleSheet build(JSONObject plain) throws JSONException {
        return new CSSStyleSheet(plain);
    }

    CSSFontFaceRule getCSSFontFaceRule() {
        return mCSSFontFaceRule;
    }

    CSSKeyframesRule getCSSKeyframesRule() {
        return mCSSKeyframesRule;
    }



    public static void calFinalStyleProps(Node node, CSSStyleDeclaration finalStyle, CSSStyleDeclaration inlineStyle) {
        // change to empty value to override old value
        for (int i = 0; i < finalStyle.getLength(); i++) {
            CSSValue old = finalStyle.item(i);
            finalStyle.setPropertyCSSValue(old.getKeyWithState(), old.toEmpty());
        }

        List<CSSStyleRule> CSSRules = getMatchedStyles(node);
        if (CSSRules != null) {
            for (CSSStyleRule CSSRule : CSSRules) {
                finalStyle.setProperty(CSSRule.props);
            }
        }

        finalStyle.setProperty(inlineStyle);
    }

    public static CSSStyleDeclaration parseInlineStyle(Node node, JSONObject styleDeclaration) throws JSONException {
        CSSStyleSheet ss = node.getMatchedStyleSheet();
        CSSStyleDeclaration declaration = new CSSStyleDeclaration(null, styleDeclaration);
        declaration.compute(ss); // TODO better place
        return declaration;
    }

    private static List<CSSStyleRule> getMatchedStyles(final Node node) {
        CSSStyleSheet ss = node.getMatchedStyleSheet();
        if (ss == null) {
            return null;
        }

        List<CSSStyleRule> cssStyleRules = new ArrayList<>();
        for (int i = 0; i < ss.mCSSRules.length(); i++) {
            if (ss.mCSSRules.item(i).getType() == CSSRule.STYLE_RULE) {
                CSSStyleRule cssRule = (CSSStyleRule) ss.mCSSRules.item(i);
                if (cssRule.match(node)) {
                    cssStyleRules.add(cssRule);
                }
            }
        }

        Collections.sort(cssStyleRules, new Comparator<CSSStyleRule>() {
            @Override
            public int compare(CSSStyleRule o1, CSSStyleRule o2) {
                return (int) (o1.getScore(node) - o2.getScore(node));
            }
        });

        return cssStyleRules;
    }
}
