package com.tools.ztest.test;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/19 上午10:23
 */
public class ListPerformanceImproved {

    private final static Object lock = new Object();

    private final int defaultLoop = 100000;

    public static long totalTime = 0;

    private int threadCount;

    private List<Integer> list;

    private int listSize;

    public ListPerformanceImproved(int threadCount, List list, int listSize) {
        this.threadCount = threadCount;
        this.list = list;
        this.listSize = listSize;
        init();
    }

    private void init() {
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
        totalTime = 0;
    }

    public void test() throws Exception {
        ListTestThread[] listTestThreads = new ListTestThread[threadCount];
        for (int i = 0; i < listTestThreads.length; i++) {
            listTestThreads[i] = new ListTestThread();
            listTestThreads[i].start();
        }
        for (ListTestThread listTestThread : listTestThreads) {
            listTestThread.join();
        }
    }

    private class ListTestThread extends Thread{

        @Override
        public void run() {
            long time = randomAccessTime();
            synchronized (lock) {
                totalTime += time;
            }
        }

        private long randomAccessTime() {
            Date startTime = new Date();
            Random random = new Random();
            for (int i = 0; i < defaultLoop; i++) {
                int index = random.nextInt(listSize);
                list.get(index);
            }
            Date endTime = new Date();
            return (endTime.getTime() - startTime.getTime());
        }
    }

    public static void main(String[] args) throws Exception {
        int threadCount = 128;
        int listSize = 200000;
        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<Integer>());
        List<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<Integer>();

        ListPerformanceImproved lp1 = new ListPerformanceImproved(threadCount, synchronizedList, listSize);
        System.out.println("---> start to test synchronizedList.....");
        lp1.test();
        System.out.println("---> threadCount: " + threadCount);
        System.out.println("---> totalTime: " + lp1.totalTime);
        System.out.println();

//        ListPerformanceImproved lp2 = new ListPerformanceImproved(threadCount, copyOnWriteArrayList, listSize);
//        System.out.println("---> start to test copyOnWriteArrayList.....");
//        lp2.test();
//        System.out.println("---> threadCount: " + threadCount);
//        System.out.println("---> totalTime: " + lp2.totalTime);
//        System.out.println();
    }
}
