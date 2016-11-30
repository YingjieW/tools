package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/30 下午10:03
 */
public class Client {
    public static void main(String[] args) {
        Component component = new ConcreteComponent();

        AbstractDecorator decoratorA = new ConcreteDecoratorA();
        AbstractDecorator decoratorB = new ConcreteDecoratorB();

        decoratorA.setComponent(component);
        decoratorB.setComponent(decoratorA);
        decoratorB.operation();
    }
}
