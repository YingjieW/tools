package com.tools.ztest.design.mediator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午5:59
 */
public class ConcreteMediator implements Mediator {
    private ConcreteColleagueA concreteColleagueA;
    private ConcreteColleagueB concreteColleagueB;

    public void setConcreteColleagueA(ConcreteColleagueA concreteColleagueA) {
        this.concreteColleagueA = concreteColleagueA;
    }

    public void setConcreteColleagueB(ConcreteColleagueB concreteColleagueB) {
        this.concreteColleagueB = concreteColleagueB;
    }

    @Override
    public void send(String message, Colleague colleague) {
        if (colleague == concreteColleagueA) {
            concreteColleagueB.notify(message);
        } else {
            concreteColleagueA.notify(message);
        }
    }
}
