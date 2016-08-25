package com.tools.ztest.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Descripe: http://mouselearnjava.iteye.com/blog/1948437
 *
 * @author yingjie.wang
 * @since 16/8/25 上午10:11
 */
public class ConditionExample {

    private int counter;

    private String currentThreadName;

    public ConditionExample(){}

    public ConditionExample(int counter, String currentThreadName) {
        this.counter = counter;
        this.currentThreadName = currentThreadName;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getCurrentThreadName() {
        return currentThreadName;
    }

    public void setCurrentThreadName(String currentThreadName) {
        this.currentThreadName = currentThreadName;
    }

    public static void main(String[] args) throws Exception {
        ConditionExample conditionExample = new ConditionExample(3, "A");
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Condition condition1 = lock.newCondition();
        System.out.println("===> " + (condition == condition1));

        Thread threadA = new Thread(new PrinterThread("A", "B", lock, condition, conditionExample));
        Thread threadB = new Thread(new PrinterThread("B", "C", lock, condition, conditionExample));
        Thread threadC = new Thread(new PrinterThread("C", "A", lock, condition, conditionExample));

        threadB.start();
        threadC.start();
        threadA.start();
    }
}
