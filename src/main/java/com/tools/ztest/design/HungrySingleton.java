package com.tools.ztest.design;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/10 下午11:09
 */
public class HungrySingleton {

    private static HungrySingleton uniqueInstance = new HungrySingleton();

    private HungrySingleton() {}

    public static HungrySingleton getInstance() {
        return uniqueInstance;
    }
}
