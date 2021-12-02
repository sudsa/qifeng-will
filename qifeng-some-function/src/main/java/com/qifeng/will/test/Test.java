package com.qifeng.will.test;

import com.qifeng.will.utils.JsonUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2020/12/11
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>(8) {{
            put("1","1");
            put("2","2");
            put("3","3");
            put("4","4");
        }};
        System.out.println(JsonUtil.beanToJson(map));
    }

}
