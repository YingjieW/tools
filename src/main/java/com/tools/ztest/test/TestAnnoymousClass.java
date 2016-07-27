package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/7/26 下午11:20
 */
// 也可是非抽象类
class Animal {
    void eat() {};
}

public class TestAnnoymousClass {
    public static void main(String[] args) {
        new Animal() {
            @Override
            public void eat() {
                System.out.println("Eating.....");
            }
        }.eat();
    }
}
