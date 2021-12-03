package com.qifeng.will.web.view.response;

public class TokenResponse extends BaseResponse {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2798158617850320727L;


	public TokenResponse(boolean success) {
        super(success);
    }
    
    public TokenResponse(boolean success, String msg){
        super(success, msg);
    }
    
    public TokenResponse(String username, String role,String token){
        super(true);
        this.username = username;
        this.token = token;
        this.role = role;
    }
    
    
    
    private String token;
    
    private String username;
    
    private String role;
    

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}