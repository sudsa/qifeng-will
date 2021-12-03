

package com.bpaas.doc.framework.web.dao;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import com.bpaas.doc.framework.base.util.StringUtil;
/**
 * Metronic框架封装分页排序字段
 * @Description TODO
 * @date 2015年11月26日 
 * @version V1.0 
 * @author Administrator
 */
public class DataTableQuery implements Serializable {
    private static final long serialVersionUID = -6509914172864875501L;

    private HttpServletRequest request;

    private int pageStart;

    private int pageLength;
    /**
     * 排序列序号
     */
    private String orderColumn;

    private String orderBy;

    private String orderParam;

    private String pageDraw;

    private boolean isOrdered;

    public DataTableQuery(HttpServletRequest request) {
        this.request = request;
        this.orderColumn = request.getParameter("order[0][column]");
        this.pageStart = Integer.parseInt(request.getParameter("start"));
        this.pageLength = Integer.parseInt(request.getParameter("length"));
        this.orderBy = request.getParameter("order[0][dir]");
        this.orderParam = StringUtil.camelToUnderline(request.getParameter("columns[" + this.orderColumn
                + "][name]"));
        this.pageDraw = request.getParameter("draw");
    }

    public boolean getIsOrdered() {
        return (this.orderParam != null) && (!"".equals(this.orderParam));
    }

    public int getPageStart() {
        return this.pageStart;
    }

    public int getPageLength() {
        return this.pageLength;
    }

    public String getOrderBy() {
        return this.orderBy;
    }

    public String getOrderParam() {
        return this.orderParam;
    }

    public String getPageDraw() {
        return this.pageDraw;
    }

    public HttpServletRequest getRequest() {
        return this.request;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(
                "Fetch DataTables Params From Request.pageStart:[")
                .append(this.pageStart).append("] pageLength:[")
                .append(this.pageLength).append("] pageDraw:[")
                .append(this.pageDraw).append("] isOrdered:[")
                .append(this.isOrdered).append("]");
        if (this.isOrdered) {
            sb.append("orderColumn:[").append(this.orderColumn)
                    .append("] orderParam:[").append(this.orderParam)
                    .append("] orderBy:[").append(this.orderBy).append("]");
        }
        return sb.toString();
    }

    public int getCurrentPage() {
        return pageStart / pageLength + 1;
    }
}