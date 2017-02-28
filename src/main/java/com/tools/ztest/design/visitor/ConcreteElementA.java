package com.tools.ztest.design.visitor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 上午11:18
 */
public class ConcreteElementA implements Element {
    @Override
    /** 利用双分派技术,实现处理与数据结构的分离 */
    public void accept(Visitor visitor) {
        visitor.visitConcreteElementA(this);
    }

    public void otherOperationA() {
        System.out.println("A - otherOperationA");
    }
}
