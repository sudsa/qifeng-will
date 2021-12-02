package com.qifeng.will.utils;

import java.math.BigDecimal;

/**
 * 功能描述: <br>
 * 〈BigDecimal工具类〉
 *
 * @Author:hanxinghua
 * @Date: 2020/6/28
 */
public class BigDecimalUtil {

    public final static BigDecimal Zero = BigDecimal.ZERO;

    public final static BigDecimal One = BigDecimal.ONE;

    public final static BigDecimal hundred = new BigDecimal(100);

    /**
     * 默认保留小数点位数
     */
    public final static int SCALE= 2;

    /**
     * 默认精确的小数位
     */
    private static int DEF_DIV_SCALE = 13;

    /**
     * 判断a与0比较
     *
     * @param a
     * @return 1 大于 0 等于 -1 小于
     */
    public static int compare0(BigDecimal a){
        return getNotNull(a).compareTo(Zero);
    }

    /**
     * 判断a与b比较
     * 为空则置为0在比较
     *
     * @param a
     * @param b
     * @return 1 大于 0 等于 -1 小于
     */
    public static int compare(BigDecimal a,BigDecimal b ){
        return getNotNull(a).compareTo(getNotNull(b));
    }

    /**
     * 处理非空,如果为空返回0
     *
     * @param param
     * @return
     */
    public static BigDecimal getNotNull(BigDecimal param){
        return param==null?Zero:param;
    }


    /**
     * 求差,得出sub1-sub2
     * 如果任一参数为空,置换为0进行计算
     *
     * @param sub1
     * @param sub2
     * @return
     */
    public static BigDecimal sub(BigDecimal sub1,BigDecimal sub2){
        return getNotNull(sub1).subtract(getNotNull(sub2));
    }

    /**
     * 求差,得出sub1-param1-param2...
     * 如果任一参数为空,置换为0进行计算
     *
     * @param sub1
     * @param params
     * @return
     */
    public static BigDecimal sub(BigDecimal sub1,BigDecimal ...params){
        BigDecimal sub = getNotNull(sub1);
        for (BigDecimal param : params) {
            sub = sub.subtract(getNotNull(param));
        }
        return sub;
    }

    /**
     * 求和,得出add1+add2
     * 如果任一参数为空,置换为0进行计算
     *
     * @param add1
     * @param add2
     * @return
     */
    public static BigDecimal add(BigDecimal add1,BigDecimal add2){
        return getNotNull(add1).add(getNotNull(add2));
    }

    /**
     * 多参数相加求和,得出param1+param2+param3...
     *
     * @param params
     * @return
     */
    public static BigDecimal add(BigDecimal ...params){
        BigDecimal total = Zero;
        for (BigDecimal param : params) {
            total = total.add(getNotNull(param));
        }
        return total;
    }

    /**
     * 乘积，得出mul1*mul2
     *
     * @param mul1
     * @param mul2
     * @return
     */
    public static BigDecimal mul(BigDecimal mul1 , double mul2 ) {
        BigDecimal b1 = BigDecimal.valueOf(mul2);
        return mul1.multiply(b1);
    }

    /**
     * 乘积，得出mul1*mul2
     *
     * @param mul1
     * @param mul2
     * @return
     */
    public static BigDecimal mul(BigDecimal mul1 , int mul2 ) {
        BigDecimal b1 = BigDecimal.valueOf(mul2);
        return mul1.multiply(b1);
    }

    /**
     * 乘积，得出mul1*mul2
     *
     * @param mul1
     * @param mul2
     * @return
     */
    public static BigDecimal mul(BigDecimal mul1 , Integer mul2 ) {
        BigDecimal b1 = BigDecimal.valueOf(mul2);
        return mul1.multiply(b1);
    }

    /**
     * 乘积，得出param1*param2*param3...
     *
     * @param params
     * @return
     */
    public static BigDecimal mul(BigDecimal... params) {
        BigDecimal b1 = new BigDecimal(1);
        for (BigDecimal b2 : params) {
            b1 = b1.multiply(b2);
        }
        return b1;
    }

    /**
     * 精确除法计算,默认保留13位小数
     * @param div1
     * @param div2
     * @return
     */
    public static BigDecimal div(BigDecimal div1,BigDecimal div2) {
        return div(div1,div2,DEF_DIV_SCALE);
    }

    /**
     * 精确除法计算
     * @param div1
     * @param div2
     * @param scale 保留小数位数
     * @return
     */
    public static BigDecimal div(BigDecimal div1,BigDecimal div2,int scale) {
        return div1.divide(div2, scale, BigDecimal.ROUND_DOWN);
    }


    /**
     * 精确除法计算
     * @param div1
     * @param div2
     * @param scale 保留小数位数
     * @return
     */
    public static BigDecimal divHalf(BigDecimal div1,BigDecimal div2,int scale) {
        return div1.divide(div2, scale, BigDecimal.ROUND_HALF_UP);
    }



    /**
     * 保留两位小数，四舍五入
     *
     * @param v 需要四舍五入的数字
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(BigDecimal v) {
        return v.divide(One, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 保留两位小数，截取
     *
     * @param b 需要处理的数字
     * @return 去掉保留位数后的结果
     */
    public static BigDecimal decimal(BigDecimal b) {
        return b.divide(One, SCALE, BigDecimal.ROUND_DOWN);
    }

    /**
     * 保留多位小数，截取
     *
     * @param b 需要处理的数字
     * @param scale 小数点后保留几位
     * @return 去掉保留位数后的结果
     */
    public static BigDecimal decimal(BigDecimal b, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return b.divide(One, scale, BigDecimal.ROUND_DOWN);
    }


}
