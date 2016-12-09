package com.tools.ztest.oom;

import java.util.ArrayList;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 16/12/8 下午5:17
 */
public class HeapOOMTest {

    public static void main(String[] args) throws Throwable {
        long objectCounter = 0l;
        try {
            List<HeapOOMTest> heapOOMTestList = new ArrayList<HeapOOMTest>();
            while (true) {
                objectCounter++;
                heapOOMTestList.add(new HeapOOMTest());
            }
        } catch (Throwable t) {
            System.out.println("=== objectCounter: " + objectCounter);
            throw t;
        }
    }
}
