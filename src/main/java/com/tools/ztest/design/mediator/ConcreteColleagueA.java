package com.tools.ztest.design.mediator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午6:02
 */
public class ConcreteColleagueA extends Colleague {
    public ConcreteColleagueA(Mediator mediator) {
        super(mediator);
    }

    public void send(String message) {
        mediator.send(message, this);
    }

    public void notify(String message) {
        System.out.println("ConcreteColleagueA get message: [" + message + "].");
    }
}
