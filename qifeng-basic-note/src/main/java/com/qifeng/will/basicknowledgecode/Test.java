package com.qifeng.will.basicknowledgecode;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {

    @org.junit.Test
    public void test() throws FileNotFoundException {
        BufferedReader br = new BufferedReader(new FileReader(new File("D:\\logdir\\logs\\gateway-api\\2021-09-23\\log.0.txt")));
        System.out.println("********************************************start file***********************************");


        br.lines().filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList()).forEach(System.out::println);

        System.out.println("********************************************end file***********************************");

        new Random().ints(8).forEach(System.out::println);

        System.out.println(IntStream.of(new int[]{1, 2, 3}).sum());

        List<List<Integer>> ints=new ArrayList<>(Arrays.asList(Arrays.asList(1,2),
                Arrays.asList(3,4,5)));
        System.out.println(ints);
        //flatmap扁平化映射
        //将多个stream连接成一个stream
        //这个操作针对多维数组，比如集合里面包含集合
        List<Integer> flatInts=ints.stream().flatMap(Collection::stream).
                collect(Collectors.toList());
        System.out.println("降维"+flatInts);



    }
}
