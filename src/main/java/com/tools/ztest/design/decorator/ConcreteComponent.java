package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/30 下午9:50
 */
public class ConcreteComponent implements Component {
    @Override
    public void operation() {
        System.out.println("---> ConcreteComponent.operation.");
    }
}
