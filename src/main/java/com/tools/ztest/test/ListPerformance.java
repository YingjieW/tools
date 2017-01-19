package com.tools.ztest.test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Descripe: http://blog.csdn.net/wind5shy/article/details/5396887
 *
 * @author yingjie.wang
 * @since 17/1/18 下午11:28
 */
public class ListPerformance {

    private final int threadNumber;

    public ListPerformance(int n) {
        threadNumber = n;
    }

    private static class TestThread implements Runnable {
        private static long totalTime;
        private final int No;
        private final int loop = 100000;
        private final Thread t;
        private final List<Integer> list;

        TestThread(int No, List<Integer> list) {
            this.No = No;
            this.list = list;
            t = new Thread(this);
        }

        public void start() {
            t.start();
        }

        public synchronized void addTime(long time) {
            totalTime += time;
        }

        @Override
        public void run() {
            long time = randomAccess();
            addTime(time);
        }

        @Override
        public String toString() {
            return "Thread " + No + ":";
        }

        public long randomAccess() {
            Date date1 = new Date();
            Random random = new Random();
            for (int i = 0; i < loop; i++) {
                int n = random.nextInt(list.size());
                list.get(n);
            }
            Date date2 = new Date();
            long time = date2.getTime() - date1.getTime();
            // System.out.println(this + list.getClass().getSimpleName()
            // + " time:" + time);
            return time;
        }

    }

    public void initList(List<Integer> list, int size) {
        for (int i = 0; i < size; i++) {
            list.add(new Integer(i));
        }
    }

    public void test(List<Integer> list) {
        System.out.println("Test List Performance");
        TestThread[] ts = new TestThread[threadNumber];
        for (int i = 0; i < ts.length; i++) {
            ts[i] = new TestThread(i, list);
        }
        for (int i = 0; i < ts.length; i++) {
            ts[i].start();
        }
    }

    public static void main(String[] args) {
        ListPerformance lp = new ListPerformance(64);
        List<Integer> al = Collections.synchronizedList(new ArrayList<Integer>());
        lp.initList(al, 100000);
        lp.test(al);
        System.out.println(al.size());
        System.out.println(TestThread.totalTime);

        TestThread.totalTime = 0;
        CopyOnWriteArrayList<Integer> cl = new CopyOnWriteArrayList<Integer>(al);
        lp.test(cl);
        System.out.println(cl.size());
        System.out.println(TestThread.totalTime);
    }
}
