package com.hanxiaozhang.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static java.util.concurrent.Executors.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 * <p>
 * 不错文章：
 * https://blog.csdn.net/qq_34021712/article/details/82872311
 *
 * @author hanxinghua
 * @create 2021/8/3
 * @since 1.0.0
 */
public class CuratorBaseTest {

    public static void main(String[] args) throws Exception {

//        // 增删查改存
//         crudc();

//        // 事务
//        transaction();

//        // 异步调用1
//        asyncCallBack1();
//        // 异步调用2
//        asyncCallBack2();

//        // 设置监视点
//        watcher();

        // 监视器 Listener
        listener();


        while (true) {

        }

    }


    /**
     * 增删查改存测试
     *
     * @throws Exception
     */
    private static void crudc() throws Exception {

        CuratorUtil.startClient_1();

        CuratorUtil.create("/dataTest", "test", CreateMode.PERSISTENT);

        CuratorUtil.update("/dataTest", "test1");

        byte[] data = CuratorUtil.getData("/dataTest");
        System.out.println("getData: " + new String(data));

        Stat stat = CuratorUtil.getStat("/dataTest");
        System.out.println("getStat: " + stat.toString());

        boolean exists = CuratorUtil.checkExists("/dataTest");
        System.out.println("checkExists: " + exists);

        CuratorUtil.delete("/dataTest");

        CuratorUtil.close();
    }


    /**
     * 事务
     *
     * @throws Exception
     */
    private static void transaction() throws Exception {

        CuratorUtil.startClient_2();
        Collection<CuratorTransactionResult> results = CuratorUtil.getClient().inTransaction().check().forPath("/parent")
                .and()
                .create().withMode(CreateMode.PERSISTENT).forPath("/parent/son1", "jack".getBytes())
                .and()
                .create().withMode(CreateMode.PERSISTENT).forPath("/parent/son2", "har".getBytes())
                .and()
//                .check().forPath("data")
//                .and()
                .commit();

        for (CuratorTransactionResult result : results) {
            System.out.println(result.getResultPath());
            System.out.println(result.getResultStat());
        }

        CuratorUtil.close();
    }


    /**
     * 异步回调1
     *
     * @throws Exception
     */
    private static void asyncCallBack1() throws Exception {

        CuratorUtil.startClient_1();
        CuratorUtil.getClient().create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground()
                .forPath("/asyncData1", "test".getBytes());

        CuratorUtil.close();
    }

    /**
     * 异步回调2
     * 使用java Executor
     *
     * @throws Exception
     */
    private static void asyncCallBack2() throws Exception {

        Executor executor = newFixedThreadPool(2);
        CuratorUtil.startClient_1();
        CuratorUtil.getClient().create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .inBackground((curatorFramework, curatorEvent) -> {
                    System.out.println(String.format("eventType:%s,resultCode:%s", curatorEvent.getType(), curatorEvent.getResultCode()));
                }, executor)
                .forPath("/asyncData2", "test".getBytes());

        CuratorUtil.close();
    }


    /**
     * watcher
     *
     * @throws Exception
     */
    private static void watcher() throws Exception {

        CuratorUtil.startClient_1();
//        // 使用默认的watch
//        CuratorUtil.getClient().getData()..watched().forPath("/parent");

//        // 自定义 Watcher
//        CuratorUtil.getClient().getData().usingWatcher(new Watcher() {
//            @Override
//            public void process(WatchedEvent event) {
//                System.out.println("WatchedEvent: "+event.toString());
//            }
//        }).forPath("/parent");

        // 自定义 CuratorWatcher
        // CuratorWatcher 与 Watcher的区别是CuratorWatcher可以抛出异常
        CuratorUtil.getClient().getData().usingWatcher(new CuratorWatcher() {
            @Override
            public void process(WatchedEvent event) throws Exception {
                System.out.println("WatchedEvent: " + event.toString());
            }
        }).forPath("/parent");
        CuratorUtil.close();

    }


    /**
     * 监听器
     */
    private static void listener() {
        CuratorUtil.startClient_1();

        // 只会触发一次 ?
//        CuratorUtil.getClient().getCuratorListenable().addListener(new CuratorListener() {
//            @Override
//            public void eventReceived(CuratorFramework feign, CuratorEvent event) throws Exception {
//                System.out.println("CuratorEvent.getPath :"+event.getPath());
//                System.out.println("CuratorEvent.getType :"+event.getType());
//            }
//        });


//        CuratorUtil.registerWatcherNodeChanged("/parent", new NodeCacheListener() {
//            @Override
//            public void nodeChanged() throws Exception {
//                System.out.println("/parent node data changed：" + new String(CuratorUtil.getData("/parent")));
//            }
//        });


//        CuratorUtil.registerWatcherPathChildrenChanged("/parent", new PathChildrenCacheListener() {
//            @Override
//            public void childEvent(CuratorFramework feign, PathChildrenCacheEvent event) throws Exception {
//                switch (event.getType()) {
//                    case CHILD_ADDED:
//                        System.out.println("CHILD_ADDED: " + event.getData().getPath());
//                        break;
//                    case CHILD_REMOVED:
//                        System.out.println("CHILD_REMOVED: " + event.getData().getPath());
//                        break;
//                    case CHILD_UPDATED:
//                        System.out.println("CHILD_UPDATED: " + event.getData().getPath());
//                        break;
//                    default:
//                        break;
//                }
//            }
//        });


        CuratorUtil.registerWatcherTreeChanged("/parent", new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println("type: " + event.getType());
                switch (event.getType()) {
                    // 添加了一个节点
                    case NODE_ADDED:
                        break;
                    // 节点的数据已更改
                    case NODE_UPDATED:
                        break;
                    // 已从树中删除一个节点
                    case NODE_REMOVED:
                        break;
                    // 当连接更改为暂停时调用
                    case CONNECTION_SUSPENDED:
                        break;
                    // 当连接更改为重新连接时调用
                    case CONNECTION_RECONNECTED:
                        break;
                    // 当连接更改为丢失时调用
                    case CONNECTION_LOST:
                        break;
                    //在初始缓存完全填充后发布  ？？ 不太理解，以后再说
                    case INITIALIZED:
                        break;
                }
            }
        });

        CuratorUtil.close();
    }


}
