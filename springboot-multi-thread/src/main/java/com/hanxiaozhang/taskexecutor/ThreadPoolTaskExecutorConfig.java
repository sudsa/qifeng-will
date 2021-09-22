package com.hanxiaozhang.taskexecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 〈一句话功能简述〉<br>
 * 〈线程池配置〉
 *
 * @author hanxinghua
 * @create 2020/2/22
 * @since 1.0.0
 */
@Configuration
public class ThreadPoolTaskExecutorConfig implements AsyncConfigurer {

    /**
     * 冒号后为默认值
     */
    @Value("${async.executor.core-pool-size:10}")
    private int corePoolSize;

    /**
     * 默认值也可以直接赋值给属性
     */
    @Value("${async.executor.max-pool-size}")
    private int maxPoolSize=10;

    @Value("${async.executor.queue-capacity:10}")
    private int queueCapacity;

    @Value("${async.executor.await-termination-seconds:60}")
    private int awaitTerminationSeconds;

    @Value("${async.executor.wait-for-tasks-to-complete-on-shutdown:true}")
    private boolean waitForTasksToCompleteOnShutdown;

    @Value("${async.executor.thread-name-prefix:AsyncExecutorThread-}")
    private String threadNamePrefix;


    @Bean(name = "asyncExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数
        threadPool.setCorePoolSize(corePoolSize);
        //设置最大线程数
        threadPool.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        threadPool.setQueueCapacity(queueCapacity);
        //等待任务在关机时完成--表明等待所有线程执行完
        threadPool.setWaitForTasksToCompleteOnShutdown(waitForTasksToCompleteOnShutdown);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        threadPool.setAwaitTerminationSeconds(awaitTerminationSeconds);
        // 线程名称前缀
        threadPool.setThreadNamePrefix(threadNamePrefix);
        // 初始化线程
        threadPool.initialize();

        return threadPool;
    }

    /**
     * 多线程异常处理
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return null;
    }
}
