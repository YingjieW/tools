package com.tools.ztest.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/8 上午8:43
 */
public class CountDownLatchTest implements Callable<Integer> {
    /** 模拟百米赛跑,多个参加赛跑的运动员在听到发令枪响后,
     * 开始跑步,到达终点后结束计时,然后计算成绩。 */

    // 开始信号
    private CountDownLatch begin;
    // 结束信号
    private CountDownLatch end;

    public CountDownLatchTest(CountDownLatch begin, CountDownLatch end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Integer call() throws Exception {
        /** Causes the current thread to wait until the latch has counted down to
          * zero, unless the thread is interrupted.
          */
         // 等待指令枪响起
        begin.await();
        // 跑步成绩
        int score = new Random().nextInt(10);
        // 跑步中
        Thread.sleep(score * 1000);
        // 运动员结束赛跑
        end.countDown();
        return score;
    }

    public static void main(String[] args) throws Exception {
        long beginTime = System.currentTimeMillis();
        // 运动员人数
        int num = 10;
        // 发令枪只响一次
        CountDownLatch begin = new CountDownLatch(1);
        // 每个运动员都有一个结束标志
        CountDownLatch end = new CountDownLatch(num);
        // 每个运动员一个跑道
        ExecutorService executorService = Executors.newFixedThreadPool(num);
        // 记录比赛成绩
        List<Future<Integer>> futureList = new ArrayList<Future<Integer>>();
        // 运动员就位,所有线程处于等待状态
        for (int i = 0; i < num; i++) {
            futureList.add(executorService.submit(new CountDownLatchTest(begin, end)));
        }
        // 发令枪响,运动员开始跑步
        begin.countDown();
        // 扥带所有运动员跑完全程
        end.await();
        for (Future<Integer> future : futureList) {
            System.out.print(future.get() + ", ");
        }
        System.out.println("\nMain Thread time cost: " + (System.currentTimeMillis() - beginTime));
        System.exit(0);
    }

}
