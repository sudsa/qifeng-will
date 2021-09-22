package com.hanxiaozhang.pdfutil;

/**
 * 功能描述: <br>
 * 〈通用工具〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/17
 */
public class CommonUtil {

    public static String getDir() {
        String dir = "D:/test/";
        // 判断系统类型，加载字体文件
        java.util.Properties prop = System.getProperties();
        String osName = prop.getProperty("os.name").toLowerCase();
        if (osName.indexOf("linux") > -1) {
            dir = "/data/byx_pm/pdfs/";
        }else if(osName.indexOf("mac os x") > -1){
           dir="/Users/king/Work/data/createpdf/";
        }

        return dir;
    }
}
