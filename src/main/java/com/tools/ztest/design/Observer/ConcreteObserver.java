package com.tools.ztest.design.Observer;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午10:55
 */
public class ConcreteObserver implements Observer {
    @Override
    public void update(Object object) {
        System.out.println("Receive notify from Subject: " + JSON.toJSONString(object));
    }
}
