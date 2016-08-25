package com.tools.ztest.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/25 上午11:17
 */
public class PrinterThread implements Runnable {

    private String threadName;

    private String nextThreadName;

    private ReentrantLock lock;

    private Condition condition;

    private ConditionExample conditionExample;

    public PrinterThread(String threadName, String nextThreadName,
                         ReentrantLock lock, Condition condition, ConditionExample conditionExample) {
        this.threadName = threadName;
        this.nextThreadName = nextThreadName;
        this.lock = lock;
        this.condition = condition;
        this.conditionExample = conditionExample;
    }

    @Override
    public void run() {
        for (int i = 0; i < conditionExample.getCounter(); i++) {
            lock.lock();
            try {
                while (!conditionExample.getCurrentThreadName().equalsIgnoreCase(threadName)) {
                    try {
                        System.out.println(Thread.currentThread().getName() + "[" + i + "]....." + threadName + " is waiting.");
                        condition.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + "[" + i + "]..." + threadName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {}
                conditionExample.setCurrentThreadName(nextThreadName);
                condition.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}
