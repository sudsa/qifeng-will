

package com.qifeng.will.web.controller;

import com.bpaas.doc.framework.web.dao.DataTableQuery;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletResponse;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * 基础控制器 其他控制器继承此控制器获得日期字段类型转换和防止XSS攻击的功能
 * 
 * @description
 * @author ty
 * @date 2014年3月19日
 */
@NotProguard
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils
                        .escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });

        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtil.parseDate(text));
            }
        });

        // Timestamp 类型转换
        binder.registerCustomEditor(
                Timestamp.class,
                new PropertyEditorSupport() {
                    @Override
                    public void setAsText(String text) {
                        Date date = DateUtil.parseDate(text);
                        setValue(date == null ? null : new Timestamp(date
                                .getTime()));
                    }
                });
    }

    /**
     * 获取MetronicUI分页数据
     * @param pageList
     * @param dtq
     * @param <T>
     * @return
     */
    public <T> Map<String, Object> getMetronicUIGrid(PageList<T> pageList,
            DataTableQuery dtq) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (CheckEmptyUtil.isEmpty(pageList)) {
            map.put("data", new PageList<T>());
            map.put("draw", dtq.getPageDraw());
            map.put("recordsTotal", 0);
            map.put("recordsFiltered", 0);
            // map.put("rows", new ArrayList<T>());
            // map.put("total", 0);
        } else {
            map.put("data", pageList);
            map.put("draw", dtq.getPageDraw());
            map.put("recordsTotal", pageList.getPaginator().getTotalCount());
            map.put("recordsFiltered", pageList.getPaginator().getTotalCount());
            // map.put("rows", pageList);
            // map.put("total", pageList.getPaginator().getTotalCount());
        }
        return map;
    }

    /**
     * 获取easyui分页数据
     * @param pageList
     * @param <T>
     * @return
     */
    public <T> Map<String, Object> getEasyUIGrid(PageList<T> pageList){
        Map<String, Object> map = new HashMap<String, Object>();
        if (CheckEmptyUtil.isEmpty(pageList)){
            map.put("rows", new ArrayList<T>());
            map.put("total", 0);
        } else{
            map.put("rows", pageList);
            map.put("total", pageList.getPaginator().getTotalCount());
        }
        return map;
    }

    /**
     * 获取easyui分页数据
     * @param pageList
     * @param <T>
     * @return
     */
    public <T> Map<String, Object> getEasyUIGrid(List<T> pageList){
        Map<String, Object> map = new HashMap<String, Object>();
        if (CheckEmptyUtil.isEmpty(pageList)){
            map.put("rows", new ArrayList<T>());
            map.put("total", 0);
        } else{
            map.put("rows", pageList);
            map.put("total", pageList.size());
        }
        return map;
    }


	public void writeJSONResult(Object result, HttpServletResponse response) {
		try {
			response.setContentType(BaseConstant.CONTENT_TYPE);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(result));
		} catch (IOException e) {
			logger.error("response回写失败", e);
		}
	}

}
