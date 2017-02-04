package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Array;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/4 上午10:39
 */
public class MergeSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        return mergeSort(arr);
    }

    private static <E extends Comparable> E[] mergeSort(E[] arr) {
        if (arr.length == 1) {
            return arr;
        }
        int half = arr.length / 2;
        E[] arr1 = (E[]) Array.newInstance(Comparable.class, half);
        E[] arr2 = (E[]) Array.newInstance(Comparable.class, arr.length - half);
        System.arraycopy(arr, 0, arr1, 0, arr1.length);
        System.arraycopy(arr, half, arr2, 0, arr2.length);
        arr1 = mergeSort(arr1);
        arr2 = mergeSort(arr2);
        return mergeArr(arr1, arr2);
    }

    private static <E extends Comparable> E[] mergeArr(E[] arr1, E[] arr2) {
        E[] result = (E[]) Array.newInstance(Comparable.class, arr1.length + arr2.length);
        int i=0, j=0, k=0;
        while (i < arr1.length && j < arr2.length) {
            if (arr1[i].compareTo(arr2[j]) < 0) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }
        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }
        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }
        return result;
    }

    public static void main(String[] args) {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Comparable[] result = MergeSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(result));
    }
}
