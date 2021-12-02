package com.qifeng.will;

import org.apache.poi.util.Internal;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.function.*;

interface Interface2{
    String format(int money);
}
class Dog{
    private static String name = "啊黄";

    private static String categoroy = "yellow";

    public Dog() {
    }
    public Dog(String name) {
        System.out.println("有参构造，Dog:"+name);
        name = name;
    }
    public Dog(String name, String categoroy) {
        System.out.println("有参构造，Dog:"+name+",种类："+categoroy);
        name = name;
        categoroy = categoroy;
    }
    public void bark(Dog this){
        System.out.println(name+" bark..");
    }

    public static String eat(String dx){
        System.out.println(name+" eat.."+dx);
        return "eateat";
    }

    public static String play(int  num, String other){
        System.out.println(name+" play with.."+other+",编号:"+num);
        return "happy";
    }
}
public class StreamOfProgram {

    private int moey = 10;
    public void st1(Interface2 interface2){
        System.out.println("money format="+interface2.format(moey));
    }
    public void st2(Function<Integer, String> interface2){
        System.out.println("money format2="+interface2.apply(moey));
    }

    public static void main(String[] args) {
        StreamOfProgram so = new StreamOfProgram();
        so.st1(i->new DecimalFormat("#,###").format(i));

        Function<Integer, String> money = i -> String.valueOf(i);
        so.st2(money.andThen(s->"人命币 "+s));


        //断言函数接口
        Predicate<Integer> p = i-> i>0;
        System.out.println(p.test(1));
        System.out.println(p.negate().test(1));

        //消费类函数接口
        Consumer<String> consumer = s-> System.out.println(s);
        Consumer<String> consumers = s-> System.out.println(s=s.toUpperCase());
        consumer.accept("8999willson.zou");
        consumer.andThen(consumers).accept("22228999willson.zou");

        //非静态方法
        //IntConsumer
        Consumer<Dog> dogConsumer = Dog::bark;
        dogConsumer.accept(new Dog());

        //静态方法
        Function<String, String> eat = Dog::eat;
        eat.apply("apple");

        //IntBinaryOperator
        UnaryOperator<String> unaryOperator = Dog::eat;
        unaryOperator.apply("orange");

        //使用类名方法引用
        BiFunction<Integer, String, String> biFunction = Dog::play;
        System.out.println(biFunction.apply(2, "tom"));

        //构造函数的方法引用
        Supplier<Dog> dogSupplier = Dog::new;
        System.out.println(dogSupplier.get());
        //有参数构造函数
        Function<String,Dog> dogConst = Dog::new;
        System.out.println(dogConst.apply("哮天犬"));

        //2个参数

        BiFunction<String, String, Dog> dogConst2args = Dog::new;
        System.out.println(dogConst2args.apply("哮天犬", "air"));
    }
}
