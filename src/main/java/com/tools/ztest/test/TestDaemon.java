package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/24 下午5:23
 */
public class TestDaemon {
    private static class DaemonThread implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("Sleeping.....");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Cann't sleep.....");
            } finally {
                System.out.println("Exec finally.....");
            }
        }
    }
    public static void main(String[] args) {
        Thread thread = new Thread(new DaemonThread());
        thread.setDaemon(true);
        thread.start();
    }
}
