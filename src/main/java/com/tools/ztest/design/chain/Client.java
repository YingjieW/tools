package com.tools.ztest.design.chain;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午3:24
 */
public class Client {
    public static void main(String[] args) {
        Handler handlerA = new ConcreteHandlerA();
        Handler handlerB = new ConcreteHandlerB();
        Handler handlerC = new ConcreteHandlerC();
        handlerA.setSuccessor(handlerB);
        handlerB.setSuccessor(handlerC);

        int[] requests = {17, 5, 7, 27, 99};
        for (int request : requests) {
            handlerA.handlerRequest(request);
        }
    }
}
