package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;

public class Menu implements Comparable<Menu>, Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 7032476421212192597L;
	/** 
     * <p>Title: </p> 
     * <p>Description: </p> 
     * @param id
     * @param pid 父节点ID，type为父节点时:00000000000000000000000000000000
     * @param domain 所属系统域，如OIM
     * @param name 菜单名称
     * @param type 菜单类型 00-父节点 01-子节点 02-动作
     * @param sort 排序，从1开始
     * @param icon 图标样式
     * @param url 父节点为空，子节点为controller路径，如ucsDomain/show，动作为shiro权限，如ucsPermission:add
     * @param description 描述，可空
     * @param status 状态，状态 00：禁用 01：启用 02：激活 03：删除
     */
    public Menu(String id, String pid, String domain, String name,
            String type, Integer sort, String icon, String url,
            String description, String status) {
        super();
        this.id = id;
        this.pid = pid;
        this.domain = domain;
        this.name = name;
        this.type = type;
        this.sort = sort;
        this.icon = icon;
        this.url = url;
        this.description = description;
        this.status = status;
    }
    
    private String id;
    private String pid;
    private String domain;
    private String name;
    private String type;
    private Integer sort;
    private String icon;
    private String url;
    private String description;
    private String status;
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
     * @return the pid
     */
    public String getPid() {
        return pid;
    }
    /**
     * @param pid the pid to set
     */
    public void setPid(String pid) {
        this.pid = pid;
    }
    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }
    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
    /**
     * @return the sort
     */
    public Integer getSort() {
        return sort;
    }
    /**
     * @param sort the sort to set
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }
    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
	/** 
	 * <p>Title: compareTo</p> 
	 * <p>Description: </p> 
	 * @param o
	 * @return 
	 * @see java.lang.Comparable#compareTo(java.lang.Object) 
	 */
	@Override
	public int compareTo(Menu menu) {
		return this.sort - menu.sort;
	}
}