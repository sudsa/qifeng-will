package com.qifeng.will.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 功能描述: <br>
 * 〈〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/9
 */
public class WatchCallBack implements Watcher, AsyncCallback.StringCallback, AsyncCallback.Children2Callback, AsyncCallback.StatCallback {


    private ZooKeeper zk;
    private String threadName;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    private String pathName;

    public WatchCallBack(ZooKeeper zk, String threadName) {
        this.zk = zk;
        this.threadName = threadName;
    }

    /**
     * 获取锁
     */
    public void tryLock() {
        try {

            System.out.println(threadName + "  create....");
//            if(zk.getData("/"))
            zk.create("/lock", threadName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, this, "abc");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /**
     * 释放锁
     */
    public void unLock() {
        try {
            zk.delete(pathName, -1);
            System.out.println(threadName + " over work....");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }



    @Override
    public void process(WatchedEvent event) {


        //如果第一个哥们，那个锁释放了，其实只有第二个收到了回调事件！！
        //如果，不是第一个哥们，某一个，挂了，也能造成他后边的收到这个通知，从而让他后边那个跟去watch挂掉这个哥们前边的。。。
        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                break;
            case NodeDeleted:
                zk.getChildren("/", false, this, "sdf");
                break;
            case NodeDataChanged:
                break;
            case NodeChildrenChanged:
                break;
        }

    }

    /**
     *  zk.create()对应的 callback
     *
     * @param rc
     * @param path
     * @param ctx
     * @param name
     */
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        // 创建lock节点成功
        if (name != null) {
            System.out.println(threadName + "  create node : " + name);
            pathName = name;
            zk.getChildren("/", false, this, "sdf");
        }

    }


    /**
     * getChildren  的 callback
     *
     *
     * @param rc
     * @param path
     * @param ctx
     * @param children
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {

        // 一定能看到自己前边的节点
        System.out.println(threadName+"look locks.....");

         // 排序
        Collections.sort(children);
        int i = children.indexOf(pathName.substring(1));

        //判断是不是第一个
        if (i == 0) {
            //yes
            System.out.println(threadName + " i am first....");
            try {
                zk.setData("/", threadName.getBytes(), -1);
                countDownLatch.countDown();
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            //no
            zk.exists("/" + children.get(i - 1), this, this, "sdf");
        }

    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        //偷懒
    }


}
