 package com.qifeng.will.web.view.response;

import java.io.Serializable;

/**
 * combotree控件的数据
 * 
 * @author huyang
 */
public class ComboTreeData implements Serializable {

	private static final long serialVersionUID = 8664241219014636921L;
	
	private String id;
	private String text;
	private String parent;

    public ComboTreeData() {

    }

	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param id
	 * @param text
	 * @param parent 
	 */
	public ComboTreeData(String id, String text, String parent) {
		super();
		this.id = id;
		this.text = text;
		this.parent = parent;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the parent
	 */
	public String getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(String parent) {
		this.parent = parent;
	}
}
