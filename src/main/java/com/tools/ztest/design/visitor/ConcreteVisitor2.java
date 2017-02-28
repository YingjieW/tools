package com.tools.ztest.design.visitor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午12:01
 */
public class ConcreteVisitor2 implements Visitor {
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
