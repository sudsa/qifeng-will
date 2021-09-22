package com.hanxiaozhang.basicknowledgecode;



import com.hanxiaozhang.basicknowledgecode.commomentity.StudentDO;
import com.hanxiaozhang.basicknowledgecode.commomentity.StudentVO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈功能简述〉<br> 
 * 〈JDK8新特性流的使用〉
 *
 * @author han
 * @create 2019/6/5
 * @since 1.0.0
 */
public class StreamCode {


    public static void main(String[] args) {

        List<StudentVO> studentList=new ArrayList<>();
        studentList.add(new StudentVO("1","小张","1",8,null,"北京顺义"));
        studentList.add(new StudentVO("2","小韩","2",14,null,"北京顺义"));
        studentList.add(new StudentVO("3","小王","1",20,null,"北京密云"));
        studentList.add(new StudentVO("4","小周","1",13,null,"北京通州"));
        studentList.add(new StudentVO("4","小李","2",7,null,"北京平谷"));


        //##流的操作
        //#中间操作：
        //map()用法：
        //用法一：
        List<String> addressList=studentList.stream().map(StudentVO::getAddress).collect(Collectors.toList());
        System.out.println("map()用法一： "+addressList.toString());
        //用法二：
        List<Integer> ageList=studentList.stream().map(x ->x.getAge()).collect(Collectors.toList());
        System.out.println("map()用法二： "+ageList.toString());
        //用法三：
        List<StudentDO> newStudentList=studentList.stream().map(x ->{
            StudentDO studentDO = new StudentDO();
            studentDO.setId(x.getId());
            studentDO.setName(x.getName());
            studentDO.setSex(x.getSex());
            return studentDO;
        }).collect(Collectors.toList());
        System.out.println("map()用法三： "+newStudentList.toString());

        //sorted()用法：
        //自然排序:(StudentVO需要实现Comparable<E>接口，重写compareTo()方法)
        List<StudentVO> studentVOAscList=studentList.stream().sorted().collect(Collectors.toList());
        System.out.println("自然排序: "+studentVOAscList.toString());
        //自然序逆序:
        List<StudentVO> studentVODescList=studentList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        System.out.println("自然序逆序: "+studentVODescList.toString());
        //自定义升序：
        List<StudentVO> studentVOSortedAscList=studentList.stream().sorted(Comparator.comparing(StudentVO::getSex)).collect(Collectors.toList());
        System.out.println("自定义升序： "+studentVOSortedAscList.toString());
        //自定义降序：
        List<StudentVO> studentVOSortedDescList=studentList.stream().sorted(Comparator.comparing(StudentVO::getSex).reversed()).collect(Collectors.toList());
        System.out.println("自定义降序： "+studentVOSortedDescList.toString());

        //filter()（过滤）用法：
        List<StudentVO> studentVOFilterList1=studentList.stream().filter(obj ->"北京顺义".equals(obj.getAddress())).collect(Collectors.toList());
        System.out.println("北京顺义的学生： "+studentVOFilterList1.toString());
        List<StudentVO> studentVOFilterList2=studentList.stream().filter(obj ->obj.getAge()>=18).collect(Collectors.toList());
        System.out.println("成年的学生： "+studentVOFilterList2.toString());

        //filter()（过滤）用法与map结合
        List<String> collect = studentList.stream().filter(obj -> "北京顺义".equals(obj.getAddress())).collect(Collectors.toList()).stream().map(x -> x.getName()).collect(Collectors.toList());
        List<String> collect1 = studentList.stream().filter(obj -> "北京顺义".equals(obj.getAddress())).map(x -> x.getName()).collect(Collectors.toList());
        System.out.println("北京顺义的学生名称： "+collect.toString());
        System.out.println("北京顺义的学生名称1： "+collect1.toString());


        //distinct()(去重)用法:
        List<StudentVO> studentVODistinctList=studentList.stream().distinct().collect(Collectors.toList());

        //limit()（截取）用法: limit(Long MaxSize)
        List<StudentVO> studentVOLimitList=studentList.stream().limit(2).collect(Collectors.toList());

        //skip()（跳过）用法：skip(Long n)
        List<StudentVO> studentVOSkipList=studentList.stream().skip(2).collect(Collectors.toList());

        //flatMap()合并多个流
        //https://blog.csdn.net/Mark_Chao/article/details/80810030
        //案例：对给定单词列表 ["Hello","World"],你想返回列表["H","e","l","o","W","r","d"],结论例1不能完成
        //例1：结果[H, e, l, l, o][W, o, r, l, d]
        String[] words = new String[]{"Hello","World"};
        System.out.print("例1结果:");
        List<String[]> a1 = Arrays.stream(words)
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(Arrays.toString(a1.get(0))+Arrays.toString(a1.get(1)));

        //例2:结果[H, e, l, l, o, W, o, r, l, d]
        System.out.print("例2结果:");
        List<String> a2 = Arrays.stream(words)
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        System.out.println(a2.toString());

        //归约：
        //流由一个个元素组成，归约就是将一个个元素“折叠”成一个值，如求和、求最值、求平均值都是归约操作。
        // 元素求和：
        // 方式一：自定义Lambda表达式求和
        int age1 = studentList.stream().map(StudentVO::getAge).reduce(0, (x1,x2)->x1+x2);
        System.out.println("自定义Lambda表达式求和: "+age1);

        //方式二：使用Integer.sum函数求和
        int age2 = studentList.stream().map(StudentVO::getAge).reduce(0,Integer::sum);
        System.out.println("使用Integer.sum函数求和: "+age2);
        //注：Integer类还提供了min、max等一系列数值操作，当流中元素为数值类型时可以直接使用

        //方式三：使用数值流求和
        int age3 = studentList.stream().mapToInt(StudentVO::getAge).sum();
        System.out.println("使用数值流求和: "+age3);
        //注：
        // 将普通流转换成数值流的方法：mapToInt、mapToDouble、mapToLong
        // 每种数值流都提供了数值计算函数，如max、min、sum等


        //#结束操作
        //forEach()用法：
        studentList.stream().forEach(x ->x.setAddress(x.getName()+":"+x.getPhone()));
        System.out.println("forEach()用法： "+studentList.toString());



        //##收集器
        //收集器是对流经过筛选、映射、去重等中间操作进行后的整理，以不同的形式展现。
        //collect()即为收集器，它接收Collector接口的实现作为具体收集器的收集方法。
        //#归约：
        //计数：
        long count1 = studentList.stream().collect(Collectors.counting());
        long count2= studentList.stream().count();
        long count3= studentList.size();
        System.out.println("计数："+"count1: "+count1+" count2: "+count2+" count3: "+count3);

        //最值:
        //例1：年龄最大的学生
        Optional<StudentVO> oldStudent =studentList.stream().collect(Collectors.maxBy(Comparator.comparingInt(StudentVO::getAge)));
        //例2：年龄最小的学生
        Optional<StudentVO> youngStudent =studentList.stream().collect(Collectors.minBy(Comparator.comparingInt(StudentVO::getAge)));
        System.out.println("年龄最大的学生: "+oldStudent.toString()+" 年龄最小的学生： "+youngStudent.toString());

        //求和:
        //例：计算所有学生年龄总和
        int sum = studentList.stream().collect(Collectors.summingInt(StudentVO::getAge));
        System.out.println("所有学生年龄总和: "+sum);
        //注：Java8提供了summingInt、summingLong、summingDouble

        //平均值:
        //例：计算所有学生的年龄平均值
        double avg  = studentList.stream().collect(Collectors.averagingInt(StudentVO::getAge));
        System.out.println("所有学生的年龄平均值: "+avg);
        //注：计算平均值时，不论计算对象是int、long、double，计算结果一定都是double

        //一次性计算所有归约操作:
        //Collectors.summarizingInt函数能一次性将最值、均值、总和、元素个数全部计算出来，并存储在对象IntSummaryStatistics中
        IntSummaryStatistics collectList = studentList.stream().collect(Collectors.summarizingInt(StudentVO::getAge));
        collectList.getCount();
        collectList.getMin();
        collectList.getMax();
        collectList.getSum();
        collectList.getAverage();
        System.out.println("一次性计算所有归约操作: "+collectList.toString());

        //连接字符串:
        //例：指定分隔符，连接姓名字符串
        String names = studentList.stream().map(StudentVO::getName).collect(Collectors.joining(", "));
        System.out.println("指定分隔符，连接姓名字符串: "+names);

        //一般性的归约操作（自定义一个归约操作）：以后细研究，先学会基本使用
        //自定义归约操作，需要使用Collectors.reducing函数，该函数接收三个参数：
        //第一个参数为归约的初始值、第二个参数为归约操作进行的字段、第三个参数为归约操作的过程
        //例：计算所有学生年龄总和
        Integer sumAge = studentList.stream().collect(Collectors.reducing(0, StudentVO::getAge, (i, j) -> i + j));
        System.out.println("所有学生年龄总: "+sumAge);

        //#分组：
        //将流中的元素按照指定类别进行划分，类似于SQL语句中的GROUPBY。
        //一级分组：
        //例：将所有学生分为小学、初中、高中、大学
        //注：返回Map<String,List<StudentVO>>类型
        Map<String,List<StudentVO>> result1 = studentList.stream()
                .collect(Collectors.groupingBy((studentVO)->{
                    if(studentVO.getAge()>=7&&studentVO.getAge()<12){
                        return "小学";
                    } else if(studentVO.getAge()>=12&&studentVO.getAge()<15){
                        return "初中";
                    }else if(studentVO.getAge()>=15&&studentVO.getAge()<18){
                        return "高中";
                    }else if(studentVO.getAge()>=18&&studentVO.getAge()<22){
                        return "大学";
                    }
                    return "其他";
                }));
        System.out.println("一级分组： "+result1.toString());

        //二级分组：
        //例：将所有学生分为小学、初中、高中、大学，并且按男女分组
        //注：返回Map<String, Map<String, List<StudentVO>>>类型
        Map<String, Map<String, List<StudentVO>>> result2 = studentList.stream()
                .collect(Collectors.groupingBy((studentVO) -> {
                            if (studentVO.getAge() >= 7 && studentVO.getAge() < 12) {
                                return "小学";
                            } else if (studentVO.getAge() >= 12 && studentVO.getAge() < 15) {
                                return "初中";
                            } else if (studentVO.getAge() >= 15 && studentVO.getAge() < 18) {
                                return "高中";
                            } else if (studentVO.getAge() >= 18 && studentVO.getAge() < 22) {
                                return "大学";
                            }
                            return "其他";
                        }, Collectors.groupingBy(x->{
                            if(x.getSex().equals("1")){
                                return "男";
                            }
                            return "女";
                        })
                ));

        System.out.println("二级分组： "+result2.toString());

        // 分组统计数目：
        //例：将所有学生分为小学、初中、高中、大学组统计人数
        //注：返回一个Map< String,Long>类型
        Map<String, Long> result3 = studentList.stream()
                .collect(Collectors.groupingBy((studentVO) -> {
                            if (studentVO.getAge() >= 7 && studentVO.getAge() < 12) {
                                return "小学";
                            } else if (studentVO.getAge() >= 12 && studentVO.getAge() < 15) {
                                return "初中";
                            } else if (studentVO.getAge() >= 15 && studentVO.getAge() < 18) {
                                return "高中";
                            } else if (studentVO.getAge() >= 18 && studentVO.getAge() < 22) {
                                return "大学";
                            }
                            return "其他";
                        }, Collectors.counting()
                ));
        System.out.println("分组统计数目： "+result3.toString());

        //将收集器的结果转换成另一种类型：（例子看不太懂）
        //使用collectingAndThen函数包裹maxBy、minBy，将maxBy、minBy返回的Optional对象转换成StudentVO对象
        //例，将所有学生按性别划分，并计算每组最大的年龄。
        Map<String, StudentVO> result4 = studentList.stream()
                .collect(Collectors.groupingBy(x -> {
                            if (x.getSex().equals("1")) {
                                return "男";
                            }
                            return "女";
                        }, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparingInt(StudentVO::getAge)),
                        Optional::get)
                ));
        System.out.println("按性别划分，计算每组最大的年龄： "+result4.toString());

        //分区:
        //分区是分组的一种特殊情况，只能分成true、false两组。
        //分组使用partitioningBy方法，该方法返回boolean类型，返回结果为true和false的元素各分成一组。
        //partitioningBy方法返回的结果为Map< Boolean,List< T>>。
        // 此外，partitioningBy方法和groupingBy方法一样，也可以接收第二个参数，实现二级分区或对分区结果进行统计。
    }
}