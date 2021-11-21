package com.hanxiaozhang.test;

/**
 * 〈一句话功能简述〉<br>
 * 〈接口测试〉
 *
 * @author hanxinghua
 * @create 2020/5/11
 * @since 1.0.0
 */
public interface TestInterface {

    /**
     * 方法1
     */
    default void  test1(){
        System.out.println("接口默认实现test1");
    }

    void test2();
}
