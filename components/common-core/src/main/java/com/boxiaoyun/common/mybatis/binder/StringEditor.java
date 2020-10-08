package com.boxiaoyun.common.mybatis.binder;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * @author LYD
 */
public class StringEditor extends PropertiesEditor {
    @Override
    public String getAsText() {
        return getValue().toString();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text.equals("off")) {
            text = "0";
            setValue(Integer.parseInt(text));
        } else if (text.equals("on")) {
            text = "1";
            setValue(Integer.parseInt(text));
        } else {
            setValue(text);
        }
    }
}
