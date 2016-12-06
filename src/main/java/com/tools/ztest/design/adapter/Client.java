package com.tools.ztest.design.adapter;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/6 下午10:09
 */
public class Client {
    public static void main(String[] args) {
        Adaptee adaptee = new Adaptee();
        Target target = new Adapter(adaptee);
        target.request();
    }
}
