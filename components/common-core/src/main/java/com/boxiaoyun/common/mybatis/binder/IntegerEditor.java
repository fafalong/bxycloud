package com.boxiaoyun.common.mybatis.binder;

import org.springframework.beans.propertyeditors.PropertiesEditor;

/**
 * @author LYD
 */
public class IntegerEditor extends PropertiesEditor {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (text == null || text.equals("") || text.equals("off")) {
			text = "0";
		}
		if (text.equals("on")) {
			text = "1";
		}
		setValue(Integer.parseInt(text));
	}

	@Override
	public String getAsText() {
		return getValue().toString();
	}
}
