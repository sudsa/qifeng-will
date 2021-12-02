package com.qifeng.will.basicknowledgecode;

import com.qifeng.will.basicknowledgecode.commomentity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.MapUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StmDevp {

    private String code;

    private int num;

    public String getUssss(){
        System.out.println("no args=::::");
        return "no ars";
    }

    public String getUssss(String param){
        System.out.println("invoke=::::"+param);
        return param;
    }

    public void getUssss(String[] array){
        for(String arr : array){
            System.out.println("noarrgs:"+arr);
        }
    }
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
                new Student("6", "奇虎360", "1", "1989", "重庆市"),
                new Student("3", "杨琦", "0", "1992", "杭州市"),
                new Student("4", "奇虎", "1", "1959", "深圳市"),
                new Student("7", "旗峰", "1", "1998", "深圳市"),
                new Student("5", "虎妞", "1", "1199", "北京市"));
        students.stream().sorted(Comparator.comparing(Student::getPhone)).forEach(System.out::println);
        students.stream().sorted(Comparator.comparing(Student::getAddress).reversed()).forEach(System.out::println);

        Map<String, Student> stringStudentMap =
                students.stream().collect(Collectors.groupingBy(Student::getAddress, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(e->Integer.valueOf(e.getPhone()))), Optional::get)));

        System.out.println(stringStudentMap);

        intStream();

        strOfReduce();

        strOfReduce2();

        strOfReduce3();

        strOfReduce4();

        strOfReduce5();

        arraysAsList();
    }

    public static void intStream(){
        int[] nums = {1,2,3,45,44,87};
        int sum = IntStream.of(nums).map(StmDevp::doubleto).sum();
        System.out.println("num求和"+sum);
    }

    public static int doubleto(int i){
        return i*2;
    }

    public static void strOfReduce(){
        String str = "I am a Ceateor of TECH";
        String reduceStr = Stream.of(str.split(" ")).reduce((s1,s2)->s1+"||"+s2).get();
        System.out.println(reduceStr);
    }
    public static void strOfReduce2(){
        String str = "I am a Ceateor of TECH";
        String reduceStr = Stream.of(str.split(" ")).reduce("jinju:",(s1,s2)->s1+"||"+s2).toUpperCase();
        System.out.println(reduceStr);
    }

    public static void strOfReduce3(){
        ArrayList<Integer> newList = new ArrayList<>();
        newList.add(99);
        ArrayList<Integer> accResult_ = Stream.of(2, 3, 4)
            .reduce(newList,
                    (acc, item) -> {
                        acc.add(item);
                        System.out.println("item: " + item);
                        System.out.println("acc+ : " + acc);
                        System.out.println("BiFunction");
                        return acc;
                    }, (acc, item) -> null);
        System.out.println("accResult_: " + accResult_);
    }


    public static void strOfReduce4() {
        int accResult = Stream.of(1, 2, 3, 4)
                .reduce(100, (acc, item) -> {
                    System.out.println("acc : " + acc);
                    acc += item;
                    System.out.println("item: " + item);
                    System.out.println("acc+ : " + acc);
                    System.out.println("--------");
                    return acc;
                });
        System.out.println(accResult);
    }

    public static void strOfReduce5() {
        //调用parallel产生一个并行流
        long accResult =IntStream.range(1,100).parallel().peek(StmDevp::debug).count();
        System.out.println(accResult);
    }

    public static int debug(int i) {
        System.out.println(Thread.currentThread().getName()+"i="+i);

        try {
            //TimeUnit.SECONDS.sleep(3);
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.getMessage();
        }
        return i;
    }

    public static void strOfReduce6() {
        //调用parallel产生一个并行流
        long accResult =IntStream.range(1,100).parallel().sequential().peek(StmDevp::debug).count();
        System.out.println(accResult);
    }

    public static void arraysAsList(){
        List<Student> list = Arrays.asList(new Student("2", "刘杨", "0", "136", "三亚市"),
                new Student("6", "奇虎360", "1", "139", "九江市"),
                new Student("67", "奇虎360", "1", "153", "四川省"),
                new Student("8", "奇虎360", "1", "188", "六安市"),
                new Student("9", "奇虎360", "1", "199", "十堰市"));

        //得到所有学生手机号码
        Set<String> set = list.parallelStream().map(Student::getPhone).collect(Collectors.toCollection(TreeSet::new));

        //统计汇总信息
        IntSummaryStatistics summaryStatistics = list.stream().collect(Collectors.summarizingInt(e->Integer.valueOf(e.getPhone())));
        System.out.println(summaryStatistics.getAverage());
        System.out.println(summaryStatistics.getCount());
        System.out.println(summaryStatistics.getSum());
        System.out.println(summaryStatistics.getMin());
        System.out.println(summaryStatistics.getMax());
        summaryStatistics.accept(33);
        System.out.println(summaryStatistics);

        //分块
        Map<Boolean, List<Student>> uu = list.stream().collect(Collectors.partitioningBy(e->e.getSex().equals("1")));
        System.out.println(uu);

        //分组
        Map<String, List<Student>> groupBy = list.stream().collect(Collectors.groupingBy(Student::getAddress));
        System.out.println(groupBy);

        MapUtils.verbosePrint(System.out, "根据地址分组", groupBy);

        //得到所有地区学生个数
        Map<String, Long> countBy = list.stream().collect(Collectors.groupingBy(Student::getAddress, Collectors.counting()));
        MapUtils.verbosePrint(System.out, "得到所有地区学生个数", countBy);

        Random random = new Random();
        Long count = Stream.generate(()->random.nextInt())
                .limit(500)
                .peek(s->System.out.println(Thread.currentThread().getName()+" s:"+s))
                .filter(s->s>50000)
                .sorted((s1,s2)->{
                    System.out.println(Thread.currentThread().getName()+" s1:"+s1+"s2:"+s2);
                    return s1.compareTo(s2);
                })
                .peek(s->{
                    System.out.println(Thread.currentThread().getName()+" s:"+s);
                }).parallel()
                .count();

        System.out.println("大于50000count="+count);
    }
}
