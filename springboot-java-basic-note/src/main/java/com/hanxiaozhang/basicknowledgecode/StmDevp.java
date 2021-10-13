package com.hanxiaozhang.basicknowledgecode;

import com.hanxiaozhang.basicknowledgecode.commomentity.Student;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class StmDevp {

    public static void main(String[] args){
        //打印出集合中的偶数
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8);
        list.stream().filter(num->num%2==0).forEach(System.out::println);
        //打印集合中的字符串，去掉重复的字符串
        List<String> string = Arrays.asList("abc","happy","hello","world","abc","hello");
        string.stream().distinct().forEach(System.out::println );
        //打印集合跳过前两个的前三个元素
        List<Integer> list1 = Arrays.asList(11,12,13,14,15,16,17,18);
        list1.stream().skip(2).limit(3).forEach(System.out::println);
        System.out.println("list1-sieze="+list1.size());

        List<Student> students = Arrays.asList(new Student("1", "王柳", "1", "199", "武汉市"),
                new Student("2", "刘杨", "0", "1949", "重庆市"),
                new Student("3", "杨琦", "0", "1992", "杭州市"),
                new Student("4", "奇虎", "1", "1959", "深圳市"),
                new Student("5", "虎妞", "1", "1199", "北京市"));
        students.stream().sorted(Comparator.comparing(Student::getPhone)).forEach(System.out::println);
        students.stream().sorted(Comparator.comparing(Student::getAddress).reversed()).forEach(System.out::println);
    }

}
