package com.tools.ztest.design.visitor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 上午11:59
 */
public class ConcreteVisitor1 implements Visitor {
    @Override
    public void visitConcreteElementA(ConcreteElementA concreteElementA) {
        System.out.println("[" + concreteElementA.getClass().getSimpleName() + "] is visited by [" +
            this.getClass().getSimpleName() + "].");
    }

    @Override
    public void visitConcreteElementB(ConcreteElementB concreteElementB) {
        System.out.println("[" + concreteElementB.getClass().getSimpleName() + "] is visited by [" +
                this.getClass().getSimpleName() + "].");
    }
}
