package com.hanxiaozhang.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * 〈一句话功能简述〉<br>
 * 〈分布式锁测试〉
 * <p>
 * <p>
 * https://blog.csdn.net/qq_35723073/article/details/107827531
 *
 * @author hanxinghua
 * @create 2021/8/4
 * @since 1.0.0
 */
public class CuratorDistributedLockTest {

    private static final String ZK_LOCK_PATH = "/curatorLockTest";

    public static void main(String[] args) throws Exception {

        CuratorUtil.startClient_1();
        CuratorFramework client = CuratorUtil.getClient();

        Thread t1 = new Thread(() -> {
            doWithLock(client);
        }, "t1");

        Thread t2 = new Thread(() -> {
            doWithLock(client);
        }, "t2");
        t1.start();
        t2.start();


    }

    private static void doWithLock(CuratorFramework client) {
        InterProcessMutex lock = new InterProcessMutex(client, ZK_LOCK_PATH);
        try {
            if (lock.acquire(10 * 1000, TimeUnit.SECONDS)) {
                System.out.println(Thread.currentThread().getName() + " hold lock");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " release lock");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                lock.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
