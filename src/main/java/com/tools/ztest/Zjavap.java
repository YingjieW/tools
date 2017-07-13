package com.tools.ztest;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/7/10 下午3:20
 */
public class Zjavap {
    public static void main(String[] args) throws Throwable {
        Object lock = new Object();
        synchronized (lock) {
            System.out.println("===> First");
            synchronized (lock) {
                System.out.println("===> Second");
            }
        }
    }

}
