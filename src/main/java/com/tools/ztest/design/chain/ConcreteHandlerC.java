package com.tools.ztest.design.chain;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午3:22
 */
public class ConcreteHandlerC extends Handler {
    @Override
    void handlerRequest(int request) {
        if (request >= 20 && request < 30) {
            System.out.println(this.getClass().getSimpleName() + " handle request[" + request + "].");
        } else if (successor != null) {
            successor.handlerRequest(request);
        } else {
            throw new RuntimeException("Successor not exists, request is [" + request + "].");
        }
    }
}
