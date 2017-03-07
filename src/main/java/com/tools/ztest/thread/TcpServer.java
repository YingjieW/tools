package com.tools.ztest.thread;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/7 上午9:04
 */
public class TcpServer implements Runnable {
    public TcpServer() {
        Thread thread = new Thread(this);
        thread.setUncaughtExceptionHandler(new TcpServerExceptionHandler());
        thread.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                System.out.println("系统正常运行: " + i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private static class TcpServerExceptionHandler implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            System.out.println("线程 " + t.getName() + " 出现异常, 将立即重启。");
            e.printStackTrace();
            new TcpServer();
        }
    }

    public static void main(String[] args) {
        new TcpServer();
    }
}
