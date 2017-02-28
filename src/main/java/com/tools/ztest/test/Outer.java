package com.tools.ztest.test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/27 下午5:21
 */
public class Outer {
    class Inner {
        private int a = 10;
    }
    private Inner inner = new Inner();
    private int b = inner.a;
    private void print() {
        System.out.println("b: " + b);
    }
    public static void main(String[] args) {
        Outer outer = new Outer();
        outer.print();
    }
}
