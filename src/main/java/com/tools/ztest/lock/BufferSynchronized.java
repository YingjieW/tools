package com.tools.ztest.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/22 下午6:14
 */
public class BufferSynchronized {

    private Object lock;

    public BufferSynchronized() {
        lock = this;
    }

    public void write() {
        synchronized (lock) {
            long startTime = System.currentTimeMillis();
            System.out.println("Start to write...");
            while (true) {
                if(System.currentTimeMillis() - startTime > 10*1000) {
                    System.out.println("Break write method to end infinite loop...");
                    break;
                }
            }
            System.out.println("Writing is over...");
        }
    }

    public void read() {
        synchronized (lock) {
            System.out.println("Reading...");
        }
    }
}
