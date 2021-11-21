package com.qifeng.will.httpclient;



import com.qifeng.will.utils.JsonUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试〉
 *
 * @author hanxinghua
 * @create 2019/10/8
 * @since 1.0.0
 */

public class HttpClientTest {

    @Test
    public void test1() {
        String str="https://www.hanxiaozhang.xyz/test/get/2";
        String result = HttpClientUtil.get(str);
        System.out.println(result);
    }

    @Test
    public void test2() {
        String str="http://d75q5t.natappfree.cc/test/post";
        Map<String, String> reqMap=new HashMap<>();
        reqMap.put("offset","0");
        reqMap.put("limit","10");
        String result = HttpClientUtil.post(str,reqMap);
        System.out.println(result);
    }

    @Test
    public void test3() {
        String str="http://fthqt2.natappfree.cc/test/postMap";
        Map<String, String> reqMap=new HashMap<>();
        reqMap.put("offset","0");
        reqMap.put("limit","10");
        String result = HttpClientUtil.post(str,reqMap);
        System.out.println(result);
    }

    @Test
    public void test4() {
        String str="http://dys4da.natappfree.cc/test/postJson";
        Map<String, String> reqMap=new HashMap<>();
        reqMap.put("offset","0");
        reqMap.put("limit","10");
        String jsonStr =  JsonUtil.beanToJson(reqMap);
        String result = HttpClientUtil.post(str,jsonStr,HttpClientConstant.DEFAULT_ENCODING);
        System.out.println(result);
    }

    @Test
    public void test5() {
        String url="http://dys4da.natappfree.cc/common/sysFile/upload";
        String fileParamName="file";
        String path="D:\\test\\test.xlsx";
        String result = HttpClientUtil.upload(url,fileParamName,path,HttpClientConstant.DEFAULT_ENCODING);
        System.out.println(result);
    }




}
