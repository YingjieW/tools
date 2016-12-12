package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/10 下午10:52
 */
public class FailFast {

    private static List<String> list = new ArrayList<String>();

    private static void printList() {
        System.out.println("===>");
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println("---> " + iterator.next());
        }
    }

    private static void printAll() {
        System.out.println("===>");
        for (String element : list) {
            System.out.println("---> " + element);
        }
    }

    private static class ThreadOne extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 20; i++ ) {
                list.add(String.valueOf(i));
                for (String element : list) {
                    System.out.println("---> " + element);
                }
            }
        }
    }

    private static class ThreadTwo extends Thread {
        @Override
        public void run() {
            for (int i = 100; i < 120; i++ ) {
                list.add(String.valueOf(i));
                for (String element : list) {
                    System.out.println("***> " + element);
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable {
        new ThreadOne().start();
        new ThreadTwo().start();
    }
}
