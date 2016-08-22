package com.tools.ztest.lock;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/8/22 下午4:55
 */
public class WriterInterThread implements Runnable {

    private BufferInterruptibly bufferInterruptibly;

    WriterInterThread(BufferInterruptibly bufferInterruptibly) {
        this.bufferInterruptibly = bufferInterruptibly;
    }

    @Override
    public void run() {
        bufferInterruptibly.write();
    }


    public static void main(String[] args) throws Exception {
        final BufferInterruptibly bufferInterruptibly = new BufferInterruptibly();

        final Thread writerThread = new Thread(new WriterInterThread(bufferInterruptibly));
        final Thread readerThread = new Thread(new ReaderInterThread(bufferInterruptibly));

        writerThread.start();
        readerThread.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                long startTime = System.currentTimeMillis();
                while (true) {
                    if(System.currentTimeMillis() - startTime > 5*1000) {
                        System.out.println("Stop waiting, try to interrupt ReaderThread...");
                        readerThread.interrupt();
                        System.out.println("Interrupted successfully..");
                        break;
                    }
                }
            }
        }).start();
    }
}
