package com.hanxiaozhang.basicknowledgecode;



import com.google.common.collect.Lists;
import com.hanxiaozhang.basicknowledgecode.commomentity.StudentDO;
import com.hanxiaozhang.basicknowledgecode.commomentity.StudentVO;
import lombok.Data;

import java.math.BigDecimal;
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
public class StreamCodeGroup {


    public static void mainOperate() {

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

    //Collectors.groupingBy根据一个或多个属性对集合中的项目进行分组
    public static void testgroup(){

        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        Product prod6 = new Product(6L, -10, new BigDecimal("-15"), "重庆啤酒", "啤酒");
        Product prod7 = new Product(7L, -30, new BigDecimal("1.5"), "维达", "日用");
        Product prod8 = new Product(8L, 50, new BigDecimal("15.9"), "扫把", "日用");
        Product prod9 = new Product(9L, 30, new BigDecimal("115"), "盘子", "日用");
        List<Product> prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9);

        //按照类目分组：
        Map<String, List<Product>> prodMap
                = prodList.stream().collect(Collectors.groupingBy(Product::getCategory));

        System.out.println("prodMap="+prodMap);
//{"啤酒":[{"category":"啤酒","id":4,"name":"青岛啤酒","num":3,"price":10},{"category":"啤酒","id":5,"name":"百威啤酒","num":10,"price":15}],"零食":[{"category":"零食","id":1,"name":"面包","num":1,"price":15.5},{"category":"零食","id":2,"name":"饼干","num":2,"price":20},{"category":"零食","id":3,"name":"月饼","num":3,"price":30}]}

        //求总数
        Map<String, Long> prodMaps
                = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
        System.out.println("prodMaps="+prodMaps);

        Map<String, Integer> prodMapsum
                = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.summingInt(Product::getNum)));
        System.out.println("prodMapsum="+prodMapsum);

        //按照几个属性拼接分组
        Map<String, List<Product>> prodMapconcat = prodList.stream().collect(Collectors.groupingBy(item -> item.getCategory() + "_" + item.getName()));
        System.out.println("prodMapconcat="+prodMapconcat);
//{"零食_月饼":[{"category":"零食","id":3,"name":"月饼","num":3,"price":30}],"零食_面包":[{"category":"零食","id":1,"name":"面包","num":1,"price":15.5}],"啤酒_百威啤酒":[{"category":"啤酒","id":5,"name":"百威啤酒","num":10,"price":15}],"啤酒_青岛啤酒":[{"category":"啤酒","id":4,"name":"青岛啤酒","num":3,"price":10}],"零食_饼干":[{"category":"零食","id":2,"name":"饼干","num":2,"price":20}]}

//根据不同条件分组
        Map<String, List<Product>> prodMapother= prodList.stream().collect(Collectors.groupingBy(item -> {
            if(item.getNum() < 3) {
                return "3";
            }else {
                return "other";
            }
        }));
        System.out.println("prodMapother="+prodMapother);
//{"other":[{"category":"零食","id":3,"name":"月饼","num":3,"price":30},{"category":"啤酒","id":4,"name":"青岛啤酒","num":3,"price":10},{"category":"啤酒","id":5,"name":"百威啤酒","num":10,"price":15}],"3":[{"category":"零食","id":1,"name":"面包","num":1,"price":15.5},{"category":"零食","id":2,"name":"饼干","num":2,"price":20}]}


        //多级分组
        Map<String, Map<String, List<Product>>> prodMapmore= prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.groupingBy(item -> {
            if(item.getNum() < 3) {
                return "3";
            }else {
                return "other";
            }
        })));
        System.out.println("prodMapmore="+prodMapmore);
//{"啤酒":{"other":[{"category":"啤酒","id":4,"name":"青岛啤酒","num":3,"price":10},{"category":"啤酒","id":5,"name":"百威啤酒","num":10,"price":15}]},"零食":{"other":[{"category":"零食","id":3,"name":"月饼","num":3,"price":30}],"3":[{"category":"零食","id":1,"name":"面包","num":1,"price":15.5},{"category":"零食","id":2,"name":"饼干","num":2,"price":20}]}}



        //按子组收集数据 求和 求总数


        //把收集器的结果转换为另一种类型
        Map<String, Product> prodMapconver = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Product::getNum)), Optional::get)));
        System.out.println("prodMapconver="+prodMapconver);
//{"啤酒":{"category":"啤酒","id":5,"name":"百威啤酒","num":10,"price":15},"零食":{"category":"零食","id":3,"name":"月饼","num":3,"price":30}}


        //联合其他搜集器
        Map<String, Set<String>> prodMapunion = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.mapping(Product::getName, Collectors.toSet())));
        System.out.println("prodMapunion="+prodMapunion);
//{"啤酒":["青岛啤酒","百威啤酒"],"零食":["面包","饼干","月饼"]}



    }



    @Data
    public static class Product{

        private Long id;
        private Integer num;
        private BigDecimal price;
        private String name;
        private String category;


        public Product(Long id, Integer num, BigDecimal price, String name, String category) {
            this.id = id;
            this.num = num;
            this.price = price;
            this.name = name;
            this.category = category;
        }


    }


    public static void main(String[] args) {

        Product prod1 = new Product(1L, 1, new BigDecimal("15.5"), "面包", "零食");
        Product prod2 = new Product(2L, 2, new BigDecimal("20"), "饼干", "零食");
        Product prod3 = new Product(3L, 3, new BigDecimal("30"), "月饼", "零食");
        Product prod4 = new Product(4L, 3, new BigDecimal("10"), "青岛啤酒", "啤酒");
        Product prod5 = new Product(5L, 10, new BigDecimal("15"), "百威啤酒", "啤酒");
        Product prod6 = new Product(6L, -10, new BigDecimal("-15"), "重庆啤酒", "啤酒");
        Product prod7 = new Product(7L, -30, new BigDecimal("1.5"), "维达", "日用");
        Product prod8 = new Product(8L, 50, new BigDecimal("15.9"), "扫把", "日用");
        Product prod9 = new Product(9L, 30, new BigDecimal("115"), "盘子", "日用");
        List<Product> prodList = Lists.newArrayList(prod1, prod2, prod3, prod4, prod5, prod6, prod7, prod8, prod9);


        Map<String, Product> prodMapconver = prodList.stream().collect(Collectors.groupingBy(Product::getCategory, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Product::getNum)), Optional::get)));
        System.out.println("prodMapconver="+prodMapconver);

    }

}