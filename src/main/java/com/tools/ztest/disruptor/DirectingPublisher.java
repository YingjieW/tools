package com.tools.ztest.disruptor;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/6 下午11:15
 */
public class DirectingPublisher {
    private TestHandler handler;
    private TestEvent event = new TestEvent();

    public DirectingPublisher(TestHandler handler) {
        this.handler = handler;
    }

    public void publish(int data) throws Exception {
        event.setValue(data);
        handler.process(event);
    }

    public static void main(String[] args) throws Exception {
        for (int j = 0; j < 5; j++) {
            CounterTracer counterTracer = new SimpleTracer(1048576);
            TestHandler testHandler = new TestHandler(counterTracer);
            DirectingPublisher directingPublisher = new DirectingPublisher(testHandler);

            counterTracer.start();

            for (int i = 0; i < 1048576; i++) {
                directingPublisher.publish(i);
                counterTracer.count();
            }

            counterTracer.waitForReached();

            System.out.println("["+j+"] : " + counterTracer.getMilliTimeSpan());
        }
    }
}
