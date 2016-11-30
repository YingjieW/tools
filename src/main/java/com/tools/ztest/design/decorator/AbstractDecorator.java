package com.tools.ztest.design.decorator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/30 下午9:59
 */
public abstract class AbstractDecorator implements Component {
    private Component component;

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public void operation() {
        if (component != null) {
            component.operation();
        }
    }
}
