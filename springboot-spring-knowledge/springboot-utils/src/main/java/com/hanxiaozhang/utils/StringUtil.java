package com.hanxiaozhang.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;


/**
 * 〈一句话功能简述〉<br>
 * 〈字符串工具类〉
 *
 * @author hanxinghua
 * @create 2019/10/10
 * @since 1.0.0
 */
public class StringUtil {


    private static final Logger logger = LoggerFactory.getLogger(StringUtil.class);

    public static void main(String[] args) {
        boolean f = isInteger("100.09");
    }

    /**
     * 检验是否为空或空字符串
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return StringUtil.toString(str).equals("");
    }

    /**
     * 检验是否非空字符串
     *
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !StringUtil.toString(str).equals("");
    }


    /**
     * 检验是否为空或空字符数组
     *
     * @param arr
     * @return
     */
    public static boolean isBlank(String[] arr) {
        if (arr == null || arr.length == 0){
            return true;
        } else if (isBlank(arr[0])){
            return true;
        } else{
            return false;
        }
    }


    /**
     * 字符串空处理，去除首尾空格 如果str为null，返回"",否则返回str
     *
     * @param str
     * @return
     */
    public static String toString(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    /**
     * 将对象转为字符串
     *
     * @param o
     * @return
     */
    public static String toString(Object o) {
        if (o == null) {
            return "";
        }
        String str = "";
        if (o instanceof String) {
            str = (String) o;
        } else {
            str = o.toString();
        }
        return str.trim();
    }

    /**
     * 校验是否全中文，返回true 表示是 反之为否
     *
     * @param realname
     * @return
     */
    public static boolean isChinese(String realname) {
        realname = toString(realname);
        Pattern regex = compile("[\\u4e00-\\u9fa5]{2,25}");
        Matcher matcher = regex.matcher(realname);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为整数
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern regex = compile("\\d*");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (isBlank(str)) {
            return false;
        }
        Pattern regex = compile("-?[0-9]+(\\.[0-9]+)?");
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    /**
     * Long数组拼接为字符串
     *
     * @param args
     * @return
     */
    public static String contact(long[] args) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < args.length; i++) {
            sb.append(args[i]);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }


    /**
     * 大写字母转成“_”+小写 驼峰命名转换为下划线命名
     *
     * @param str
     * @return
     */
    public static String toUnderline(String str) {
        char[] charArr = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] >= 'A' && charArr[i] <= 'Z') {
                sb.append('_').append(charArr[i]);
            } else {
                sb.append(charArr[i]);
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 下划线改成驼峰样子
     *
     * @param str
     * @return
     */
    public static String clearUnderline(String str) {
        char[] charArr = StringUtil.toString(str).toLowerCase().toCharArray();
        StringBuffer sb = new StringBuffer();
        sb.append(charArr[0]);
        boolean isClear = false;
        for (int i = 1; i < charArr.length; i++) {
            if (charArr[i] == '_') {
                isClear = true;
                continue;
            }
            if (isClear && (charArr[i] >= 'a' && charArr[i] <= 'z')) {
                char c = (char) (charArr[i] - 32);
                sb.append(c);
                isClear = false;
            } else {
                sb.append(charArr[i]);
            }

        }
        return sb.toString();
    }

    /**
     * String to int
     *
     * @param str
     * @return
     */
    public static int toInt(String str) {
        if (StringUtil.isBlank(str)){
            return 0;
        }
        int ret = 0;
        try {
            ret = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * String to Long
     *
     * @param str
     * @return
     */
    public static long toLong(String str) {
        if (StringUtil.isBlank(str)){
            return 0L;
        }
        long ret = 0;
        try {
            ret = Long.parseLong(str);
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 去除空格
     *
     * @param str
     * @return
     */
    public static String replaceEmptyStr(String str) {
        if (str == null) {
            return "";
        }
        return str.replace(" ", "");
    }


    /**
     * 格式化字符串(替换符自己指定)
     *
     * @param format
     * @param replaceOperator
     * @param args
     * @return
     */
    public static String formatIfArgs(String format, String replaceOperator, Object... args) {
        if (isBlank(format) || isBlank(replaceOperator)) {
            return format;
        }

        format = replace(format, replaceOperator, "%s");
        return formatIfArgs(format, args);
    }


    /**
     * 格式化字符串（替换符为%s）
     *
     * @param format
     * @param args
     * @return
     */
    public static String formatIfArgs(String format, Object... args) {
        if (isBlank(format)) {
            return format;
        }

        return (args == null || args.length == 0)  ? String.format(format.replaceAll("%([^n])", "%%$1")) : String.format(format, args);
    }


    /**
     * 替换字符串
     *
     * @param inString
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (isNotBlank(inString) && isNotBlank(oldPattern) && newPattern != null) {
            int index = inString.indexOf(oldPattern);
            if (index == -1) {
                return inString;
            } else {
                int capacity = inString.length();
                if (newPattern.length() > oldPattern.length()) {
                    capacity += 16;
                }

                StringBuilder sb = new StringBuilder(capacity);
                int pos = 0;

                for(int patLen = oldPattern.length(); index >= 0; index = inString.indexOf(oldPattern, pos)) {
                    sb.append(inString.substring(pos, index));
                    sb.append(newPattern);
                    pos = index + patLen;
                }

                sb.append(inString.substring(pos));
                return sb.toString();
            }
        } else {
            return inString;
        }
    }

}
