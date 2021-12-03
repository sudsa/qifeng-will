package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;
import java.util.Date;

import com.bpaas.doc.framework.base.command.NotProguard;

@NotProguard
public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8947715428421280193L;

	public Organization(String id, String name, String pid, String enterpriseId, String status, Date createDate,
			Date modifyDate, String orgType) {
		super();
		this.id = id;
		this.name = name;
		this.pid = pid;
		this.enterpriseId = enterpriseId;
		this.status = status;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.orgType = orgType;
	}

	private String id;
	private String name;
	private String pid;
	private String enterpriseId;
	private String status;
	private Date createDate;
	private Date modifyDate;
	private String orgType;

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(String enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return id;
    }
}