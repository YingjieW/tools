package com.tools.ztest.design.template;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/7 下午10:24
 */
public class Client {
    public static void main(String[] args) {
        TestPaper testPaperA = new StudentA();
        TestPaper testPaperB = new StudentB();

        testPaperA.question01();
        testPaperB.question01();
    }
}
