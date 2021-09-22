package com.qifeng.will.base.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ConvertUtils extends org.apache.commons.beanutils.ConvertUtils{

    static {
        registerDateConverter();
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成List.
     * 
     * @param collection
     *            来源集合.
     * @param propertyName
     *            要提取的属性名.
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List convertElementPropertyToList(
            final Collection collection, final String propertyName) {
        List list = new ArrayList();

        try {
            for (Object obj : collection) {
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception e) {
            throw ReflectionsUtil.convertReflectionExceptionToUnchecked(e);
        }

        return list;
    }

    /**
     * 提取集合中的对象的属性(通过getter函数), 组合成由分割符分隔的字符串.
     * 
     * @param collection
     *            来源集合.
     * @param propertyName
     *            要提取的属性名.
     * @param separator
     *            分隔符.
     */
    @SuppressWarnings({ "rawtypes" })
    public static String convertElementPropertyToString(
            final Collection collection, final String propertyName,
            final String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    /**
     * 转换字符串到相应类型.
     * 
     * @param value
     *            待转换的字符串.
     * @param toType
     *            转换目标类型.
     */
	public static Object convertStringToObject(String value, Class<?> toType) {
		Object obj = convert(value,toType);
        return obj;
    }

    /**
     * 定义日期Converter的格式: yyyy-MM-dd 或 yyyy-MM-dd HH:mm:ss
     */
    private static void registerDateConverter() {
        DateConverter dc = new DateConverter();
        dc.setUseLocaleFormat(true);
        dc.setPatterns(new String[] { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss" });
        org.apache.commons.beanutils.ConvertUtils.register(dc, Date.class);
    }
    
    /**
     * @throws IllegalAccessException 
     * @throws IllegalArgumentException 
     * 对象转换为Map<field,value>
     * Collection不进行转换，Date自动格式化为yyyy-MM-dd HH:mm:ss
     * @Description: TODO
     * @param @param obj
     * @param @return
     * @param @throws Exception   
     * @return Map<String,Object>    
     * @throws
     */
	public static Map<String, String> objectToMap(Object obj) throws IllegalArgumentException, IllegalAccessException  {
		if (obj == null) {
			return null;
		}

		Map<String, String> map = new HashMap<String, String>();

		Field[] declaredFields = obj.getClass().getDeclaredFields();
		for (Field field : declaredFields) {
			field.setAccessible(true);
			if (field.get(obj) == null || Collection.class.equals(field.getType())) {
				continue;
			}
			if (Date.class.equals(field.getType())) {
				Date date = (Date)field.get(obj);
				map.put(field.getName(), DateUtil.formatDateTime(date));
			} else{
				map.put(field.getName(), field.get(obj).toString());
			}
		}

		return map;
	}
}
