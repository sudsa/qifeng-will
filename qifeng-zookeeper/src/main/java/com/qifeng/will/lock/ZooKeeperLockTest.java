package com.qifeng.will.lock;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 功能描述: <br>
 * 〈分布式锁〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/9
 */
public class ZooKeeperLockTest {


    private ZooKeeper zk;

    @Before
    public void conn() {
        zk = ZookeeperUtils.getZooKeeper();
    }

    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void lock() {

        for (int i = 0; i < 10; i++) {
            new Thread() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    WatchCallBack watchCallBack = new WatchCallBack(zk,threadName);
                    //每一个线程：
                    //抢锁
                    watchCallBack.tryLock();
                    //干活
                    System.out.println(threadName + " working...");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //释放锁
                    watchCallBack.unLock();
                }
            }.start();
        }



        while (true) {

        }


    }


}
