package com.tools.ztest.design.visitor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 上午11:15
 */
public interface Visitor {
    void visitConcreteElementA(ConcreteElementA concreteElementA);
    void visitConcreteElementB(ConcreteElementB concreteElementB);
}
