package com.tools.ztest.design.Observer;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午10:56
 */
public class Client {
    public static void main(String[] args) throws Exception {
        ConcreteObserverA concreteObserverA = new ConcreteObserverA();
        ConcreteObserverB concreteObserverB = new ConcreteObserverB();
        RegisterCenter.register(concreteObserverA.getClass(), concreteObserverA.getClass().getMethod("printing", Object.class));
        NotifyCenter.attach(concreteObserverA);
        NotifyCenter.attach(concreteObserverB);
        NotifyCenter.setNotifyData("Test_Notify");
        NotifyCenter.noitfyObserver();
    }
}
