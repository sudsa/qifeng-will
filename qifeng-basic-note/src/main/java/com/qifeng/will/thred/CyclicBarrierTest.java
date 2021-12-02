package com.qifeng.will.thred;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/*
 *功能描述
 * CyclicBarrier计数达到指定后会重新循环使用，所以CyclicBarrier可以用在所有子线程之间互相等待多次的情
 * 比如在某种需求中，比如一个大型的任务，常常需要分配好多子任务去执行，
 * 只有当所有子任务都执行完成时候，才能执行主任务，这时候，就可以选择CyclicBarrier了。
 * 比如团队旅游，一个团队通常分为几组，每组人走的路线可能不同，
 * 但都需要到达某一地点等待团队其它成员到达后才能进行下一站。
 * @date 2021/10/14
 * @param
 * @return
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {

        TotalService totalService = new TotalService();
        CyclicBarrier barrier = new CyclicBarrier(5,
                new TotalTask(totalService));

        // 实际系统是查出所有省编码code的列表，然后循环，每个code生成一个线程。
        new BillTask(new BillService(), barrier, "北京").start();
        new BillTask(new BillService(), barrier, "上海").start();
        new BillTask(new BillService(), barrier, "广西").start();
        new BillTask(new BillService(), barrier, "四川").start();
        new BillTask(new BillService(), barrier, "黑龙江").start();

    }


    /**
     * 主任务：汇总任务
     */
    static class TotalTask implements Runnable {

        private TotalService totalService;

        TotalTask(TotalService totalService) {
            this.totalService = totalService;
        }

        public void run() {
            // 读取内存中各省的数据汇总，过程略。
            totalService.count();
            System.out.println("=======================================");
            System.out.println("开始全国汇总");
        }
    }


    /**
     * 子任务：计费任务
     */
    static class BillTask extends Thread {
        // 计费服务
        private BillService billService;
        private CyclicBarrier barrier;
        // 代码，按省代码分类，各省数据库独立。
        private String code;

        BillTask(BillService billService, CyclicBarrier barrier, String code) {
            this.billService = billService;
            this.barrier = barrier;
            this.code = code;
        }

        public void run() {
            System.out.println("开始计算--" + code + "省--数据！");
            billService.bill(code);
            // 把bill方法结果存入内存，如ConcurrentHashMap,vector等,代码略
            System.out.println(code + "省已经计算完成,并通知汇总Service！");
            try {
                // 通知barrier已经完成
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

    }


}
