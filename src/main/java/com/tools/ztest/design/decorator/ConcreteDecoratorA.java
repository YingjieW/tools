package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/30 下午10:00
 */
public class ConcreteDecoratorA extends AbstractDecorator {

    @Override
    public void operation() {
        super.operation();
        addBehavior();
    }

    private void addBehavior() {
        System.out.println("===> addBehavior of ConcreteDecoratorA.");
    }
}
