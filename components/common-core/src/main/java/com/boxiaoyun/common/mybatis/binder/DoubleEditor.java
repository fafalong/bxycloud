package com.boxiaoyun.common.mybatis.binder;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * @author LYD
 */
public class DoubleEditor extends PropertiesEditor {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.equals("")) {
            text = "-1";
        }
        setValue(Double.parseDouble(text));
    }

    @Override
    public String getAsText() {
        return getValue().toString();
    }
}
