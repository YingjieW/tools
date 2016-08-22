package com.tools.ztest.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/22 下午6:22
 */
public class ReaderSyncThread implements Runnable {

    private BufferSynchronized bufferSynchronized;

    ReaderSyncThread(BufferSynchronized bufferSynchronized) {
        this.bufferSynchronized = bufferSynchronized;
    }

    @Override
    public void run() {
        bufferSynchronized.read();
        System.out.println("Reading is over....");
    }
}
