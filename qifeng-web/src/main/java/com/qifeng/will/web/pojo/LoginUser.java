

package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;

public class LoginUser implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5450865433477510354L;

	private String id;

    private String username;

    private String password;

    private String salt;

    private String name;

    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
