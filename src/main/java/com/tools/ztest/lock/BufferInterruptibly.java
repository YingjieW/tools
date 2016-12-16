package com.tools.ztest.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe: http://blog.csdn.net/natian306/article/details/18504111
 *
 * @author yingjie.wang
 * @since 16/8/22 下午4:45
 */
public class BufferInterruptibly {

    private ReentrantLock reentrantLock = new ReentrantLock();

    public void write() {
        reentrantLock.lock();
        try {
            long startTime = System.currentTimeMillis();
            System.out.println("Start to write.");
            while (true) {
                if(System.currentTimeMillis() - startTime > 10*1000) {
                    System.out.println("Break write() method to end infinite loop.");
                    break;
                }
            }
            System.out.println("Writing is Over.");
        } finally {
            reentrantLock.unlock();
            System.out.println("Unlock reentrantLock of write() method. count: " + reentrantLock.getHoldCount());
        }
    }

    public void read() throws InterruptedException {
//        reentrantLock.lockInterruptibly();
        reentrantLock.lock();
        try {
            System.out.println("Reading....");
        } finally {
            reentrantLock.unlock();
            System.out.println("Unlock reentrantLock of read() method. count: " + reentrantLock.getHoldCount());
        }
    }
}
