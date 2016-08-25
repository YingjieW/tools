package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/25 上午11:37
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        final Object lock1 = new Object();
        final Object lock2 = new Object();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "...lock1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock2) {
                        System.out.println(Thread.currentThread().getName() + "...lock2");
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "...lock2");
                    synchronized (lock1) {
                        System.out.println(Thread.currentThread().getName() + "...lock1");
                    }
                }
            }
        }).start();
    }
}
