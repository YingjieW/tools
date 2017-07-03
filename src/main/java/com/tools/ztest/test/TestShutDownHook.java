//package com.tools.ztest.test;
//
///**
// * Descripe: http://blog.csdn.net/dd864140130/article/details/49155179
// *
// * @author yingjie.wang
// * @since 16/8/24 下午3:39
// */
//public class TestShutdownHook {
//    public static void main(String[] args) throws Exception {
//        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("Run shut down hook, currentTimeMillis: " + System.currentTimeMillis());
//            }
//        }));
//        System.out.println("Main thread, currentTimeMillis: " + System.currentTimeMillis());
//        Thread.sleep(100);
//        Runtime.getRuntime().exit(10);
////        Runtime.getRuntime().halt(2);
//    }
//}
