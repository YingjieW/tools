package com.tools.ztest.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/7 下午9:15
 */
public class TaxCalculator implements Callable<Integer> {
    private int seedMoney;

    public TaxCalculator(int seedMoney) {
        this.seedMoney = seedMoney;
    }

    @Override
    public Integer call() throws Exception {
        Thread.sleep(10*1000);
        return seedMoney / 10;
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> future = executorService.submit(new TaxCalculator(1100));
        while (!future.isDone()) {
            Thread.sleep(200);
            System.out.print("~ ");
        }
        System.out.println("\nCalculate completely, tax is $[" + future.get() + "].");
        executorService.shutdown();
    }
}
