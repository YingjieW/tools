package com.tools.ztest.classloader;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/25 下午6:56
 */
public class TestLoadClassMethod {
    public static void main(String[] args) throws Exception{
        // loadClass只加载,不初始化
        Class c1 = Thread.currentThread().getContextClassLoader().loadClass("com.tools.ztest.classloader.StaticClass");
        System.out.println("------------------");
        // forName会初始化类
        Class c2 = Class.forName("com.tools.ztest.classloader.StaticClass");
        Thread.currentThread().getContextClassLoader();
    }
}
