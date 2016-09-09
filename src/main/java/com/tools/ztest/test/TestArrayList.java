package com.tools.ztest.test;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/9/8 上午10:49
 */
public class TestArrayList {

    public static void main(String[] args) throws Exception {
        ArrayList<Object> arrayList = new ArrayList<Object>(25000000);
        for(int i = 0; i < arrayList.size(); i++) {
            arrayList.add(i);
        }
        useIterator(arrayList);
        useIndex(arrayList);
        useFor(arrayList);
    }

    public static void useIterator(ArrayList<Object> arrayList) {
        long startTime = System.currentTimeMillis();
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        System.out.println("===> useIter  : " + (System.currentTimeMillis() - startTime));
    }

    public static void useIndex(ArrayList<Object> arrayList) {
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i);
        }
        System.out.println("===> useIndex : " + (System.currentTimeMillis() - startTime));
    }

    public static void useFor(ArrayList<Object> arrayList) {
        long startTime = System.currentTimeMillis();
        for(Object o : arrayList) {}
        System.out.println("===> useFor   : " + (System.currentTimeMillis() - startTime));
    }
}
