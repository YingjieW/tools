package com.tools.ztest.design.singleton;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/10 下午11:15
 */
public class DoubleCheckedLockingSingleton {

    private static volatile DoubleCheckedLockingSingleton uniqueInstance;

    private DoubleCheckedLockingSingleton() {}

    public static DoubleCheckedLockingSingleton getInstance() {
        if (uniqueInstance == null) {
            synchronized (DoubleCheckedLockingSingleton.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new DoubleCheckedLockingSingleton();
                }
            }
        }
        return uniqueInstance;
    }
}
