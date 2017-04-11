package com.tools.ztest.ThreadFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/11 上午8:36
 */
public class WorkThread extends Thread {
    /**
     * 构建线程封装类WorkThread,该类的功能主要是为了能够更好的管理线程而创建的
     */
    private Runnable target;   //线程执行目标
    private AtomicInteger counter;

    public WorkThread(Runnable target, AtomicInteger counter) {
        this.target = target;
        this.counter = counter;
    }
    @Override
    public void run() {
        try {
            target.run();
        } finally {
            int c = counter.getAndDecrement();
            System.out.println("terminate no " + c + " Threads");
        }
    }
}
