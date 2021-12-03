/**  
* @Project: bpaas-framework-base
* @Title: EasyUIPageUtil.java
* @Package com.bpaas.doc.framework.base.util
* @Description: TODO
* @author Administrator
* @date 2015年11月27日 上午9:13:21
* @version V1.0
*/
package com.bpaas.doc.framework.web.pojo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.bpaas.doc.framework.base.common.BaseConstant;
import com.bpaas.doc.framework.base.util.CheckEmptyUtil;
import com.bpaas.doc.framework.base.util.StringUtil;

/**
 * bootstrap模版使用
 * @author huyang
 *
 */
public class Page implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2878238301445311610L;
	private Integer page;
    private Integer rows;
    private String sort;
    private String order;
    private String sortParam;

    public Page(HttpServletRequest request){
        page = Integer.parseInt(request.getParameter("page")==null?"0":request.getParameter("page"));
        rows = Integer.parseInt(request.getParameter("rows")==null?"0":request.getParameter("rows"));
        String sortParam = request.getParameter("sort");
        this.sortParam = sortParam;
        if (!CheckEmptyUtil.isEmpty(sortParam)){
            if (sortParam.indexOf(BaseConstant.Separate.PERIODS) >= 0){
                String[] sortParams = sortParam.split("\\.");
                StringBuffer sortBuffer = new StringBuffer(sortParams[0]);
                sortBuffer.append(BaseConstant.Separate.PERIODS).append(StringUtil.camelToUnderline(sortParams[1]));
                sort = sortBuffer.toString();
            } else{
                sort = StringUtil.camelToUnderline(request.getParameter("sort"));
            }
        }
        order = request.getParameter("order");
    }
    /** 
	 * <p>Title: </p> 
	 * <p>Description: </p> 
	 * @param page
	 * @param rows
	 * @param sort
	 * @param order 
	 */
	public Page(Integer page, Integer rows, String sort, String order) {
		super();
		this.page = page;
		this.rows = rows;
        this.sortParam = sort;
        int periodsIndex = sort.indexOf(BaseConstant.Separate.PERIODS)+1 ;
        if(periodsIndex > 0){
            String childExampleStr = sort.substring(0, periodsIndex);
            String sortStr = new String(sort.substring(periodsIndex, sort.length()));
            this.sort=childExampleStr + StringUtil.camelToUnderline(sortStr);
        }else{
        	this.sort = StringUtil.camelToUnderline(sort);
        }
		this.order = order;
	}
	/**
     * @return 当前页码
     */
    public Integer getPage() {
        return page;
    }
    /**
     * @return 每页数据行数
     */
    public Integer getRows() {
        return rows;
    }
    /**
     * @return 排序字段,驼峰形式自动转换为下划线
     */
    public String getSort() {
        return sort;
    }
    /**
     * @return 排序方向desc,asc
     */
    public String getOrder() {
        return order;
    }
    
    public PageBounds getPageBounds(){
    	if(rows == 0){
    		return new PageBounds();
    	} else {
            return new PageBounds(page, rows);
    	}
    }
    
    public String getOrderByClause(){
		// 构建排序条件
		StringBuilder orderByClause = new StringBuilder("");
		orderByClause.append(getSort()).append(" ").append(getOrder());
		return orderByClause.toString();
    }

    /**
     * 原始的sort参数数据
     * @return
     */
    public String getSortParam() {
        return sortParam;
    }
}
