package com.tools.ztest.design.State;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/11 下午10:11
 */
public class Context {

    private State state;

    // 定义Context的初始状态
    public Context(State state) {
        this.state = state;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    // 对请求做处理,并设置下一个状态
    public void request() {
        state.handle(this);
    }
}
