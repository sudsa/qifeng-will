package com.qifeng.will.basicknowledgecode;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public class WeakHashMapTest {

    static Map wMap = new WeakHashMap();
    static Map map = new HashMap();

    public static void init(){
        wMap.put("1", "ding");
        wMap.put("2", "job");
        wMap.put(new String("jack"),"coq");

        map.put("copany","vake");
    }
    public static void testWeakHashMap(){

        System.out.println("first get:"+map.get("copany"));
        System.out.println("first get:"+wMap.get("jack"));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("next get:"+map.get("copany"));
        System.out.println("next get:"+wMap.get("jack"));

        //System.gc();
        System.out.println("gc 马匹 get:"+map.get("copany"));
        System.out.println("gc get:"+wMap.get("jack"));
    }
    public static void main(String[] args) {
        init();
        testWeakHashMap();
    }

}
