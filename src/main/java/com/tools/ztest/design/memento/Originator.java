package com.tools.ztest.design.memento;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/16 下午10:43
 */
public class Originator {
    private String state;

    public Memento createMemento() {
        return new Memento(state);
    }

    public void setMemento(Memento memento) {
        state = memento.getState();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return state;
    }
}
