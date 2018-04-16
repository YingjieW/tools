package com.tools.temp;

import java.util.concurrent.*;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/4/14 下午12:18
 */
public class TestThreadPoolExecutor extends ThreadPoolExecutor {
    public TestThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
                                  TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    @Override
    protected void beforeExecute(Thread thread, Runnable runnable) {
        super.beforeExecute(thread, runnable);
        System.out.println("===> before execute - " + thread.getName());
    }

    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        try {
            System.out.println("===> after execute - " + Thread.currentThread().getName());
        } finally {
            super.afterExecute(runnable, throwable);
        }
    }

    public static void main(String[] args) throws Exception {
        TestThreadPoolExecutor executor = new TestThreadPoolExecutor(3, 5, 10,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.DiscardPolicy());

        executor.execute(() -> {
            System.out.println("-----> execute - " + Thread.currentThread().getName());
        });
    }
}
