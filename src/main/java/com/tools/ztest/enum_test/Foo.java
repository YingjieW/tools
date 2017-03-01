package com.tools.ztest.enum_test;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/1 下午10:16
 */
@Access(level = CommonIdentifier.ADMIN)
public class Foo {
    public static void main(String[] args) {
        Foo foo = new Foo();
        Access access = foo.getClass().getAnnotation(Access.class);
        if (access == null || !access.level().identify()) {
            System.out.println(access.level().REFUSE_WORD);
        } else {
            System.out.println("PASS");
        }
    }
}
