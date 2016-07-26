package com.tools.ztest.classloader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/25 下午6:59
 */
public class StaticClass {
    static {
        System.out.println("StaticClass.static");
    }

    @Override
    public String toString() {
        return "com.tools.ztest.classloader.StaticClass";
    }
}
