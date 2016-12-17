package com.tools.ztest.redis.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/16 下午2:51
 */
public class IDGeneratorTest {
    public static void main(String[] args) throws Exception {
        DistriputedLockBasedOnRedis lock = new DistriputedLockBasedOnRedis("idGenerator", 1);

        int capacity = 20;
        Thread[] threads = new Thread[capacity];
        for (int i = 0; i < capacity; i++) {
            IDGeneratorThread idGeneratorThread = new IDGeneratorThread(new IDGenerator(lock), ("Thread" + i));
            threads[i] = idGeneratorThread;
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            System.out.println("-----------> " + thread.getName() + " : " + thread.getState());
        }

        Thread.sleep(2*1000);

        for (Thread thread : threads) {
            System.out.println("=============>" + thread.getName() + " : " + thread.getState()
            + " \t-  " + thread.getPriority());
        }

        System.out.println("=============> END");

        System.out.println("===> " + Thread.currentThread().getName() + " : " + Thread.currentThread().getState()
                + " \t-  " + Thread.currentThread().getPriority());
    }
}
