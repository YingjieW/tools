package com.tools.ztest.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe: http://netjs.blogspot.sg/2016/02/reentrantlock-in-java-concurrency.html
 *
 * @author yingjie.wang
 * @since 16/8/22 下午3:02
 */
public class ReentrantLockThread implements Runnable{

    private String threadName;

    private ReentrantLock reentrantLock;

    ReentrantLockThread(String threadName, ReentrantLock reentrantLock) {
        this.threadName = threadName;
        this.reentrantLock = reentrantLock;
    }

    @Override
    public void run() {
        System.out.println("In run() method, thread[" + threadName + "] is waiting for getting lock.");
        try {
            reentrantLock.lock();
            System.out.println("Thread[" + threadName + "] has got lock in run() method.");
            lockAgain();
            System.out.println("Count of locks held by thread[" + threadName + "] in run() method is:  " + reentrantLock.getHoldCount() + " .");
        } finally {
            reentrantLock.unlock();
        }
    }

    public void lockAgain() {
        System.out.println("In lockAgain() method, thread[" + threadName + "] is waiting for getting lock.");
        try {
            reentrantLock.lock();
            System.out.println("Thread[" + threadName + "] has got lock in lockAgain() method.");
            System.out.println("Count of locks held by thread[" + threadName + "] in lockAgain() method is:  " + reentrantLock.getHoldCount() + " .");
        } finally {
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        ReentrantLock reentrantLock = new ReentrantLock();
        Thread thread1 = new Thread(new ReentrantLockThread("Thread-1", reentrantLock));
        Thread thread2 = new Thread(new ReentrantLockThread("Thread-2", reentrantLock));
        System.out.println("main() method starting.");
        thread1.start();
        thread2.start();
    }
}
