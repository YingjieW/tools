package com.tools.ztest.design.singleton;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/10 下午10:53
 */
public class LazySingleton {

    private static LazySingleton uniqueInstance;

    private LazySingleton() {}

    public static LazySingleton getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new LazySingleton();
        }
        return uniqueInstance;
    }
}
