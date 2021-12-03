

package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
 */
public class ShiroUser implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4404447304936520901L;

    public String id;

    public String loginName;

    public String name;

	public List<Menu> menus;

	public Enterprise ent;

	public Organization org;

    public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

    public ShiroUser(String id, String loginName, String name) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
    }

    public ShiroUser(String id, String loginName, String name, List<Menu> menus) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.menus = menus;
    }

    public ShiroUser(String id, String loginName, String name, List<Menu> menus, Enterprise ent, Organization org) {
        this.id = id;
        this.loginName = loginName;
        this.name = name;
        this.menus = menus;
        this.ent = ent;
        this.org = org;
    }

    /**
     * 获取用户ID
     */
    public String getId() {
        return id;
    }

    /**
     * 获取用户名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取帐号
     */
    public String getLoginName() {
        return loginName;
    }

    /**
     * 获取菜单
     */
    public List<Menu> getMenus() {
        return menus;
    }

    /**
     * 获取企业
     */
    public Enterprise getEnt() {
        return ent;
    }

    /**
     * 获取组织
     */
    public Organization getOrg() {
        return org;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return loginName;
    }

    /**
     * 重载hashCode,只计算loginName;
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(loginName);
    }

    /**
     * 重载equals,只计算loginName;
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ShiroUser other = (ShiroUser) obj;
        if (loginName == null) {
            if (other.loginName != null) {
                return false;
            }
        } else if (!loginName.equals(other.loginName)) {
            return false;
        }
        return true;
    }
}
