package com.tools.ztest.test;

import java.util.concurrent.CountDownLatch;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 下午6:52
 */
public class TestCountDownLatchThread extends Thread {

    private CountDownLatch countDownLatch;

    public TestCountDownLatchThread(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        try {
            TestSynchronized.add();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 为保证countDown()方法的执行,将其放在finally中
            countDownLatch.countDown();
        }
    }
}
