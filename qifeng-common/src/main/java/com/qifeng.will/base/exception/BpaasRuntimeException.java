/**  
* @Project: bpaas-framework-base
* @Title: BpaasException.java
* @Package com.bpaas.doc.framework.base.exception
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
public class BpaasRuntimeException extends RuntimeException {

    /** 
     * @Fields serialVersionUID : TODO
     */ 
    private static final long serialVersionUID = 1L;

    public BpaasRuntimeException(){
        super();
    }
    
    public BpaasRuntimeException(String msg){
        super(msg);
    }
    
    public BpaasRuntimeException(Throwable throwable){
        super(throwable);
    }
    
    public BpaasRuntimeException(String msg, Throwable throwable){
        super(msg, throwable);
    }
}
