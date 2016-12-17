package com.tools.ztest.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/24 下午11:51
 */
public class BoundedBuffer {
    final static Lock lock = new ReentrantLock();
    final static Condition notFull  = lock.newCondition();
    final static Condition notEmpty = lock.newCondition();

    final static Object[] items = new Object[10];
    static int putptr, takeptr, count;

    public static void put(Object x) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) {
                notFull.await();
            }
            items[putptr] = x;
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public static Object take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            Object x = items[takeptr];
            items[takeptr] = -999;
            System.out.println("===> x: " + x);
            System.out.println("===> takeptr: " + takeptr);
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        // 线程1: take()
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("take: " + BoundedBuffer.take());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        // 线程2: take()
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("=========> take: " + BoundedBuffer.take());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        // 线程3: put
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("--------------start to put...");
//                while (true) {
//                    try {
//                        for (int i = 3; i < 10; i++) {
//                            BoundedBuffer.put(i);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }
}
