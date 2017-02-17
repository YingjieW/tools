package com.tools.ztest.solid_code;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/14 下午10:29
 */
public class Suggestion_6 {

    private static class Base {
        void fun (int price, int... discounts) {
            System.out.println("Base......fun");
        }
    }

    private static class Sub extends Base {
        @Override
        void fun(int price, int[] discounts) {
            System.out.println("Sub......fun");
        }
    }

    public static void main(String[] args) {
        Base base = new Sub();
        base.fun(100, 50);
        Sub sub = new Sub();
        // 下面这句会报编译错误
//        sub.fun(100, 50);
    }
}
