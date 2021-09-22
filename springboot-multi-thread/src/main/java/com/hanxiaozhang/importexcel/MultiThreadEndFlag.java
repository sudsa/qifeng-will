package com.hanxiaozhang.importexcel;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;


/**
 * 功能描述: <br>
 * 〈多线程结束标志〉
 *
 * @Author:hanxinghua
 * @Date: 2020/2/23
 */
@Slf4j
public class MultiThreadEndFlag {

    /**
     * 是否解除等待
     */
    private volatile boolean releaseWaitFlag = false;

    /**
     * 是否全部执行成功
     */
    private volatile boolean allSuccessFlag = false;

    /**
     * 线程个数
     */
    private volatile int threadCount = 0;

    /**
     * 失败个数
     */
    private volatile int failCount = 0;

    /**
     * 初始化子线程的总数
     * @param count
     */
    public MultiThreadEndFlag(int count){
        threadCount = count;
    }


    public boolean allSuccessFlag() {
        return allSuccessFlag;
    }

    /**
     * 等待全部结束
     * @param resultFlag
     */
    public synchronized void waitForEnd(int resultFlag){
        //统计失败的线程个数
        if(resultFlag==0){
            failCount++;
        }
        threadCount--;
        log.info("waitForEnd()，等待全部结束：[{}]",threadCount);
        log.info("waitForEnd()，当前线程名称：[{}]",Thread.currentThread().getName());
        while (!releaseWaitFlag){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行结束通知
     */
    public synchronized void go(){
        releaseWaitFlag = true;
        //结果都显示成功
        allSuccessFlag = (failCount == 0);
        notifyAll();
    }
    /**
     * 等待结束
     */
    public void end(){
        while (threadCount > 0){
            log.info("end()：剩余线程个数[{}]",threadCount);
            waitFunc(50);
        }
        log.info("线程全部执行完毕通知");
        go();
    }

    /**
     * 等待
     */
    private void waitFunc(long millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
