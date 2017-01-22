package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/22 下午6:28
 */
public class ArrayQueueTest {
    public static void main(String[] args) {
        Integer[] integers = {0,1,2,3,4,5,6,7,8,9};
        ArrayQueue<Integer> arrayQueue = new ArrayQueue<Integer>();
        for (Integer i : integers) {
            arrayQueue.add(i);
        }
        System.out.println(arrayQueue);

        for (int i = 0; i < 2; i++) {
            System.out.print(arrayQueue.remove() + " ");
        }
        System.out.println("\n" + arrayQueue);

        arrayQueue.add(77);
        arrayQueue.add(88);
        arrayQueue.add(99);
        System.out.println(arrayQueue);
    }
}
