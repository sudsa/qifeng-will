package com.qifeng.will.thred;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync(proxyTargetClass = true)//利用@EnableAsync注解开启异步任务支持
public class ThreadPoolConfig implements AsyncConfigurer {

    /*注：IO密集型（某大厂实践经验）
    核心线程数 = CPU核数 / （1-阻塞系数）
    或着
    CPU密集型：核心线程数 = CPU核数 + 1
    IO密集型：核心线程数 = CPU核数 * 2*/
    //获取当前机器的核数
    public static final int cpuNum = Runtime.getRuntime().availableProcessors();

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(cpuNum);//核心线程大小
        taskExecutor.setMaxPoolSize(cpuNum * 2);//最大线程大小
        taskExecutor.setQueueCapacity(500);//队列最大容量
        //当提交的任务个数大于QueueCapacity，就需要设置该参数，但spring提供的都不太满足业务场景，可以自定义一个，也可以注意不要超过QueueCapacity即可
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.setThreadNamePrefix("BCarLogo-Thread-");
        taskExecutor.setThreadFactory(Executors.defaultThreadFactory());
        taskExecutor.initialize();
        return taskExecutor;
    }

}
