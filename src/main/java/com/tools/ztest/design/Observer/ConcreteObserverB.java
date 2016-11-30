package com.tools.ztest.design.Observer;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午11:17
 */
public class ConcreteObserverB {
    public void functionTest(Object object) {
        System.out.println("ConcreteObserverB.functionTest receive notify:[" + JSON.toJSONString(object) + "].");
    }
}
