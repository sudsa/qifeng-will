package com.hanxiaozhang.configcenter;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.concurrent.CountDownLatch;


/**
 * 功能描述: <br>
 * 〈默认的Watch〉
 *
 * @Author:hanxinghua
 * @Date: 2021/6/8
 */
public class DefaultWatch implements Watcher {


    private CountDownLatch countDownLatch;

    public DefaultWatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void process(WatchedEvent event) {

        switch (event.getState()) {
            case Unknown:
                break;
            case Disconnected:
                break;
            case NoSyncConnected:
                break;
            case SyncConnected:
                countDownLatch.countDown();
                System.out.println("Zookeeper  connect  success!");
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


    }
}
