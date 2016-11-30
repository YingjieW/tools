package com.tools.ztest.design.Observer;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午11:15
 */
public class ConcreteObserverA {
    public void printing(Object object) {
        System.out.println("ConcreteObserverA.printing receive notify:[" + JSON.toJSONString(object) + "].");
    }
}
