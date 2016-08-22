package com.tools.ztest.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/22 下午4:52
 */
public class ReaderInterThread implements Runnable {

    private BufferInterruptibly bufferInterruptibly;

    ReaderInterThread(BufferInterruptibly bufferInterruptibly) {
        this.bufferInterruptibly = bufferInterruptibly;
    }

    @Override
    public void run() {
        try {
            bufferInterruptibly.read();
        } catch (InterruptedException e) {
            System.out.println("ReaderThread is interrupted...");
        }
        System.out.println("Reading is over.");
    }
}
