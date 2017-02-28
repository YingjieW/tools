package com.tools.ztest.design.chain;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午3:09
 */
public class ConcreteHandlerA extends Handler {
    @Override
    void handlerRequest(int request) {
        if (request >= 0 && request < 10) {
            System.out.println(this.getClass().getSimpleName() + " handle request[" + request + "].");
        } else if (successor != null) {
            successor.handlerRequest(request);
        } else {
            throw new RuntimeException("Successor not exists, request is [" + request + "].");
        }
    }
}
