package com.tools.ztest.design.visitor;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/28 上午11:16
 */
public interface Element {
    void accept(Visitor visitor);
}
