package com.tools.ztest.design.Observer;

import java.util.LinkedList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/11/29 下午10:42
 */
public class Subject {

    private List<Observer> observerList = new LinkedList<Observer>();

    private Object notifyData;

    Subject(Object notifyData) {
        this.notifyData = notifyData;
    }

    public void attach(Observer observer) {
        observerList.add(observer);
    }

    public void detach(Observer observer) {
        observerList.remove(observer);
    }

    public void notifyObserver() {
        if (observerList == null) {
            return;
        }
        for (Observer observer : observerList) {
            observer.update(notifyData);
        }
    }

    public Object getNotifyData() {
        return notifyData;
    }

    public void setNotifyData(Object notifyData) {
        this.notifyData = notifyData;
    }
}
