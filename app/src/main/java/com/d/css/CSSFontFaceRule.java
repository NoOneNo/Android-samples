/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import android.text.TextUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class CSSFontFaceRule extends CSSRule {
    private String[] fontNames;
    private JSONObject[] fontFaces;

    CSSFontFaceRule(JSONObject declaration) throws JSONException {
        parse(declaration);
    }

    private void parse(JSONObject declaration) throws JSONException {
        fontNames = new String[declaration.length()];
        fontFaces = new JSONObject[declaration.length()];

        for (int i = 0; i < declaration.length(); i++) {
            fontNames[i] = declaration.names().getString(i);
            fontFaces[i] = declaration.getJSONObject(fontNames[i]);
        }
    }

    @Override
    public int getType() {
        return CSSRule.FONT_FACE_RULE;
    }


    Object getFontFaces(String fontFamily) throws JSONException {
        JSONArray result = new JSONArray();
        String[] fontFamilies = fontFamily.trim().split(",");
        for (String item : fontFamilies) {
            item = item.trim();
            if (!TextUtils.isEmpty(item)) {

                int index = -1;
                for (int i=0;i<fontNames.length;i++) {
                    if (fontNames[i].equals(item)) {
                        index = i;
                        break;
                    }
                }

                if (index >= 0) {
                    result.put(fontFaces[index]);
                } else {
                    JSONObject font =  new JSONObject();
                    font.put("fontName", item);
                    result.put(font);
                }
            }
        }
        return result;
    }
}
