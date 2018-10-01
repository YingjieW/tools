package com.tools.ztest.design.Observer;

import open.framework.bill.utils.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午11:15
 */
public class NotifyCenter {

    private static List<Object> observerList = new LinkedList<Object>();

    private static Object notifyData = "DEFAULT";

    public static void setNotifyData(Object notifyData) {
        NotifyCenter.notifyData = notifyData;
    }

    public static void attach(Object object) {
        if (null == object) {
            return;
        }
        if (!observerList.contains(object)) {
            observerList.add(object);
        }
    }

    public static void detach(Object object) {
        if (null == object) {
            return;
        }
        if (observerList.contains(object)) {
            observerList.remove(object);
        }
    }

    public static void noitfyObserver() throws IllegalAccessException,InvocationTargetException {
        if (observerList == null || observerList.size() == 0) {
            return;
        }
        for (Object observer : observerList) {
            LinkedHashSet<Method> observerMethodSet = RegisterCenter.getObserverMap().get(observer.getClass());
            if (CollectionUtils.isEmpty(observerMethodSet)) {
                System.out.println("[" + observer.getClass() + "] must be registered in RegisterCenter, and register as least one method in RegisterCenter");
                continue;
            }
            for (Method observerMethod : observerMethodSet) {
                observerMethod.invoke(observer, notifyData);
            }
        }
    }
}
