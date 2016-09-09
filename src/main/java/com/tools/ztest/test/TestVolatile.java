package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @date 16/4/19 下午2:06
 */
public class TestVolatile {

    public volatile static int counter = 0;

    public static void add() {
        counter++;
    }

    public static void main(String[] args) throws Exception {
        List<Thread> list = new ArrayList<Thread>();
        for(int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        TestVolatile.add();
                    }
                }
            });
            list.add(thread);
            thread.start();
        }

        for(Thread thread : list) {
            thread.join();
        }

        System.out.println("===> counter: " + TestVolatile.counter);
    }
}
