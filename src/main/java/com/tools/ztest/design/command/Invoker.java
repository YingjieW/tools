package com.tools.ztest.design.command;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午10:05
 */
public class Invoker {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void executeCommand() {
        command.execute();
    }
}
