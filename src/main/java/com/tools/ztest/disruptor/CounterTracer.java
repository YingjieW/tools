package com.tools.ztest.disruptor;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/4/6 下午11:11
 */
public interface CounterTracer {

    void start();

    long getMilliTimeSpan();

    boolean count();

    void waitForReached() throws InterruptedException;
}
