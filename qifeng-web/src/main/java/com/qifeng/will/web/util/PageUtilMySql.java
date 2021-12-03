/**  
* @Project: bpaas-framework-base
* @Title: EasyUIPageUtil.java
* @Package com.bpaas.doc.framework.base.util
* @Description: TODO
* @author Administrator
* @date 2015年11月27日 上午9:13:21
* @version V1.0
*/
package com.bpaas.doc.framework.web.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 修复MySql中文排序
 * @date 2015年11月27日 
 * @version V1.0 
 * @author Administrator
 */
public class PageUtilMySql extends PageUtil {

	private static final long serialVersionUID = 4983737076447585205L;

	public PageUtilMySql(HttpServletRequest request) {
        super(request);
    }
    
    public PageUtilMySql(Integer page, Integer rows, String sort, String order){
    	super(page, rows, sort, order);
    }

    @Override
    public String getSort() {
        // TODO Auto-generated method stub
        return "CONVERT(" + super.getSort() + " USING gbk)";
    }
}
