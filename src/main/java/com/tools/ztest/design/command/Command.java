package com.tools.ztest.design.command;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午10:01
 */
abstract class Command {
    protected Receiver receiver;

    public Command(Receiver receiver) {
        this.receiver = receiver;
    }

    abstract public void execute();
}
