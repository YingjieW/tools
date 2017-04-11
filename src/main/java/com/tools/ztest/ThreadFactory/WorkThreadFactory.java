package com.tools.ztest.ThreadFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/11 上午8:43
 */
public class WorkThreadFactory implements ThreadFactory {
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Thread newThread(Runnable r)
    {
        int c = atomicInteger.incrementAndGet();
        System.out.println("create no " + c + " Threads");
        return new WorkThread(r, atomicInteger);//通过计数器，可以更好的管理线程
    }
}
