package com.tools.ztest.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/6 下午10:26
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    @Override
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
