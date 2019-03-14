package com.tools.ztest.threadPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/29 下午12:17
 */
public class TestThreadPool {

    public static void main(String[] args) throws Exception {
        ArrayList<Callable<Object>> list = new ArrayList<>();
        list.add(new SleepThreadCallable());
        list.add(new SleepThreadCallable());
        list.add(new SleepThreadCallable());

        list.add(new SleepThreadCallable());
        list.add(new SleepThreadCallable());
        list.add(new SleepThreadCallable());

        list.add(new SleepThreadCallable());
        long start = System.currentTimeMillis();
        System.out.println(start);

        // invokeAll是同步的，需要等待执行完毕
        ThreadPoolUtil.getThreadPool().invokeAll(list);

        // 循环submit是ok的，异步的
//        for (Callable callable : list) {
//            ThreadPoolUtil.getThreadPool().submit(callable);
//        }

        // 单独submit是ok的，异步的
//        ThreadPoolUtil.getThreadPool().submit(() -> {
//            System.out.println("1 - start to sleep 10s");
//            try {
//                TimeUnit.SECONDS.sleep(10);
//            } catch (Exception e) {}
//            System.out.println("1 - end sleep 10s");
//        });
//        System.out.println("1.........");
//        ThreadPoolUtil.getThreadPool().submit(new SleepThreadCallable());
//


        long end = System.currentTimeMillis();
        System.out.println(end);
        System.out.println("-------------------> cost: " + (start - end));
    }
}
