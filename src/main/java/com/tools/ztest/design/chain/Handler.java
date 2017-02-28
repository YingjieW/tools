package com.tools.ztest.design.chain;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 下午3:06
 */
abstract class Handler {
    // 继任者
    protected Handler successor;

    public void setSuccessor(Handler successor) {
        this.successor = successor;
    }

    abstract void handlerRequest(int request);
}
