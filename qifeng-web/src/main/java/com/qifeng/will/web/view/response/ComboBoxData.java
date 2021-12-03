package com.qifeng.will.web.view.response;

import java.io.Serializable;

/**
 * combobox控件的数据
 * 
 * @author huyang
 */
public class ComboBoxData implements Serializable {
	private static final long serialVersionUID = -2060169548419736190L;

	private String valueField;

    private String textField;

    public ComboBoxData() {

    }

    public ComboBoxData(String valueField, String textField) {
        this.valueField = valueField;
        this.textField = textField;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public String getTextField() {
        return textField;
    }

    public void setTextField(String textField) {
        this.textField = textField;
    }
}
