package com.tools.ztest.design.template;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午10:17
 */
abstract class TestPaper {

    protected abstract Object answer01();

    abstract Object answer02();

    public void question01() {
        System.out.println("---> Question1: ...........");
        System.out.println("===> Answer01 : " + answer01());
    }

    public void question02() {
        System.out.println("---> Question2: ...........");
        System.out.println("===> Answer02 : " + answer02());
    }
}
