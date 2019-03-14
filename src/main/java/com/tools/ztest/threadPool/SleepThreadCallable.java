package com.tools.ztest.threadPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class SleepThreadCallable implements Callable {

    @Override
    public Object call() throws Exception {
        try {
            System.out.println("=====  " + Thread.currentThread().getName() + " start to sleep, sleepTime: 10s");
            System.out.println(Thread.currentThread().getContextClassLoader().getClass().getName());
            TimeUnit.SECONDS.sleep(10);
            System.out.println("=====  " + Thread.currentThread().getName() + " awake.");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return Thread.currentThread().getName();
    }
}
