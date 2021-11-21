package com.hanxiaozhang.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.hanxiaozhang.annotation.SensitiveAnnotation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

/**
 * 功能描述: <br>
 * 〈日志敏感信息脱敏工具〉
 *
 * @Author:hanxinghua
 * @Date: 2020/1/29
 */
@Slf4j
public class SensitiveUtil {


    /**
     * 将对象转换Json
     *
     * @param object
     * @return
     */
    public static String toJsonString(Object object) {
        return JSON.toJSONString(object, getValueFilter());
    }


    /**
     * [身份证号] 显示最后四位，其他隐藏。共计18位或者15位。
     * <例子：*************5762>
     *
     * @param idCardNum
     * @return
     */
    public static String desensitizeIdCardNum(String idCardNum) {
        if (StringUtils.isBlank(idCardNum)) {
            return "";
        }
        String num = StringUtils.right(idCardNum, 4);
        return StringUtils.leftPad(num, StringUtils.length(idCardNum), "*");
    }


    /**
     * [身份证号] 显示前4位和最后四位，其他隐藏。共计18位或者15位。
     * <例子：2323*********5762>
     *
     * @param idCardNum 身份证号
     * @return 身份证号
     */
    public static String desensitizeIdCardB4A4(String idCardNum) {
        if (StringUtils.isBlank(idCardNum)) {
            return "";
        }
        return StringUtils.left(idCardNum, 4).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(idCardNum, 4),StringUtils.length(idCardNum), "*"), "***"));

    }


    /**
     * [手机号码] 前三位，后四位，其他隐藏
     * <例子:138******1234>
     *
     * @param num
     * @return
     */
    public static String  desensitizeMobilePhone(String num) {
        if (StringUtils.isBlank(num)) {
            return "";
        }
        return StringUtils.left(num, 3).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(num, 4),StringUtils.length(num), "*"), "***"));
    }


    /**
     * [银行卡号] 前六位，后四位，其他用星号隐藏每位1个星号
     * <例子:6222600**********1234>
     *
     * @param cardNum
     * @return
     */
    public static String desensitizeBankCard(String cardNum) {
        if (StringUtils.isBlank(cardNum)) {
            return "";
        }
        return StringUtils.left(cardNum, 6).concat(StringUtils.removeStart(StringUtils.leftPad(StringUtils.right(cardNum, 4), StringUtils.length(cardNum), "*"), "******"));
    }


    /**
     * [姓名] 只显示第一个汉字，其他隐藏为星号
     * <例子：李**>
     *
     * @param fullName
     * @return
     */
    public static String desensitizeChineseName(String fullName) {
        if (StringUtils.isBlank(fullName)) {
            return "";
        }
        String name = StringUtils.left(fullName, 1);
        return StringUtils.rightPad(name, StringUtils.length(fullName), "*");
    }


    /**
     * [IP地址] 只显示最后两位或者三位数字，其他隐藏为星号
     * <例子：**.**.**.21>
     *
     * @param ip
     * @return
     */
    public static String desensitizeIP(String ip) {
        if (StringUtils.isBlank(ip)) {
            return "";
        }
        String name = StringUtils.right(ip, 3);
        return StringUtils.leftPad(name, StringUtils.length(ip), "*");
    }


    /**
     * 获取过滤脱敏字段值
     *
     * @return
     */
    private static final ValueFilter getValueFilter() {
        return new ValueFilter() {
            @Override
            public Object process(Object obj, String key, Object value) {
                try {
                    Field field = obj.getClass().getDeclaredField(key);
                    SensitiveAnnotation annotation = field.getAnnotation(SensitiveAnnotation.class);
                    if (null != annotation && value instanceof String) {
                        String strVal = (String) value;
                        if (StringUtils.isNotBlank(strVal)) {
                            switch (annotation.type()) {
                                case PHONE:
                                    return desensitizeMobilePhone(strVal);
                                case ID_CARD:
                                    return desensitizeIdCardNum(strVal);
                                case BANK_CARD:
                                    return desensitizeBankCard(strVal);
                                case REAL_NAME:
                                    return desensitizeChineseName(strVal);
                                default:
                                    break;
                            }
                        }
                    }
                } catch (NoSuchFieldException e) {
                    log.warn("未找到方法",e);
                }
                return value;
            }
        };
    }
}
