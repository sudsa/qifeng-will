package com.qifeng.will.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.utils.EnsurePath;

/**
 * 〈一句话功能简述〉<br>
 * 〈Leader选举〉
 *
 * @author hanxinghua
 * @create 2021/8/5
 * @since 1.0.0
 */
public class CuratorLeaderTest {

    private static final String ZK_PATH = "/curatorLeaderTest";

    public static void main(String[] args) throws InterruptedException {

        LeaderSelectorListener listener = new LeaderSelectorListener() {

            /**
             * 当您的实例被授予领导权时调用。
             * 此方法在您希望释放领导力之前不应返回
             *
             * @param client
             * @throws Exception
             */
            @Override
            public void takeLeadership(CuratorFramework client) throws Exception {
                System.out.println(Thread.currentThread().getName() + " 现在是领导!");
                Thread.sleep(5000L);
                System.out.println(Thread.currentThread().getName() + " 现在完成工作释放权利!");
            }

            /**
             * 当连接中，存在状态更改时调用
             *
             * @param client
             * @param state
             */
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState state) {
            }
        };

        new Thread(() -> {
            registerListener(listener);
        }).start();

        new Thread(() -> {
            registerListener(listener);
        }).start();

        new Thread(() -> {
            registerListener(listener);
        }).start();

        Thread.sleep(Integer.MAX_VALUE);
    }

    private static void registerListener(LeaderSelectorListener listener) {
        // 1.连接zk
        CuratorUtil.startClient_1();

        // 2.确保路径被创建
        try {
            new EnsurePath(ZK_PATH).ensure(CuratorUtil.getClient().getZookeeperClient());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3.注册监听器
        LeaderSelector selector = new LeaderSelector(CuratorUtil.getClient(), ZK_PATH, listener);
        selector.autoRequeue();
        selector.start();
    }
}
