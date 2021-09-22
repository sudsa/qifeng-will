package com.qifeng.will.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 格式校验
 * @author xuwei
 */
public class RegexUtil {

    public static boolean CheckEmail(String email) {
        //电子邮件
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(email);
        return matcher.matches();
    }

    public static boolean checkPhone(String phone) {
        String check = "^[1][3456789][0-9]{9}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(phone);
        return matcher.matches();
    }

   /* public static void main(String[] args) {
        String phone = "13200000000";
        boolean b = checkPhone(phone);
        System.out.println(b);
    }*/
}
