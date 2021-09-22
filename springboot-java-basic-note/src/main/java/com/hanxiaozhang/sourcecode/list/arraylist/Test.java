package com.hanxiaozhang.sourcecode.list.arraylist;

import java.util.Arrays;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author hanxinghua
 * @create 2020/5/2
 * @since 1.0.0
 */
public class Test {

    public static void main(String[] args) {

//        test();
//        test1();
//        test2();
        test3();

    }

    private static void test() {
        List<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.remove(2);
////        list.add("e");
////        list.add("f");
////        list.add("g");
//        List<String> list1 = new MyArrayList<>();
////        list1.add("a");
////        list1.add("b");
////        list1.add("c");
////        list1.add("d");
////        list.removeAll(list1);
//        list.retainAll(list1);
        System.out.println(list.size());
    }

    /**
     * 扩容1.5倍测试
     * int newCapacity = oldCapacity + (oldCapacity >> 1);
     *
     */
    public  static  void  test1(){
        int oldCapacity=10;
        System.out.println("oldCapacity ==>转化成二进制: "+Integer.toBinaryString(oldCapacity));
        int rightCapacity=oldCapacity >> 1;
        System.out.println("oldCapacity >> 1  ==>转化成二进制: "+Integer.toBinaryString(rightCapacity));
        System.out.println("oldCapacity >> 1的值: "+rightCapacity);
        int leftCapacity=oldCapacity << 1;
        System.out.println("oldCapacity << 1  ==>转化成二进制: "+Integer.toBinaryString(leftCapacity));
        System.out.println("oldCapacity << 1的值: "+leftCapacity);
    }


    /**
     * 验证一下++i与i++
     *
     */
    public  static  void  test2(){

        int[] array = new int[]{0,1,2,3,4,5,6,7,8,9,10};
        int index=3;
        array[index++]=0;
//        array[++index]=0;
        System.out.println(Arrays.toString(array));


    }

    /**
     * 测试fail-fast机制
     *
     */
    public static  void test3(){
        List<String> list = new MyArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("g");
//        for (int i = 0; i < list.size(); i++) {
//            list.remove(list.get(i));
//        }
        for (String str:list) {
            list.remove(str);
        }
//        list.forEach(x->{
//            list.remove(x);
//        });

    }


}
