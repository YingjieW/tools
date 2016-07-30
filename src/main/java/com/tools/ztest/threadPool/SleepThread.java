package com.tools.ztest.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/29 下午12:02
 */
public class SleepThread implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SleepThread.class);

    private long sleepTime;

    SleepThread() {}

    SleepThread(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        if(this.sleepTime > 0) {
            try {
                logger.info("=====  " + Thread.currentThread().getName() + " start to sleep, sleepTime:" + this.sleepTime + "ms");
                Thread.sleep(this.sleepTime);
                logger.info("=====  " + Thread.currentThread().getName() + " awake.");
            } catch (Throwable t) {
                logger.error("SleepThread unknown exception.", t);
            }
        } else {
            logger.info("*****  " + Thread.currentThread().getName() + " no sleep");
        }
    }
}
