package com.tools.ztest.oom;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/8 下午5:03
 */
public class StackOverflowTest {

    public long stackDepth = 0;

    public void stackOverflow() {
        this.stackDepth++;
        this.stackOverflow();
    }

    public static void main(String[] args) throws Throwable {
        StackOverflowTest stackOverflowTest = new StackOverflowTest();
        try {
            stackOverflowTest.stackOverflow();
        } catch (Throwable t) {
            System.out.println("=== stackDepth: " + stackOverflowTest.stackDepth);
            throw t;
        }
    }
}
