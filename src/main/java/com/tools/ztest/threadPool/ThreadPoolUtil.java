package com.tools.ztest.threadPool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/29 上午11:59
 */
public class ThreadPoolUtil {

    private volatile static ExecutorService threadPool;

    //保持最少corePoolSize个线程
    private static int corePoolSize = 3;
    //如果corePoolSize都在工作，新任务放入到queue中等待
    private static int waitQueueSize = 50;
    //如果queue中等待任务已满，则继续创建新线程处理任务,如果工作线程数达到maxPoolSize依然不够处理，则拒绝最新的请求(本处将抛出异常)
    private static int maxPoolSize = 3;
    //空闲线程存活时间,根据定时分布配置
    private static long threadLiveTime = 5;
    //任务队列
    private static ArrayBlockingQueue waitQueue = new ArrayBlockingQueue(waitQueueSize);


    /* 任务处理线程池 */
    public static ExecutorService getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadPoolUtil.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(
                            corePoolSize, maxPoolSize, threadLiveTime, TimeUnit.MINUTES, waitQueue,
                            new ThreadPoolExecutor.DiscardPolicy());
                }
            }
        }
        return threadPool;
    }
}
