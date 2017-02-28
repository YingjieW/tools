package com.tools.ztest.design.command;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午10:06
 */
public class Client {
    public static void main(String[] args) {
        Receiver receiver = new Receiver();
        Command commandA = new ConcreteCommandA(receiver);
        Command commandB = new ConcreteCommandB(receiver);
        Invoker invoker = new Invoker();

        invoker.setCommand(commandA);
        invoker.executeCommand();

        invoker.setCommand(commandB);
        invoker.executeCommand();

    }
}
