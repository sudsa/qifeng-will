package com.hanxiaozhang.basicknowledgecode.inter;

import java.util.function.Consumer;

@FunctionalInterface
public interface Interface1 {
    int numaAdd(int a, int b);
}
class InterTest{

    public static void main(String[] args) {
        Interface1 interface1 = (x, y)-> x+y;
        //array
        Interface1 []interfaces = {(x, y)-> x+y};
        //强转
        Object obj = (Interface1)(x, y)-> x+y;

        //通过返回类型
        Interface1 inters = createLamda();

        System.out.println(test((x, y) -> x + y));
        String str = "我们的青春：";
        Consumer<String> consumer = s -> System.out.println(str +s);
        consumer.accept("终将失去");
    }

    public static Interface1 createLamda(){
        return (x,y)->x+y;
    }

    public static int test(Interface1 interface1){
        return interface1.numaAdd(3,4);
    }
}