package com.tools.ztest.thread;

import java.util.Random;
import java.util.concurrent.CyclicBarrier;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/8 上午9:18
 */
public class CyclicBarrierTest implements Runnable {
    // 关卡
    private CyclicBarrier cyclicBarrier;

    public CyclicBarrierTest(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(1000));
            System.out.println(Thread.currentThread().getName() + " - 到达汇合点.");
            /** Waits until all parties have invoked await on this barrier. */
             cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("隧道已打通!");
            }
        });
        // 工人1挖隧道
        new Thread(new CyclicBarrierTest(cyclicBarrier), "工人1").start();
        // 工人2挖隧道
        new Thread(new CyclicBarrierTest(cyclicBarrier), "工人2").start();
    }
}
