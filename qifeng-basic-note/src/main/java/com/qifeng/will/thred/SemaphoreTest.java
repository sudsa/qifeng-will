package com.qifeng.will.thred;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/*
 *功能描述
 Semaphore也是一个线程同步的辅助类，可以维护当前访问自身的线程个数，并提供了同步机制。使用Semaphore可以控制同时访问资源的线程个数，例如，实现一个文件允许的并发访问数。

Semaphore的主要方法摘要：

　　void acquire():从此信号量获取一个许可，在提供一个许可前一直将线程阻塞，否则线程被中断。

　　void release():释放一个许可，将其返回给信号量。

　　int availablePermits():返回此信号量中当前可用的许可数。

　　boolean hasQueuedThreads():查询是否有线程正在等待获取。

 * @author zouhw02
 * @date 2021/10/14
 * @param
 * @return
 */
public class SemaphoreTest {

    /*
    Semaphore可以用于做流量控制，特别公用资源有限的应用场景，
    比如数据库连接。假如有一个需求，要读取几万个文件的数据，
    因为都是IO密集型任务，我们可以启动几十个线程并发的读取，
    但是如果读到内存后，还需要存储到数据库中，
    而数据库的连接数只有10个，这时我们必须控制只有十个线程同时获取数据库连接保存数据，
    否则会报错无法获取数据库连接。这个时候，我们就可以使用Semaphore来做流控
     */
    private static final int THREAD_COUNT = 30;
    private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_COUNT);
    private static Semaphore s = new Semaphore(10);
    public static void main(String[] args) {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        s.acquire();
                        System.out.println("save data");
                        s.release();
                    } catch (InterruptedException e) {
                    }
                }
            });
        }
        threadPool.shutdown();
    }

}
