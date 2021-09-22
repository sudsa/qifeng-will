package com.hanxiaozhang;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class SpringbootZookeeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootZookeeperApplication.class, args);

        /*
         1. Zk有session概念，没有连接池的概念。
         2. watch的注册，只发生在读类型调用上，例如 get类型方法（getData、getChildren）、exists。
         3. watch 分两类：
            第一类：new zk的时候，传入watch，这个watch是session级别的，与path、node没有关系。
            第二类：getData()、getChildren()、exists()。
         */

        CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            // 1.实例化ZK链接
            ZooKeeper zooKeeper = new ZooKeeper("127.0.0.1:2181", 3000, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    Event.KeeperState state = watchedEvent.getState();
                    Event.EventType type = watchedEvent.getType();
                    String path = watchedEvent.getPath();
                    System.out.println("new ZK():" + watchedEvent.toString());
                    switch (state) {
                        case Unknown:
                            break;
                        case Disconnected:
                            break;
                        case NoSyncConnected:
                            break;
                        case SyncConnected:
                            System.out.println("ZK SyncConnected");
                            countDownLatch.countDown();
                            break;
                        case AuthFailed:
                            break;
                        case ConnectedReadOnly:
                            break;
                        case SaslAuthenticated:
                            break;
                        case Expired:
                            break;
                    }

                    switch (type) {
                        case None:
                            break;
                        case NodeCreated:
                            break;
                        case NodeDeleted:
                            break;
                        case NodeDataChanged:
                            break;
                        case NodeChildrenChanged:
                            break;
                    }

                }
            });
            countDownLatch.await();


            // 2.获取Zk的状态
            ZooKeeper.States state = zooKeeper.getState();
            switch (state) {
                case CONNECTING:
                    System.out.println("get now Zk State is CONNECTING!");
                    break;
                case ASSOCIATING:
                    break;
                case CONNECTED:
                    System.out.println("get now Zk State is CONNECTED!");
                    break;
                case CONNECTEDREADONLY:
                    break;
                case CLOSED:
                    break;
                case AUTH_FAILED:
                    break;
                case NOT_CONNECTED:
                    break;
            }


            // 3.创建一个节点（/abc）
            String s = zooKeeper.create("/abc", "hello".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);


            Stat stat = new Stat();
            // 4.1 获取一个节点（/abc），不设置Watcher
//            byte[] node = zooKeeper.getData("/abc" ,false,stat);

            // 4.2 获取一个节点（/abc），设置默认的Watcher（即new Zk的watch）
//            byte[] node = zooKeeper.getData("/abc" ,true,stat);

            // 4.3 获取一个节点（/abc），并设置Watcher
            byte[] node = zooKeeper.getData("/abc", new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("getData:" + watchedEvent.toString());
                    try {
                        // 方式1：true使用默认的Watch， new Zk的watch
                        // zooKeeper.getData("/abc",true,stat);

                        // 方式2：使用当前的 Watcher
                        zooKeeper.getData("/abc", this, stat);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, stat);
            System.out.println(new String(node));


            // 5.更新节点（/abc），测试观察者
            Stat stat1 = zooKeeper.setData("/abc", "hello1".getBytes(), 0);
            Stat stat2 = zooKeeper.setData("/abc", "hello2".getBytes(), stat1.getVersion());


            // 6.测试异步回调结果
            System.out.println("-----Async begin-----");
            zooKeeper.getData("/abc", false, new AsyncCallback.DataCallback() {
                @Override
                public void processResult(int rc, String path, Object ctx, byte data[], Stat stat) {

                    switch (KeeperException.Code.get(rc)) {
                            // 与服务器的连接丢失
                        case CONNECTIONLOSS:

                            // 成功
                        case OK:

                        default:

                    }
                    System.out.println("-----Async call back-----");
                    System.out.println("call back ctx: " + ctx.toString());
                    System.out.println("call back data: " + new String(data));
                }
            }, "abc1");
            System.out.println("-----Async end-----");


        } catch (IOException | InterruptedException | KeeperException e) {
            e.printStackTrace();
        }
    }

}
