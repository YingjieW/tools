package com.tools.ztest.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/22 下午6:27
 */
public class WriterSyncThread implements Runnable {

    private BufferSynchronized bufferSynchronized;

    WriterSyncThread(BufferSynchronized bufferSynchronized) {
        this.bufferSynchronized = bufferSynchronized;
    }

    @Override
    public void run() {
        bufferSynchronized.write();
    }

    public static void main(String[] args) throws Exception {
        BufferSynchronized bufferSynchronized = new BufferSynchronized();

        final Thread writerThread = new Thread(new WriterSyncThread(bufferSynchronized));
        final Thread readerThread = new Thread(new ReaderSyncThread(bufferSynchronized));

        writerThread.start();
        readerThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while (true) {
                    if(System.currentTimeMillis() - startTime > 5*1000) {
                        System.out.println("Stop waiting, try to interrupt readerThread.....");
                        readerThread.interrupt();
                        System.out.println("Interrupted successfully..");
                        break;
                    }
                }
            }
        }).start();
    }
}
