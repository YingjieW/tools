package com.tools.ztest.interrupt;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/10/11 下午5:20
 */
public class Demo02 extends Thread {

    @Override
    public void run() {
        try {
            int i = 0;
            while (!this.isInterrupted()) {
                Thread.sleep(100);
                System.out.println("====> [" + this.getState() + "]: loop - [" + i++ + "].");
            }
        } catch (InterruptedException e) {
            System.out.println("----> [" + this.getState() + "]: catch InterruptedException.");
        }
    }

    public static void main(String[] args) throws Exception {
        Demo02 demo02 = new Demo02();
        System.out.println("~~~~> [" + demo02.getState() + "]: new Demo02().");
        demo02.start();
        System.out.println("~~~~> [" + demo02.getState() + "]: start demo02.");
        Thread.sleep(300);
        demo02.interrupt();
        System.out.println("~~~~> [" + demo02.getState() + "]: invoke interrupt() method.");
        Thread.sleep(300);
        System.out.println("~~~~> [" + demo02.getState() + "] check state of demo02 thread.");
    }
}
