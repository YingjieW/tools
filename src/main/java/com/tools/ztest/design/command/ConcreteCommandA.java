package com.tools.ztest.design.command;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午10:04
 */
public class ConcreteCommandA extends Command {
    public ConcreteCommandA(Receiver receiver) {
        super(receiver);
    }

    @Override
    public void execute() {
        receiver.actionA();
    }
}
