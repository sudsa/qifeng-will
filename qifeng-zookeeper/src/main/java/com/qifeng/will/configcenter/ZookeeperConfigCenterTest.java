package com.qifeng.will.configcenter;

import org.apache.zookeeper.ZooKeeper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 功能描述: <br>
 * 〈zk配置中心测试类〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/8
 */
public class ZookeeperConfigCenterTest {


    private ZooKeeper zk;


    /**
     * 测试前，链接Zk
     */
    @Before
    public void connection() {
        zk = ZookeeperUtils.getZooKeeper();
    }

    /**
     * 测试后，关闭Zk
     */
    @After
    public void close() {
        try {
            zk.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getConfig() {

        // 配置文件
        ConfigProperties myConfig = new ConfigProperties();

        WatchCallBack watchCallBack = new WatchCallBack(zk, myConfig);

        watchCallBack.aWait();


        // 可能出现的情况： 1，节点不存在 2，节点存在

        //  模拟获取配置数据，每200ms获取一次
        while (true) {

            // 如果数据不存在，调用aWait()重新获取
            if (myConfig.getConf().equals("")) {
                System.out.println("conf delete ......");
                watchCallBack.aWait();
            } else {
                System.out.println(myConfig.getConf());
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
