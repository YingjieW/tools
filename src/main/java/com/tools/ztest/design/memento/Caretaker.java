package com.tools.ztest.design.memento;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/16 下午10:44
 */
public class Caretaker {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
}
