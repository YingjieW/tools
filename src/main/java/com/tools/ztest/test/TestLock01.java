package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/9 上午10:15
 */
public class TestLock01 {
    public int counter = 0;
    private Lock lock = new ReentrantLock();

    public void increase() {
        lock.lock();
        try {
            this.counter++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new ArrayList<Thread>(10000);
        final TestLock01 testLock01 = new TestLock01();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        testLock01.increase();
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }
        for(Thread thread : threadList) {
            thread.join();
        }
        System.out.println("===> counter: " + testLock01.counter);
    }
}
