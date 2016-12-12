package com.tools.ztest.design.State;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/11 下午10:17
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Context context = new Context(new ConcreteStateA());
        context.request();
        context.request();
        context.request();
        System.out.println(context.getState());
    }
}
