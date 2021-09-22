package com.hanxiaozhang.configcenter;

import org.apache.zookeeper.ZooKeeper;

import java.util.concurrent.CountDownLatch;

/**
 * 功能描述: <br>
 * 〈zk工具类〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/8
 */
public class ZookeeperUtils {

    private static ZooKeeper zk;

    /**
     * 配置中心的根节点configCenter
     *
     */
    private static String address = "127.0.0.1:2181/configCenter";

    private static CountDownLatch init = new CountDownLatch(1);

    private static DefaultWatch watch = new DefaultWatch(init);


    /**
     * 获取Zk
     *
     * @return
     */
    public static ZooKeeper getZooKeeper() {

        try {
            zk = new ZooKeeper(address, 1000, watch);
            init.await();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return zk;
    }


}
