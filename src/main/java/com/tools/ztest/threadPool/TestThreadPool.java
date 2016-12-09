package com.tools.ztest.threadPool;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/29 下午12:17
 */
public class TestThreadPool {

    public static void main(String[] args) {
        ThreadPoolUtil.getThreadPool().execute(new SleepThread(50000));
//        for(int i = 0; i < 9; i++ ) {
//            ThreadPoolUtil.getThreadPool().execute(new SleepThread(0));
//        }
    }
}
