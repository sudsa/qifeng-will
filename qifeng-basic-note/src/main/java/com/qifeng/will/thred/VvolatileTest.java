package com.hanxiaozhang.thred;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class VvolatileTest {
    public static volatile int race = 0;
    //volatile只保证可见性，只能保证拿到的变量一定是最新的
    // 并且线程对volatile变量一次操作只需要获取一次变量
    //“一次写入，到处读取”，某一线程负责更新变量，其他线程只读取变量(不更新变量)，并根据变量的新值执行相应逻辑

    public static AtomicInteger i = new AtomicInteger(0);
    public static int k = 0;
    public static final CountDownLatch countDownLatch = new CountDownLatch(10);

    static void add() {
        race++;
        i.incrementAndGet();
        k++;
    }

    static class VO implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 1000; j++) {
                add();
            }
            countDownLatch.countDown();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new VO());
            thread.start();
        }
        //保证前面的10条线程都执行完
        countDownLatch.await();
        System.out.println("race="+race);
        System.out.println("i="+i);
        System.out.println("k="+k);

    }


}
