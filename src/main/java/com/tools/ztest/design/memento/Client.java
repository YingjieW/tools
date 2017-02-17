package com.tools.ztest.design.memento;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/16 下午10:47
 */
public class Client {
    public static void main(String[] args) {
        Originator originator = new Originator();
        originator.setState("On");
        System.out.println(originator);

        Caretaker caretaker = new Caretaker();
        caretaker.setMemento(originator.createMemento());

        originator.setState("Off");
        System.out.println(originator);

        originator.setMemento(caretaker.getMemento());
        System.out.println(originator);
    }
}
