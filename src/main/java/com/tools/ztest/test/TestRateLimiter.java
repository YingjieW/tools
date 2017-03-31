package com.tools.ztest.test;

import com.google.common.util.concurrent.RateLimiter;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/3/31 下午4:38
 */
public class TestRateLimiter {

    public static void main(String[] args) throws Exception {
//        withRateLimiter(10);
        withRateLimiter(20);
        withoutRateLimiter(20);
    }

    private static void withRateLimiter(int total) throws Exception {
        Long start = System.currentTimeMillis();
        RateLimiter limiter = RateLimiter.create(10.0); // 每秒不超过10个任务被提交
        for (int i = 0; i < total; i++) {
            limiter.acquire(); // 请求RateLimiter, 超过permits会被阻塞
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println();
    }

    private static void withoutRateLimiter(int total) throws Exception {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            System.out.println("call execute.." + i);

        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println();
    }
}
