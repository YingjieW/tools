package com.tools.ztest.test;

import java.io.IOException;

/**
 * Descripe: http://www.jianshu.com/p/4e2343cf747b
 *
 * @author yingjie.wang
 * @since 16/9/11 下午8:53
 */
public class TestThread01 {

    private int i = 10;
    private Object object = new Object();

    public static void main(String[] args) throws IOException {
        TestThread01 testThread01 = new TestThread01();
        MyThread thread1 = testThread01.new MyThread();
        MyThread thread2 = testThread01.new MyThread();
        thread1.start();
        thread2.start();
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("===> i:" + (i++));
                try {
                    System.out.println("===> 线程" + Thread.currentThread().getName() + "进入睡眠状态");
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                }
                System.out.println("===> 线程" + Thread.currentThread().getName() + "睡眠结束");
                System.out.println("===> i:" + (i++));
            }
        }
    }
}
