package com.hanxiaozhang.importexcel;

import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.Executors.*;

/**
 * 〈一句话功能简述〉<br>
 * 〈导入Excel执行器〉
 *
 *  2020-04-14 问题：使用固定线程池，线程数大于7之后，第8个线程就启动不了，也没有找了原因
 *
 *
 * @author hanxinghua
 * @create 2020/2/23
 * @since 1.0.0
 */
@Slf4j
public class ImportExcelExecutor {


    private final static int MAX_THREAD=7;


    /**
     * 执行方法(分批创建子线程)
     * @param saveService 保存的服务
     * @param lists 数据List
     * @param groupLen 分组的长度
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static <T>  List<T>  execute(SaveExcelService<T> saveService, List<T> lists, int groupLen) throws ExecutionException, InterruptedException {

        if(lists==null || lists.size()==0){
            return null;
        }

        List<T> errorList=new ArrayList<>();

        //创建一个固定线程池
        ExecutorService executorService =  new ThreadPoolExecutor(MAX_THREAD, MAX_THREAD, 0, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>(), defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        //创建一个Future集合
        List<Future<ErrorInfoEntity>> futures = new ArrayList<>();
        //集合的元素个数
        int size = lists.size();

        //适配线程池数与分组长度
        //Math.ceil()对小数向下“”取整”
        int batches = (int) Math.ceil(size * 1.0 /groupLen);
        //分组超长最大线程限制，则设置分组数为最大线程限制，计算分组集合尺寸
        if(batches>MAX_THREAD){
            batches = MAX_THREAD;
            groupLen = (int) Math.ceil(size * 1.0 /batches);
        }
        log.info("总条数：[{}],批次数量：[{}],每批数据量：[{}]",size,batches,groupLen);


        MultiThreadEndFlag flag = new MultiThreadEndFlag(batches);

        int startIndex, toIndex, maxIndex = lists.size();

        for(int i=0;i<batches;i++){
            //开始索引位置
            startIndex = i * groupLen;
            //截止索引位置
            toIndex = startIndex + groupLen;
            //如果截止索引大于最大索引，截止索引等于最大索引
            if(toIndex> maxIndex) {
                toIndex = maxIndex;
            }
            //截取数组
            List<T> temp = lists.subList(startIndex,toIndex);
            if(temp == null || temp.size()==0){
                continue;
            }
            futures.add(executorService.submit(new ImportExcelTask(saveService,temp,flag)));
        }
        flag.end();

        //子线程全部等待返回(存在异常，则直接抛向主线程)
        for(Future<ErrorInfoEntity> future:futures){
            errorList.addAll(future.get().getErrorList());
        }

        //所有线程返回后，关闭线程池
        executorService.shutdown();

        return errorList;
    }

    private static int getPoolInfo(ThreadPoolExecutor tpe) {

        int queueSize = tpe.getQueue().size();
        System.out.println("当前排队线程数：" + queueSize);

        int activeCount = tpe.getActiveCount();
        System.out.println("当前活动线程数：" + activeCount);

        long completedTaskCount = tpe.getCompletedTaskCount();
        System.out.println("执行完成线程数：" + completedTaskCount);

        long taskCount = tpe.getTaskCount();
        System.out.println("总线程数：" + taskCount);

        //线程池中当前线程的数量，为0时意味着没有任何线程，线程池会终止，此值不会超过MaximumPoolSize
        System.out.println("当前线程的数量:" + tpe.getPoolSize());

        //线程池的初始线程数量(当没有任务提交，或提交任务数小于此值值，实际并不会产生那么多线程数)
        System.out.println("线程池的初始线程数量:" + tpe.getCorePoolSize());

        //线程池可允许最大的线程数
        System.out.println("线程池可允许最大的线程数" + tpe.getMaximumPoolSize());
        tpe.getLargestPoolSize();

        return queueSize;
    }

}
