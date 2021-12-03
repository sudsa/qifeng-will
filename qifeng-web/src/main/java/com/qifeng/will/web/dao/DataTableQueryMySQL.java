

package com.bpaas.doc.framework.web.dao;

import javax.servlet.http.HttpServletRequest;

/**
 * 解决mysql中文排序问题
 * 
 * @author huyang 2015年7月7日
 */
public class DataTableQueryMySQL extends DataTableQuery {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public DataTableQueryMySQL(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getOrderParam() {
        // TODO Auto-generated method stub
        return "CONVERT(" + super.getOrderParam() + " USING gbk)";
    }

}