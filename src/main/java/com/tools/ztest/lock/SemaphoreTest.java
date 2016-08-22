package com.tools.ztest.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Descripe: http://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/
 *
 * @author yingjie.wang
 * @since 16/8/22 下午11:57
 */
public class SemaphoreTest implements Runnable {

    private static Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        boolean permit = false;
        try {
            permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
            if(permit) {
                System.out.println("Semaphore acquired, avaliablePermis:[" + semaphore.availablePermits() + "].");
                Thread.sleep(5*1000);
            } else {
                System.out.println("Could not acquire semaphore");
            }
        } catch (InterruptedException e) {
            System.out.println("InterruptedException........");
        } finally {
            if (permit) {
                semaphore.release();
            }
        }
    }

    public static void main(String[] args) {
        for(int i = 0; i < 9; i++) {
            new Thread(new SemaphoreTest()).start();
        }
    }
}
