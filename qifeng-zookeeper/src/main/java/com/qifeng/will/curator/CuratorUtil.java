package com.qifeng.will.curator;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈〉
 *
 * @author hanxinghua
 * @create 2021/8/2
 * @since 1.0.0
 */
public class CuratorUtil {


    private static String zkPath = "127.0.0.1:2181";


    /**
     * 客户端
     */
    private static CuratorFramework client = null;


    /**
     * 获取规则
     * <p>
     * 规则有很多种，都实现了RetryPolicy接口
     *
     * @return
     */
    private static RetryPolicy getRetryPolicy() {
        // 重试规则，重试1000次，每次睡眠3ms
        RetryPolicy retryPolicy = new RetryNTimes(1000, 3);
        return retryPolicy;
    }


    /**
     * 创建会话
     * 使用静态工程方法创建
     *
     * @return
     */
    public static void startClient_1() {

        client = CuratorFrameworkFactory.newClient(zkPath, 5000, 5000, getRetryPolicy());
        client.start();
    }


    /**
     * 创建会话
     * 使用Fluent风格api创建
     *
     * @return
     */
    public static void startClient_2() {

        client = CuratorFrameworkFactory
                .builder()
                .connectString(zkPath)
                .sessionTimeoutMs(5000)
                .connectionTimeoutMs(5000)
                .retryPolicy(getRetryPolicy())
                .build();
        client.start();
    }


    /**
     * 获取客户端
     *
     * @return
     */
    public static CuratorFramework getClient() {
        return client;
    }


    /**
     * 创建节点
     *
     * @param path
     * @param data
     * @param mode
     * @throws Exception
     */
    public static void create(String path, String data, CreateMode mode) throws Exception {
        client.create()
                // 递归创建所需父节点
                .creatingParentsIfNeeded()
                // 节点类型
                .withMode(mode)
                // 路径 和 数据
                .forPath(path, data.getBytes());
    }

    /**
     * 删除
     *
     * @param path
     * @throws Exception
     */
    public static void delete(String path) throws Exception {
        client.delete()
                // 强制保证删除
                .guaranteed()
                // 递归删除子节点
                .deletingChildrenIfNeeded()
                // 路径
                .forPath(path);
    }

    /**
     * 删除
     *
     * @param path
     * @param version
     * @throws Exception
     */
    public static void delete(String path, int version) throws Exception {
        client.delete()
                // 强制保证删除
                .guaranteed()
                // 递归删除子节点
                .deletingChildrenIfNeeded()
                // 指定删除的版本号
                .withVersion(version)
                // 路径
                .forPath(path);
    }

    /**
     * 修改
     *
     * @param path
     * @param data
     * @throws Exception
     */
    public static void update(String path, String data) throws Exception {
        client.setData()
                .forPath(path, data.getBytes());
    }

    /**
     * 修改
     *
     * @param path
     * @param data
     * @param version
     * @throws Exception
     */
    public static void update(String path, String data, int version) throws Exception {
        client.setData()
                .withVersion(version)
                .forPath(path, data.getBytes());
    }

    /**
     * 查询
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static byte[] getData(String path) throws Exception {
        return client.getData()
                .forPath(path);
    }

    /**
     * 查询状态
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static Stat getStat(String path) throws Exception {
        Stat stat = new Stat();
        client.getData()
                .storingStatIn(stat)
                .forPath(path);
        return stat;
    }

    /**
     * 校验是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static boolean checkExists(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return stat != null;
    }

    /**
     * 获取子路径
     *
     * @param path
     * @return
     * @throws Exception
     */
    public static List<String> getChildren(String path) throws Exception {
        List<String> list = client.getChildren().forPath(path);
        return list;
    }

    /*
     * PathCache：监视一个路径下孩子结点的创建、删除、以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。
     * NodeCache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
     * TreeCache：PathCache和NodeCache的合体，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
     */


    /**
     * 监听
     *
     * @param path
     * @param nodeCacheListener
     * @return
     */
    public static boolean registerWatcherNodeChanged(String path, NodeCacheListener nodeCacheListener) {

        NodeCache nodeCache = new NodeCache(client, path, false);
        try {
            nodeCache.getListenable().addListener(nodeCacheListener);

            nodeCache.start(true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }


    /**
     * 监听
     *
     * @param path
     * @param pathChildrenCacheListener
     * @return
     */
    public static boolean registerWatcherPathChildrenChanged(String path, PathChildrenCacheListener pathChildrenCacheListener) {

        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, path, true);
        try {

            pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
            pathChildrenCache.start();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * 监听
     *
     * @param path
     * @param treeCacheListener
     * @return
     */
    public static boolean registerWatcherTreeChanged(String path, TreeCacheListener treeCacheListener) {

        TreeCache treeCache = new TreeCache(client, path);
        try {

            treeCache.getListenable().addListener(treeCacheListener);

            treeCache.start();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /**
     * 关闭
     */
    public static void close() {
        client.close();
        client = null;
    }


}
