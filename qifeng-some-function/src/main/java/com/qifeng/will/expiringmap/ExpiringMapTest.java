package com.qifeng.will.expiringmap;

import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author howill
 * @create 2020/11/17
 * @since 1.0.0
 */
public class ExpiringMapTest {

    public static void main(String[] args) throws Exception {

//        // 简单使用：设置5秒的过期时间
//        test1();
//        // 过期策略的使用：
        test2();
//        // 可变有效期，即单独为每个entity设置过期时间和策略：
//        test3();
//        // 最大值的使用：
//        test4();
//        // 过期侦听器的使用：
//        test5();
//        // 懒加载的使用：
//        test6();
//        // 其他
//        test7();

//        ExpiringMap<String, PriorityBlockingQueue<String>> map1 = ExpiringMap.builder()
//                .maxSize(10000)
//                .variableExpiration()
//                .expiration(2, TimeUnit.SECONDS)
//                .build();
//
//        PriorityBlockingQueue<String> queue = new PriorityBlockingQueue<>();
//        PriorityBlockingQueue<String> queue1 = new PriorityBlockingQueue<>();
//        queue.put("22");
//        queue1.put("11");
//        map1.put("1", queue);
//        Thread.sleep(1500);
//        System.out.println(map1);
//        queue.put("33");
//        System.out.println(map1);
////        Thread.sleep(600);
////        System.out.println(map1);
//
//        map1.put("2", queue1);
//        System.out.println(map1.getExpectedExpiration("1"));
//        System.out.println(map1.getExpectedExpiration("2"));


    }

    /**
     * 其他：
     *
     * @throws InterruptedException
     */
    private static void test7() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .expiration(2, TimeUnit.SECONDS)
                .build();
        map.put("1", "测试");
        // 查看剩余过期时间：
        long remainExpiration = map.getExpectedExpiration("1");
        System.out.println("查看剩余过期时间：" + remainExpiration);
        // 查看设置过期时间：
        long setExpiration = map.getExpiration("1");
        System.out.println("查看设置过期时间：" + setExpiration);
        // 重置过期时间
        map.resetExpiration("1");
        System.out.println("查看剩余过期时间：" + map.getExpectedExpiration("1"));

    }


    /**
     * 懒加载的使用：put方法时不创建对象，在调用get方法时自动去创建对象
     *
     * @throws InterruptedException
     */
    private static void test6() throws InterruptedException {
        ExpiringMap<String, Student> map = ExpiringMap.builder()
                .expiration(2, TimeUnit.SECONDS)
                .entryLoader(name -> new Student(name))
                .build();
        System.out.println(map); // {}
        map.get("hanxiaozhang");
        System.out.println(map); // {hanxiaozhang=com.hanxiaozhang.expiringmap.ExpiringMapTest$Student@5d6f64b1}
    }

    static class Student {
        Object name;
        public Student(Object name) {
            this.name = name;
        }
    }

    /**
     * 过期侦听器的使用：当entity过期时，可以通知过期侦听器：
     *
     * @throws InterruptedException
     */
    private static void test5() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                // 同步过期提醒
                .expirationListener((key, value) -> remindExpiration(key, value))
                // 异步过期提醒
                .asyncExpirationListener((key, value) -> remindAsyncExpiration(key, value))
                .expiration(2, TimeUnit.SECONDS)
                .build();
        map.put("1", "测试");
        while (true) {

        }

    }

    /**
     * 过期提醒
     *
     * @param key
     * @param value
     */
    private static void remindExpiration(Object key, Object value) {
        System.out.println("过期提醒,key: " + key + " value: " + value);
    }

    /**
     * 异步过期提醒
     *
     * @param key
     * @param value
     */
    private static void remindAsyncExpiration(Object key, Object value) {
        System.out.println("异步过期提醒,key: " + key + " value: " + value);
    }


    /**
     * 最大值的使用：
     * Map中映射数目超过最大值的大小时，先过期第一个要过期的entity过期
     *
     * @throws InterruptedException
     */
    private static void test4() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .maxSize(3)
                .expiration(2, TimeUnit.SECONDS)
                .build();
        map.put("1", "测试");
        map.put("2", "测试");
        map.put("3", "测试");
        System.out.println(map); // {1=测试, 2=测试, 3=测试}
        map.put("4", "测试");
        System.out.println(map); // {2=测试, 3=测试, 4=测试}

    }


    /**
     * 可变有效期，即单独为每个entity设置过期时间和策略：
     *
     * @throws InterruptedException
     */
    private static void test3() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .variableExpiration()
                .expiration(2, TimeUnit.SECONDS)
                .build();
        map.put("1", "测试", ExpirationPolicy.CREATED, 1, TimeUnit.SECONDS);
        map.put("2", "测试", ExpirationPolicy.CREATED, 2, TimeUnit.SECONDS);
        map.put("3", "测试", ExpirationPolicy.CREATED, 999, TimeUnit.MILLISECONDS);
        map.put("4", "测试", ExpirationPolicy.CREATED, 1003, TimeUnit.MILLISECONDS);
        Thread.sleep(1002);
        System.out.println(map); // {2=测试, 4=测试}
    }

    /**
     * 过期策略的使用：
     * CREATED：  在每次更新元素时，过期倒计时清零
     * ACCESSED： 在每次访问元素时，过期倒计时清零
     *
     * @throws InterruptedException
     */
    private static void test2() throws InterruptedException {
        // 使用默认的CREATED策略：
        System.out.println("----使用默认的CREATED策略：----");
        ExpiringMap<String, String> map = ExpiringMap.builder()
                .expirationPolicy(ExpirationPolicy.CREATED)
                .expiration(2, TimeUnit.SECONDS)
                .build();
        // -- 测试CREATED策略，访问的情况
        map.put("1", "测试");
        Thread.sleep(1000);
        System.out.println(map.get("1"));  // 测试
        Thread.sleep(1001);
        System.out.println(map.get("1"));  // null
        // -- 测试CREATED策略，更新的情况
        map.put("2", "测试");
        Thread.sleep(1000);
        System.out.println(map.get("2")); // 测试
        map.put("2", "测试1");
        Thread.sleep(1000);
        System.out.println(map.get("2")); // 测试1
        Thread.sleep(900);
        System.out.println(map.get("2")); // 测试1
        Thread.sleep(200);
        System.out.println(map.get("2")); // null


        // 使用默认的ACCESSED策略：
        System.out.println("----使用默认的ACCESSED策略：----");
        ExpiringMap<String, String> map1 = ExpiringMap.builder()
                .expirationPolicy(ExpirationPolicy.ACCESSED)
                .expiration(2, TimeUnit.SECONDS)
                .build();
        // -- 测试ACCESSED策略，访问的情况
        map1.put("1", "测试");
        Thread.sleep(1000);
        System.out.println(map1.get("1"));  // 测试
        Thread.sleep(1100);
        System.out.println(map1.get("1"));  // 测试
        Thread.sleep(2100);
        System.out.println(map1.get("1"));  // 测试

        // 只是访问时，过期倒计时清零
        map1.put("2", "测试");
        Thread.sleep(1000);
        System.out.println(map1); // {2=测试}
        Thread.sleep(1100);
        System.out.println(map1); // {}

    }

    /**
     * 简单使用，设置5秒的过期时间
     *
     * @throws InterruptedException
     */
    private static void test1() throws InterruptedException {
        ExpiringMap<String, String> map = ExpiringMap.builder()
                // 设置过期时间和过期时间单位
                .expiration(5, TimeUnit.SECONDS)
                .build();
        map.put("1", "测试");
        map.put("2", "测试");
        Thread.sleep(6000);
        System.out.println(map);
    }

}
