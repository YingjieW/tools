package com.tools.ztest.interrupt;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/10/11 下午6:34
 */
public class Demo01 {
    public static void main(String[] args) throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("---> Running...");
                    try {
                        System.out.println("~~~> [B]isInterrupted(): " + Thread.currentThread().isInterrupted());
                        Thread.sleep(1000);
                        System.out.println("~~~> [A]isInterrupted(): " + Thread.currentThread().isInterrupted());
                    } catch (InterruptedException e) {
                        System.out.println("---> Interrupted...");
                        System.out.println("~~~> [E]isInterrupted(): " + Thread.currentThread().isInterrupted());
                    }
                }
            }
        });

        System.out.println("=======> Starting...");
        thread.start();

        try {
            Thread.sleep(3*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("=======> Interrupt thread....");
        thread.interrupt();
    }
}
