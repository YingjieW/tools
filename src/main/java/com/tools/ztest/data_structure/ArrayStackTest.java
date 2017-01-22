package com.tools.ztest.data_structure;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/1/22 下午5:30
 */
public class ArrayStackTest {
    public static void main(String[] args) throws Exception {
        Integer[] integers = {0,1,2,3,4,5,6,7,8};
        ArrayStack<Integer> arrayStack = new ArrayStack<Integer>();
        for (Integer i : integers) {
            arrayStack.push(i);
        }
        System.out.println(arrayStack);

        for (int i = 0; i < 9; i++) {
            System.out.print(arrayStack.pop() + " ");;
        }
        System.out.println("\n" + arrayStack);

        for (int i = 1; i < 22; i++) {
            arrayStack.push((0-i));
        }
        System.out.println(arrayStack);

        Integer[] pushArray = {1,2,3,4,5};
        Integer[] popArray1 = {4,5,3,1,2};
        Integer[] popArray2 = {4,5,3,2,1};
        System.out.println(arrayStack.isPopOrder(pushArray, popArray1));
        System.out.println(arrayStack.isPopOrder(pushArray, popArray2));
    }
}
