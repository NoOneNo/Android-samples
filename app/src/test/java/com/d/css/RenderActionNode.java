/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import java.util.Map;

public class RenderActionNode implements Node {
    private String mTagName;
    private RenderActionNode mParent;

    private CSSStyleSheet mCSSStyleSheet;
    private CSSStyleDeclaration mInlineStyle = new CSSStyleDeclaration(null);
    private CSSStyleDeclaration mFinalStyle = new CSSStyleDeclaration(null);

    private String mCSSId = null;
    private String[] mCSSClass = null;

    private boolean mUseParentStyle = false;
    private int mStyleObjectId = -1;

    RenderActionNode(String tagName) {
        mTagName = tagName;
    }

    void setStyleSheet(int styleObjectId, CSSStyleSheet CSSStyleSheet) {
        mStyleObjectId = styleObjectId;
        mCSSStyleSheet = CSSStyleSheet;
    }

    void setUseParentStyle(boolean useParentStyle) {
        mUseParentStyle = useParentStyle;
    }

    void updateCSSAttrs(Map<String, Object> attributes) {
        Object classPlain = attributes.get("class");
        if (classPlain != null) {
            classPlain = ((String)classPlain).replace("@appRootElement", ""); // TODO FixBug @appRootElement
            setCSSClass((String) classPlain);
        }

        Object idPlain = attributes.get("id");
        if (idPlain != null) {
            setCSSId((String) idPlain);
        }
    }

    void updateInlineStyles(CSSStyleDeclaration style) {
        mInlineStyle.setProperty(style);
    }

    private void setCSSId(String idPlain) {
        mCSSId = idPlain.trim();
    }

    private void setCSSClass(String classPlain) {
        mCSSClass = classPlain.trim().split("\\s+");
    }

    CSSStyleDeclaration computeFinalStyleProps() {
        CSSStyleSheet.calFinalStyleProps(this, mFinalStyle, mInlineStyle);
        return mFinalStyle;
    }

    public void setParent(RenderActionNode parent) {
        mParent = parent;
    }

    @Override
    public RenderActionNode getParent() {
        return mParent;
    }

    @Override
    public String getCSSId() {
        return mCSSId;
    }

    @Override
    public String[] getCSSClass() {
        return mCSSClass;
    }

    @Override
    public String getTagName() {
        return mTagName;
    }

    @Override
    public CSSStyleSheet getMatchedStyleSheet() {
        RenderActionNode curr = this;
        if (curr.mUseParentStyle) {
            curr = curr.getParent();
        }
        while (curr != null) {
            if (curr.mCSSStyleSheet != null) {
                return curr.mCSSStyleSheet;
            }
            curr = curr.getParent();
        }
        return null;
    }
}
