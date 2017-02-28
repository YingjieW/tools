package com.tools.ztest.design.mediator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午5:57
 */
public interface Mediator {
    void send(String message, Colleague colleague);
}
