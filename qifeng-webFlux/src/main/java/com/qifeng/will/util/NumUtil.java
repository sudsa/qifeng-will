package com.qifeng.will.util;

import java.util.function.Predicate;

public class NumUtil {

    public static boolean isEven(Integer n){
        return n%2==0?true:false;
    }

    public static Predicate<Integer> createIsOdd() {
        Predicate<Integer> check = (Integer number) -> number % 2 != 0;
        return check;
    }

}
