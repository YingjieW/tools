package com.tools.ztest.design.adapter;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/6 下午10:06
 */
public class Adapter implements Target{

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void request() {
        this.adaptee.specificRequest();
    }
}
