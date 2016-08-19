package com.tools.ztest.init;

/**
 * Descripe: http://www.jianshu.com/p/a9d8c1a37b8c
 *
 * @author yingjie.wang
 * @since 16/8/19 下午2:23
 */
public class SuperClass {
    static {
        System.out.println("SuperClass init!");
    }
    public static int value = 123;
}
