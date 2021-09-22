package com.hanxiaozhang.configcenter;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * 功能描述: <br>
 * 〈Watch和回调〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/8
 */
public class WatchCallBack implements Watcher, AsyncCallback.StatCallback, AsyncCallback.DataCallback {


    private ZooKeeper zk;
    private ConfigProperties configProperties;
    CountDownLatch countDownLatch = new CountDownLatch(1);


    public WatchCallBack(ZooKeeper zk, ConfigProperties configProperties) {
        this.zk = zk;
        this.configProperties = configProperties;
    }


    /**
     * 异步判断/AppConfig节点是否存在，调用--> callback  1
     */
    public void aWait() {
        zk.exists("/AppConfig", this, this, "ABC");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * callback 2 中，将节点数据赋值到
     *
     * @param rc   返回调用的结构，返回OK或与KeeperException异常对应的编码值
     * @param path  传递给异步调用的路径
     * @param ctx   传递给异步调用的上下文对象
     * @param data  节点的数据
     * @param stat  节点的状态
     */
    @Override
    public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {

        if (data != null) {
            configProperties.setConf(new String(data));
            countDownLatch.countDown();
        }

    }

    /**
     * callback  1 中，又异步获取/AppConfig节点数据，调用--> callback  2
     *
     * @param rc
     * @param path
     * @param ctx
     * @param stat
     */
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        if (stat != null) {
            zk.getData("/AppConfig", this, this, "sdfs");
        }

    }

    /**
     * Watcher
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {

        switch (event.getType()) {
            case None:
                break;
            case NodeCreated:
                // 节点数据被创建时，获取数据
                zk.getData("/AppConfig", this, this, "sdfs");
                break;
            case NodeDeleted:
                // 节点数据删除，考虑容忍性（容忍不容忍被删除）
                // 一种处理，设置为空
                configProperties.setConf("");
                countDownLatch = new CountDownLatch(1);
                break;
            case NodeDataChanged:
                // 节点数据变更时，重新获取数据
                zk.getData("/AppConfig", this, this, "sdfs");
                break;
            case NodeChildrenChanged:
                break;
        }

    }
}
