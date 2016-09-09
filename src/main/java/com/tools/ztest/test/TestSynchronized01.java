package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/9 上午10:05
 */
public class TestSynchronized01 {

    public int counter = 0;

    public synchronized void add() {
        this.counter++;
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new ArrayList<Thread>(10000);
        final TestSynchronized01 testSynchronized01 = new TestSynchronized01();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        testSynchronized01.add();
                    }
                }
            });
            thread.start();
            threadList.add(thread);
        }
        for(Thread thread : threadList) {
            thread.join();
        }
        System.out.println("===> counter: " + testSynchronized01.counter);
    }
}
