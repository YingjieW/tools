package com.tools.ztest.thread;

/**
 * ThreadLocalTest
 *
 * @author yingjie.wang
 * @date 16/4/12 下午5:07
 */
public class ThreadLocalTest {

    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        for(int i = 0; i < 5; i++ ) {
            new Thread(new MyThread(i)).start();
        }
    }

    private static class MyThread implements Runnable {
        private int index;

        public MyThread(int index) {
            this.index = index;
        }

        public void run() {
            System.out.println("Thead - " + index + "  start, value is " + threadLocal.get() + ".");
            for(int i = 0; i < 10; i++) {
                threadLocal.set(threadLocal.get() + i);
            }
            System.out.println("Thread - " + index + " end, value is " + threadLocal.get() + ".");
        }
    }
}
