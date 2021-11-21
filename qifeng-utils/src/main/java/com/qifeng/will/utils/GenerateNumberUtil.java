package com.hanxiaozhang.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉<br>
 * 〈订单工具类〉
 *
 * @author hanxinghua
 * @create 2020/6/28
 * @since 1.0.0
 */
public class GenerateNumberUtil {

    public final static String DATE_YYYYMMDD = "yyyyMMdd";


    /**
     * 获取订单编号
     *
     * @return
     */
    public static  String getOrderNo(){
        return getSerialNo("gg");
    }


    /**
     * 获取自定义前缀的流水号
     *
     * @param prefix
     * @return
     */
    public static String getSerialNo(String prefix) {
        return prefix + format(new Date(), DATE_YYYYMMDD) + String.valueOf(System.currentTimeMillis());
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {

        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);

    }

}
