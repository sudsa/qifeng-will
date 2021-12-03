package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;
import java.util.Date;

import com.bpaas.doc.framework.base.command.NotProguard;

@NotProguard
public class Enterprise implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -538385243377636808L;

	public Enterprise(String id, String name, String taxNo, String tel, String address, Date createDate,
			Date modifyDate, String code) {
		super();
		this.id = id;
		this.name = name;
		this.taxNo = taxNo;
		this.tel = tel;
		this.address = address;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.code = code;
	}

	private String id;
	private String name;
	private String taxNo;
	private String tel;
	private String address;
	private Date createDate;
	private Date modifyDate;
	private String code;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTaxNo() {
		return taxNo;
	}

	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return id;
    }
}