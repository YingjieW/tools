package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 下午5:24
 */
public class TestAtomicInteger {

    // AtomicInteger: 线程安全
    public static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void add() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {

        }
        atomicInteger.getAndIncrement();
    }

    public static void main(String[] args) throws Exception {
        List<Thread> threadList = new ArrayList<Thread>();
        // 同时启动1000个线程
        for(int i = 0; i < 1000; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    TestAtomicInteger.add();
               }
            });
            thread.start();
            threadList.add(thread);
        }
        // 等待所有子线程执行结束
        try {
            for(Thread thread : threadList) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 输出结果
        System.out.println("atomicInteger: " + atomicInteger.toString());
    }
}
