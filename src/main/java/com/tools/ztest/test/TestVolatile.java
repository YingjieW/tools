package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @date 16/4/19 下午2:06
 */
public class TestVolatile {

    // 用volatile修饰的变量，线程在每次使用变量的时候，都会读取变量修改后的最的值。
    public volatile static int counter = 0;

    public static void add() {
        try {
            Thread.sleep(1);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        counter++;
    }

    public static void main(String[] args) throws Exception {
        for(int i = 1; i <= 1000; i++) {
            new Thread(new Runnable() {
                public void run() {
                    TestVolatile.add();
                }
            }).start();
        }
        // 睡眠10ms, 是为了等待上面这1000个线程全部执行完毕
        Thread.sleep(10);
        System.out.println("###  counter = " + TestVolatile.counter);
    }
}