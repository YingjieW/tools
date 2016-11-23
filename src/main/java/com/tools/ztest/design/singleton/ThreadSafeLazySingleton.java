package com.tools.ztest.design.singleton;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/10 下午11:04
 */
public class ThreadSafeLazySingleton {

    private static ThreadSafeLazySingleton uniqueInstance;

    private ThreadSafeLazySingleton() {}

    public static synchronized ThreadSafeLazySingleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new ThreadSafeLazySingleton();
        }
        return uniqueInstance;
    }
}
