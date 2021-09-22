package com.hanxiaozhang.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈Json工具类（阿里fastjson版）〉
 *
 * @author hanxinghua
 * @create 2019/10/10
 * @since 1.0.0
 */
public class JsonUtil {

    public static void main(String[] args) {

    }
    /**
     * Bean对象转JSON
     *
     * @param object
     * @param dataFormatString
     * @return
     */
    public static String beanToJson(Object object, String dataFormatString) {
        if (object != null) {
            if (StringUtil.isBlank(dataFormatString)) {
                return JSONObject.toJSONString(object);
            }
            return JSON.toJSONStringWithDateFormat(object, dataFormatString);
        } else {
            return null;
        }
    }

    /**
     * Bean对象转JSON
     *
     * @param object
     * @return
     */
    public static String beanToJson(Object object) {
        if (object != null) {
            return JSON.toJSONString(object);
        } else {
            return "";
        }
    }

    /**
     * String转JSON字符串
     *
     * @param key
     * @param value
     * @return
     */
    public static String stringToJsonByFastjson(String key, String value) {
        if (StringUtil.isBlank(key) || StringUtil.isBlank(value)) {
            return null;
        }
        Map<String, String> map = new HashMap<String, String>(16);
        map.put(key, value);
        return beanToJson(map, null);
    }

    /**
     * 将json字符串转换成对象
     *
     * @param json
     * @param clazz
     * @return
     */
    public static Object jsonToBean(String json, Object clazz) {
        if (StringUtil.isBlank(json) || clazz == null) {
            return null;
        }
        return JSON.parseObject(json, clazz.getClass());
    }

    /**
     * json字符串转Map<String, Object>
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToMapSO(String json) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, Map.class, Feature.OrderedField);
    }


    /**
     * json字符串转Map<String, String>
     *
     * @param json
     * @return
     */
    public static Map<String, String> jsonToMapSS(String json) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, Map.class);
    }


    /**
     * json字符串转LinkedHashMap<String, Object>
     *
     * @param json
     * @return
     */
    public static Map<String, Object> jsonToLinkedHashMapSO(String json) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, LinkedHashMap.class);
    }


    /**
     * json字符串转LinkedHashMap<String, String>
     *
     * @param json
     * @return
     */
    public static Map<String, String> jsonToLinkedHashMapSS(String json) {
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseObject(json, LinkedHashMap.class);
    }


    /**
     * json字符串转List<T>
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json,Class<T> tClass){
        if (StringUtil.isBlank(json)) {
            return null;
        }
        return JSON.parseArray(json, tClass);
    }

}
