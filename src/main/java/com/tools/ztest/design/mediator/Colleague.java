package com.tools.ztest.design.mediator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午5:58
 */
abstract class Colleague {
    protected Mediator mediator;

    public Colleague(Mediator mediator) {
        this.mediator = mediator;
    }

    abstract void send(String message);
    abstract void notify(String message);
}
