package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/3 下午11:51
 */
public class Client1 {
    public static void main(String[] args) throws Throwable {
        Animal jerry = new Rat();
        jerry = new DecorateAnimal(jerry, FlyFeature.class);
        jerry = new DecorateAnimal(jerry, DigFeature.class);
        jerry.doStuff();

    }
}
