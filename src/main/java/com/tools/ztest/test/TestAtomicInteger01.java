package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/9 上午10:22
 */
public class TestAtomicInteger01 {
    public AtomicInteger counter = new AtomicInteger(0);

    public void increase() {
        this.counter.getAndIncrement();
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new ArrayList<Thread>(10000);
        final TestAtomicInteger01 testAtomicInteger01 = new TestAtomicInteger01();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        testAtomicInteger01.increase();
                    }
                }
            });
            threadList.add(thread);
            thread.start();
        }
        for(Thread thread : threadList) {
            thread.join();
        }
        System.out.println("===> counter: " + testAtomicInteger01.counter);
    }
}
