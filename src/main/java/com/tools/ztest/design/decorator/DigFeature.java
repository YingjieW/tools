package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/3 下午11:38
 */
public class DigFeature implements Feature {
    @Override
    public void loadFeature() {
        System.out.println("Add dig ability...");
    }
}
