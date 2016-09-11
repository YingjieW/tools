package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/11 下午9:06
 */
public class TestYield extends Thread {

    @Override
    public void run() {
        long beginTime=System.currentTimeMillis();
        int count=0;
        for (int i=0; i< 50000; i++){
            count=count+(i+1);
            Thread.yield();
        }
        long endTime=System.currentTimeMillis();
        System.out.println("===> 用时："+ (endTime-beginTime)+" 毫秒！");
    }

    public static void main(String[] args) {
        TestYield testYield = new TestYield();
        testYield.start();
    }
}
