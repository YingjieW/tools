package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/24 下午6:13
 */
public class Reader extends Thread{
    Calculator c;
    public Reader(Calculator calc) {
        c = calc;
    }
    public void run() {
        synchronized(c) {
            try {
                System.out.println("Waiting for calculation...");
                c.wait();
            } catch (InterruptedException e) {}
            System.out.println("Total is: " + c.total);
            try {
                Thread.sleep(100*1000);
            } catch (InterruptedException e) {}
        }
    }

    public static void main(String [] args) {
        Calculator calculator = new Calculator();
        new Reader(calculator).start();
        new Reader(calculator).start();
        new Reader(calculator).start();
        calculator.start();
    }
}
