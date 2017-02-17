package com.tools.ztest.design.memento;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/16 下午10:43
 */
public class Memento {
    private String state;

    public Memento() {}

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
