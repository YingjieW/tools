package com.tools.ztest.design.composite;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/21 下午11:13
 */
abstract class Component {
    protected String name;
    public Component(String name) {
        this.name = name;
    }
    public abstract void add(Component component);
    public abstract void remove(Component component);
    public abstract void display(int depth);
}
