package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/24 下午6:13
 */
public class Calculator extends Thread {
    int total;
    public void run() {
        synchronized(this) {
            for(int i=0;i<100;i++) {
                total += i;
            }
            notifyAll();
        }
    }
}
