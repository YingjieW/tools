package com.tools.ztest.design.builder;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午11:33
 */
public class ThinPersionBuilder implements PersonBuilder {
    @Override
    public void buildHead() {
        System.out.println("---> build thin head...");
    }

    @Override
    public void buildBody() {
        System.out.println("---> build thin body...");
    }

    @Override
    public void buildArmLeft() {
        System.out.println("---> build thin left arm...");
    }

    @Override
    public void buildArmRight() {
        System.out.println("---> build thin right arm...");
    }

    @Override
    public void buildLegleft() {
        System.out.println("---> build thin left leg...");
    }

    @Override
    public void buildLegRight() {
        System.out.println("---> build thin right leg...");
    }
}
