package com.tools.ztest.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/6 下午10:28
 */
public class LongEventHandler implements EventHandler<LongEvent> {
    @Override
    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("Event: " + event.getValue());
    }

    public static void main(String[] args) throws Exception {
        EventFactory<LongEvent> eventFactory = new LongEventFactory();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        int ringBufferSize = 1024 * 1024; // RingBuffer 大小，必须是 2 的 N 次方；

        // 该方法已废弃
        Disruptor<LongEvent> disruptor = new Disruptor<LongEvent>(eventFactory,
                ringBufferSize, executor, ProducerType.SINGLE,
                new YieldingWaitStrategy());

        EventHandler<LongEvent> eventHandler = new LongEventHandler();
        disruptor.handleEventsWith(eventHandler);

        disruptor.start();

        // 发布事件；
        RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
        //请求下一个事件序号；
        long sequence = ringBuffer.next();
        try {
            //获取该序号对应的事件对象；
            LongEvent event = ringBuffer.get(sequence);
            //获取要通过事件传递的业务数据；
            long data = System.currentTimeMillis();
            event.setValue(data);
        } finally{
            //发布事件；
            ringBuffer.publish(sequence);
        }

        //关闭 disruptor，方法会堵塞，直至所有的事件都得到处理；
        disruptor.shutdown();
        //关闭 disruptor 使用的线程池；如果需要的话，必须手动关闭， disruptor 在 shutdown 时不会自动关闭；
        executor.shutdown();
    }
}
