/*
 * Copyright (C) 2019, hapjs.org. All rights reserved.
 */

package com.d.css;

public class CSSValue {
    public final String key;
    public Object value;
    public String state = "";

    CSSValue(String keyAndState, Object value) {
        this.value = value;

        if (keyAndState.contains(":")) {
            int index = keyAndState.indexOf(":");
            key = keyAndState.substring(0, index).intern();
            state = keyAndState.substring(index + 1).intern();
        } else {
            key = keyAndState.intern();
        }
    }

    CSSValue(String key, Object value, String state) {
        this.key = key;
        this.value = value;
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    String getKeyWithState() {
        return key+":"+getState();
    }

    CSSValue toEmpty() {
        return new CSSValue(key, null, state);
    }

    @Override
    public String toString() {
        return key+":"+getState()+":"+value;
    }
}
