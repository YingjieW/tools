package com.tools.ztest.design.State;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/11 下午10:16
 */
public class ConcreteStateB implements State{
    @Override
    public void handle(Context context) {
        // 设置ConcreteStateB的下一个状态是ConcreteStateA
        context.setState(new ConcreteStateA());
    }
}
