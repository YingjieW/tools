package com.tools.ztest.test;

import java.util.concurrent.CountDownLatch;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 下午5:34
 */
public class TestSynchronized {
    // 锁
    public static Object lock = new Object();

    public static int counter = 0;

    public static void add() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {}
        synchronized (lock) {
            counter++;
        }
    }

    public static void main(String[] args) throws Exception {
        // 创建总数为1000的计数器
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for(int i = 0; i < 1000; i++) {
            Thread thread = new TestCountDownLatchThread(countDownLatch);
            thread.start();
        }
        // 等待子线程结束
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("counter: " + TestSynchronized.counter);
    }
}
