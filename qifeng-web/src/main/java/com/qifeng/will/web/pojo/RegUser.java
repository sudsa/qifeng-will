

package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;

/**
 * 注册的用户
 * 
 * @author huyang
 * @date 2015年11月2日
 */
public class RegUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5990164804866230976L;

	private String userType;

    private String username;

    private String password;

    private String name;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
