package com.tools.ztest.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Descripe: 测试ReentrantReadWriteLock
 *
 * @author yingjie.wang
 * @since 16/8/22 下午11:01
 */
public class ReadWriteLockTest {

    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public static void readLock(String name) {
        lock.readLock().lock();
        System.out.println("[" + name + "]: Implement readLock...");
//        lock.readLock().unlock();
//        System.out.println("[" + name + "]: Release readLock...");
    }

    public static void writeLock(String name) {
        lock.writeLock().lock();
        System.out.println("[" + name + "]: Implement writeLock...");
        lock.writeLock().unlock();
        System.out.println("[" + name + "]: Release writeLock...");
    }

    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                ReadWriteLockTest.readLock("Thread-01");
            }
        });

        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                ReadWriteLockTest.writeLock("Thread-02");
            }
        });

        thread2.start();
        thread2.interrupt();
    }
}
