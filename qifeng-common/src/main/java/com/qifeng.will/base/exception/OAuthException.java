/**  
* @Project: bpaas-framework-base
* @Title: BpaasException.java
* @Package com.bpaas.framework.base.exception
* @Description: TODO
* @author Administrator
* @date 2015年11月19日 下午4:26:59
* @version V1.0
*/
package com.qifeng.will.base.exception;

import com.qifeng.will.base.command.NotProguard;

/**
 * @Description TODO
 * @date 2015年11月19日 
 * @version V1.0 
 * @author Administrator
 */
@NotProguard
public class OAuthException extends Exception {

    /** 
     * @Fields serialVersionUID : TODOR
     */ 
    private static final long serialVersionUID = 1L;

    public OAuthException(){
        super();
    }
    
    public OAuthException(String msg){
        super(msg);
    }
    
    public OAuthException(Throwable throwable){
        super(throwable);
    }
    
    public OAuthException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
