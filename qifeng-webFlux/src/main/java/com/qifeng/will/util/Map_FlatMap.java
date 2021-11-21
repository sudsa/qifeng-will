package com.qifeng.will.util;


import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Map_FlatMap {

    List<String[]> eggs = new ArrayList<>();

    @Before
    public void init() {
        // 第一箱鸡蛋
        eggs.add(new String[]{"鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1", "鸡蛋_1"});
        // 第二箱鸡蛋
        eggs.add(new String[]{"鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2", "鸡蛋_2"});
    }

    // 自增生成组编号
    static int group = 1;
    // 自增生成学生编号
    static int student = 1;

    /**
     * 把二箱鸡蛋分别加工成煎蛋，还是放在原来的两箱，分给2组学生
     * map方法返回的是一个object，map将流中的当前元素替换为此返回值
     */
    @Test
    public void map() {
        eggs.stream()
                .map(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("组" + group++ + ":" + Arrays.toString(x.toArray())));
        /*
        控制台打印：------------
        组1:[煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1, 煎蛋_1]
        组2:[煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2, 煎蛋_2]
         */
    }

    /**
     * 把二箱鸡蛋分别加工成煎蛋，然后放到一起【10个煎蛋】，分给10个学生
     * flatMap方法返回的是一个stream，flatMap将流中的当前元素替换为此返回流拆解的流元素
     */
    @Test
    public void flatMap() {
        eggs.stream()
                .flatMap(x -> Arrays.stream(x).map(y -> y.replace("鸡", "煎")))
                .forEach(x -> System.out.println("学生" + student++ + ":" + x));
        /*
        控制台打印：------------
        学生1:煎蛋_1
        学生2:煎蛋_1
        学生3:煎蛋_1
        学生4:煎蛋_1
        学生5:煎蛋_1
        学生6:煎蛋_2
        学生7:煎蛋_2
        学生8:煎蛋_2
        学生9:煎蛋_2
        学生10:煎蛋_2
         */
    }
}
