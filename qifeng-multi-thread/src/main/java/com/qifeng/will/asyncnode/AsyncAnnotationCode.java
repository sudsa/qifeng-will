package com.qifeng.will.asyncnode;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * 〈一句话功能简述〉<br>
 * 〈@Async的使用〉
 *
 *  异步的方法有3种
 * 1. 最简单的无参数无返回值异步调用
 * 2. 带参数无返回值异步调用 
 * 3. 返回Future异步调用
 *
 * @Async 必须与@EnableAsync 一起使用才有效果
 *
 *
 * @author howill.zou
 * @create 2020/2/21
 * @since 1.0.0
 */
@Slf4j
@Component
@EnableAsync
public class AsyncAnnotationCode {

    // 一、使用方法介绍：
    // **************************************************************

    /**
     * 1.最简单的无参数无返回值异步调用
     */
    @Async
    public  void asyncSimple() {
        log.info("asyncSimple");
    }

    /**
     * 2.带参数无返回值异步调用 
     *
     * @param s
     */
    @Async
    public void asyncParameter(String s) {
        log.info("asyncParameter, parameter={}", s);
    }

    /**
     * 3.返回Future异步调用
     *
     * @param s
     * @return
     */
    @Async
    public Future<String> asyncReturnFuture(String s) {
        log.info("asyncReturnFuture, parameter={}", s);
        Future<String> future=new AsyncResult<String>("futureString: " + s);
        return future;
    }


    // 二、模拟异步场景，验证@Async必须与@EnableAsync一起使用才有效果：
    // **************************************************************

    /**
     * 模拟步骤1,执行100ms
     *
     * @throws InterruptedException
     */
    public void step1() throws InterruptedException {
        System.out.println("step1 start");
        System.out.println("step1 当前线程名称:"+Thread.currentThread().getName());
        Thread.sleep(100);
        System.out.println("step1 end");
    }

    /**
     * 模拟步骤2,执行300ms
     *
     * @throws InterruptedException
     */
    @Async
    public void step2() throws InterruptedException {
        System.out.println("step2 start");
        System.out.println("step2 当前线程名称:"+Thread.currentThread().getName());
        Thread.sleep(300);
        System.out.println("step2 end");
    }

    /**
     * 模拟步骤3,执行200ms
     *
     * @throws InterruptedException
     */
    public void step3() throws InterruptedException {
        System.out.println("step3 start");
        System.out.println("step3 当前线程名称:"+Thread.currentThread().getName());
        Thread.sleep(200);
        System.out.println("step3 end");
    }


}
