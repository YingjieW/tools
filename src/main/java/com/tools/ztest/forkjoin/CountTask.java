package com.tools.ztest.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * Description: http://www.infoq.com/cn/articles/fork-join-introduction
 *
 * @author yingjie.wang
 * @since 17/11/16 下午2:23
 */
public class CountTask extends RecursiveTask<Integer> {

    private static int THRESHOLD = 2;
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        }
        if (end == 5000) {
            throw new RuntimeException("test...");
        }
        // 拆分为子任务
        int middle = (start + end) / 2;
        CountTask leftTask = new CountTask(start, middle);
        CountTask rightTask = new CountTask(middle + 1, end);
        // 执行子任务
        leftTask.fork();
        rightTask.fork();
        // 等待子任务执行完毕，获取结果
        int leftResult = leftTask.join();
        int rightResult = rightTask.join();
        // 合并子任务结果
        sum = leftResult + rightResult;
        return sum;
    }

    public static void main(String[] args) throws Exception {
        ForkJoinPool pool = new ForkJoinPool();
        int count = 10000;
        CountTask task = new CountTask(1, count);
        long start = System.currentTimeMillis();
        Future<Integer> result = pool.submit(task);
        System.out.println(result.get());
        System.out.println("." + (System.currentTimeMillis() - start));

        long start1 = System.currentTimeMillis();
        int sum = 0;
        for (int i = 1; i <= count; i++) {
            sum += i;
        }
        System.out.println(sum);
        System.out.println(".." + (System.currentTimeMillis() - start1));
    }
}
