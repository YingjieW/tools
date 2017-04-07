package com.tools.ztest.disruptor;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/7 上午8:36
 */
public class DisruptorPublisher {

    private class TestEventFactory implements EventFactory<TestEvent> {
        @Override
        public TestEvent newInstance() {
            return new TestEvent();
        }
    }

    private class TestEventHandler implements EventHandler<TestEvent> {
        private TestHandler handler;

        public TestEventHandler(TestHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onEvent(TestEvent event, long sequence, boolean endOfBatch)
                throws Exception {
            handler.process(event);
        }
    }

    private static final WaitStrategy YIELDING_WAIT = new YieldingWaitStrategy();

    private Disruptor<TestEvent> disruptor;
    private TestEventHandler handler;
    private RingBuffer<TestEvent> ringBuffer;
    private ExecutorService executor;
    private TestEventFactory testEventFactory;

    public DisruptorPublisher(int bufferSize, TestHandler handler) {
        this.handler = new TestEventHandler(handler);
        executor = Executors.newSingleThreadExecutor();
        testEventFactory = new TestEventFactory();
        disruptor = new Disruptor<TestEvent>(testEventFactory, bufferSize,
                executor, ProducerType.SINGLE, YIELDING_WAIT);
    }

    @SuppressWarnings("unchecked")
    public void start() {
        disruptor.handleEventsWith(handler);
        disruptor.start();
        ringBuffer = disruptor.getRingBuffer();
    }

    public void publish(int data) throws Exception {
        long seq = ringBuffer.next();
        try {
            TestEvent evt = ringBuffer.get(seq);
            evt.setValue(data);
        } finally {
            ringBuffer.publish(seq);
        }
    }

    public static void main(String[] args) throws Exception {
        for (int j = 0; j < 5; j++) {
            CounterTracer counterTracer = new SimpleTracer(5048576);
            TestHandler testHandler = new TestHandler(counterTracer);
            DisruptorPublisher disruptorPublisher = new DisruptorPublisher(1024, testHandler);

            counterTracer.start();
            disruptorPublisher.start();

            for (int i = 0; i < 5048576; i++) {
                disruptorPublisher.publish(i);
                counterTracer.count();
            }

            counterTracer.waitForReached();

            System.out.println("["+j+"] : " + counterTracer.getMilliTimeSpan());
        }
    }
}
