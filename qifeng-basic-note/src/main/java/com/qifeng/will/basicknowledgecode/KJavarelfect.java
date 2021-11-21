package com.hanxiaozhang.basicknowledgecode;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class KJavarelfect {


    public static void test(){
        Class clazz = null;
        try {
            clazz = Class.forName("com.hanxiaozhang.basicknowledgecode.StmDevp");
            Constructor constructor = clazz.getConstructor(String.class, int.class);
            constructor.setAccessible(true);
            StmDevp sp = (StmDevp) constructor.newInstance("9981", 21);
            Method method = clazz.getMethod("getUssss", String.class);
            if (method != null) {
                method.invoke(sp,"jackkkk");
            }
            Method method2 = clazz.getMethod("getUssss", String[].class);
            //method2.invoke("getUssss", new Object[]{new String[]{"123","11","33"}});
            method2.invoke("getUssss", new String[]{"123","11","33"});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        test();
    }

}
