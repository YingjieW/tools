package com.tools.ztest.disruptor;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/7 上午8:23
 */
public class BlockingQueuePublisher {

    private ArrayBlockingQueue<TestEvent> queue ;
    private TestHandler handler;
    public BlockingQueuePublisher(int maxEventSize, TestHandler handler) {
        this.queue = new ArrayBlockingQueue<TestEvent>(maxEventSize);
        this.handler = handler;
    }

    public void start(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                handle();
            }
        });
        thread.start();
    }

    private void handle(){
        try {
            TestEvent testEvent ;
            while (true) {
                testEvent = queue.take();
                if (testEvent != null && handler.process(testEvent)) {
                    //完成后自动结束处理线程；
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void publish(int data) throws Exception {
        TestEvent testEvent = new TestEvent();
        testEvent.setValue(data);
        queue.put(testEvent);
    }

    public static void main(String[] args) throws Exception {
        for (int j = 0; j < 5; j++) {
            CounterTracer counterTracer = new SimpleTracer(10048576);
            TestHandler testHandler = new TestHandler(counterTracer);
            BlockingQueuePublisher blockingQueuePublisher = new BlockingQueuePublisher(1024, testHandler);

            counterTracer.start();
            blockingQueuePublisher.start();

            for (int i = 0; i < 10048576; i++) {
                blockingQueuePublisher.publish(i);
                if(counterTracer.count()) {
                    break;
                }
            }

            counterTracer.waitForReached();
            System.out.println("["+j+"] : " + counterTracer.getMilliTimeSpan());
        }
    }
}
