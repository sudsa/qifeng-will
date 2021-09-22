package com.hanxiaozhang.wordutil;



import com.hanxiaozhang.constant.ContractConstant;
import com.hanxiaozhang.watermarkutil.PDFWatermarkPrint;
import com.hanxiaozhang.watermarkutil.WatermarkPrint;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * 功能描述: <br>
 * 〈word转pdf, pdf转word工具类测试〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/16
 */
@Slf4j
public class WordUtilTest {


    public static void main(String[] args) throws Exception {

        String docx = "D:\\test\\test.docx";
        WordUtil.getInstance().execute(docx,"test",initData(),"hhhh");
    }


    /**
     * 初始化docx文件中的变量数据
     * @return
     */
    public static Map initData() {
        Map<String, String> data = new HashMap<>(4);
        data.put("username", "张三");
        return data;
    }
}
