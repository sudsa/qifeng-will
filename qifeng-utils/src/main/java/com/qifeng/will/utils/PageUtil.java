package com.qifeng.will.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 功能描述: <br>
 * 〈分页工具〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/18
 */
public class PageUtil implements Serializable {
	private static final long serialVersionUID = 1L;
	private int total;
	private List<?> rows;

	public PageUtil(List<?> list, int total) {
		this.rows = list;
		this.total = total;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

}
