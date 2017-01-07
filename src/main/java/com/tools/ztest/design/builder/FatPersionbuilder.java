package com.tools.ztest.design.builder;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午11:35
 */
public class FatPersionbuilder implements PersonBuilder {
    @Override
    public void buildHead() {
        System.out.println("===> build fat head...");
    }

    @Override
    public void buildBody() {
        System.out.println("===> build fat body...");
    }

    @Override
    public void buildArmLeft() {
        System.out.println("===> build fat left arm...");
    }

    @Override
    public void buildArmRight() {
        System.out.println("===> build fat right arm...");
    }

    @Override
    public void buildLegleft() {
        System.out.println("===> build fat left leg...");
    }

    @Override
    public void buildLegRight() {
        System.out.println("===> build fat right leg...");
    }
}
