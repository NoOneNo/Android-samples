/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class CSSStyleRule extends CSSRule{
    private static final String TAG = "CSSStyleRule";

    private final CSSStyleSheet mOwner;
    private final Selector selector;
    final CSSStyleDeclaration props;


    CSSStyleRule(CSSStyleSheet ss, String selectorsPlain, JSONObject stylePropsPlain) throws JSONException {
        mOwner = ss;
        selector = Selector.parse(selectorsPlain);
        props = new CSSStyleDeclaration(this, stylePropsPlain);
    }

    @Override
    public int getType() {
        return CSSRule.STYLE_RULE;
    }

    long getScore(Node node){
        return selector.getScore(node);
    }

    boolean match(Node node){
        return selector.match(node);
    }

    void setOrder(long order) {
        selector.setOrder(order);
    }

    void compute() throws JSONException {
        props.compute(mOwner);
    }

    /**
     * Selector
     * */

    private static abstract class Selector {
        private long mScore = 0L;

        void setOrder(Long order) {
            mScore = calScore() * 1000000L + order;
        }

        abstract long calScore();

        private long getScore() {
            return mScore;
        }

        long getScore(Node node) {
            if (match(node)) {
                return getScore();
            }
            return 0;
        }

        abstract boolean match(Node node);

        private static Selector parse(String plain) {
            plain = plain.trim();

            if (plain.contains(",")) {
                String[] selectorPlains = plain.split(",");
                Selector[] selectors = new Selector[selectorPlains.length];
                for (int i = 0; i < selectorPlains.length; i++) {
                    selectors[i] = parse(selectorPlains[i]);
                }
                return new MultiSelector(selectors);
            }

            if (plain.contains(">")) {
                String[] subSelectorNames = plain.split(">", 2);
                if (subSelectorNames.length != 2) {
                    Log.w(TAG, "invalid selector:" + plain);
                    return new TagSelector(plain);
                }
                Selector parent = parse(subSelectorNames[0]);
                Selector child = parse(subSelectorNames[1]);
                return new ChildSelector(parent, child);
            }

            if (plain.matches(".*\\s+.*")) {
                String[] subSelectorPlains = plain.split("\\s+");
                Selector[] subSelectors = new Selector[subSelectorPlains.length];
                for (int i = 0; i < subSelectorPlains.length; i++) {
                    subSelectors[i] = parse(subSelectorPlains[i]);
                }
                return new DescendantSelector(subSelectors);
            }

            if (plain.startsWith("#")) {
                return new IdSelector(plain.substring(1));
            }
            if (plain.startsWith(".")) {
                return new ClassSelector(plain.substring(1));
            }
            return new TagSelector(plain);
        }
    }

    /**
     * [#id, #id2]
     * */
    static class MultiSelector extends Selector {
        private Selector[] selectors;

        MultiSelector(Selector[] selectors) {
            this.selectors = selectors;
        }

        @Override
        void setOrder(Long order) {
            for (Selector selector:selectors) {
                selector.setOrder(order);
            }
        }

        @Override
        long calScore() {
            throw new IllegalStateException("MultiSelector");
        }

        long getScore(Node node) {
            long maxScore = 0;
            for (Selector selector:selectors) {
                if (selector.getScore(node) > maxScore) {
                    maxScore = selector.getScore(node);
                }
            }
            return maxScore;
        }

        @Override
        public boolean match(Node node) {
            for (Selector selector:selectors) {
                if (selector.match(node)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * [#id]
     * */
    static class IdSelector extends Selector {
        private final String mId;

        IdSelector(String id) {
            mId = id;
        }

        @Override
        long calScore() {
            return 1000000L;
        }

        @Override
        boolean match(Node node) {
            return mId.equals(node.getCSSId());
        }

        @Override
        public String toString() {
            return "id:"+mId;
        }
    }

    /**
     * [.cls]
     * */
    static class ClassSelector extends Selector {
        private final String mCls;

        ClassSelector(String cls) {
            mCls = cls;
        }

        @Override
        long calScore() {
            return 1000L;
        }

        @Override
        boolean match(Node node) {
            return arrayContains(node.getCSSClass(), mCls);
        }

        boolean arrayContains(String[] a, String s){
            if (a == null) {
                return false;
            }
            for (String item:a) {
                if (s.equals(item)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "class:"+mCls;
        }
    }

    /**
     * [text]
     * */
    static class TagSelector extends Selector {
        private final String mTag;

        TagSelector(String tag) {
            mTag = tag;
        }

        @Override
        long calScore() {
            return 1L;
        }

        @Override
        public boolean match(Node node) {
            return mTag.equals(node.getTagName());
        }

        @Override
        public String toString() {
            return "tag:"+mTag;
        }
    }

    /**
     * [div > text]
     * */
    static class ChildSelector extends Selector {
        private Selector mParent;
        private Selector mChild;

        ChildSelector(Selector parent, Selector child) {
            mParent = parent;
            mChild = child;
        }

        @Override
        long calScore() {
            return mParent.calScore() + mChild.calScore();
        }

        @Override
        public boolean match(Node node) {
            return mChild.match(node) && mParent.match(node.getParent());
        }
    }

    /**
     * [div text]
     * */
    static class DescendantSelector extends Selector {
        private Selector[] mSelectors;

        DescendantSelector(Selector[] selectors) {
            mSelectors = selectors;
        }

        @Override
        long calScore() {
            long score = 0L;
            for (Selector s:mSelectors) {
                score += s.calScore();
            }
            return score;
        }

        @Override
        public boolean match(Node node) {
            boolean endMatch = mSelectors[mSelectors.length-1].match(node);
            if (!endMatch) {
                return false;
            }

            return descMatch(mSelectors, node.getParent(), mSelectors.length-1);
        }

        private boolean descMatch(Selector[] selectors, Node node, int length) {
            if (length == 0) {
                return true;
            }

            if (node == null) {
                return false;
            }

            boolean endMatch = selectors[length-1].match(node);
            if (endMatch) {
                return descMatch(selectors, node.getParent(), length-1);
            } else {
                return descMatch(selectors, node.getParent(), length);
            }
        }
    }
}
