package com.tools.ztest.design.State;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/11 下午10:15
 */
public class ConcreteStateA implements State {
    @Override
    public void handle(Context context) {
        // 设置ConcreteStateA的下一个状态是ConcreteStateB
        context.setState(new ConcreteStateB());
    }
}
