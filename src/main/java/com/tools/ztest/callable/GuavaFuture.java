package com.tools.ztest.callable;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/20 下午6:39
 */
public class GuavaFuture implements Callable<String> {
    @Override
    public String call() throws Exception {
        System.out.println("call start sleeping...");
        TimeUnit.SECONDS.sleep(3);
        System.out.println("call end sleeping...");
        return "Hello World";
    }

    public static void main(String[] args) throws Exception {
        GuavaFuture guavaFuture = new GuavaFuture();
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        ListenableFuture<String> listenableFuture = listeningExecutorService.submit(guavaFuture);
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("listener starts.....");
                long start = System.currentTimeMillis();
                System.out.println("cost : " + (System.currentTimeMillis() - start));
                System.exit(0);
            }
        }, Executors.newSingleThreadExecutor());
    }
}
