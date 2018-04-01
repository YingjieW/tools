package com.tools.ztest.test;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 18/3/28 下午9:31
 */
public class SemaphoreTest {
    private static final int COUNT = 40;
    private static Executor executor = Executors.newFixedThreadPool(COUNT);
    private static Semaphore semaphore = new Semaphore(10);
    public static void main(String[] args) {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                System.out.println("currentThread.getName: " + Thread.currentThread().getName());
                System.out.println("this.getName: " + this.getName());
            }
        };
        Thread thread = new Thread(t1);
        thread.setName("testing...");
        thread.start();
    }

    static class Task implements Runnable {
        @Override
        public void run() {
            try {
                //读取文件操作
                semaphore.acquire();
                // 存数据过程
                semaphore.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
            }
        }
    }

}
