package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/3 下午6:45
 */
public class QuickSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        quickSort(arr, 0, arr.length-1);
        return arr;
    }

    private static <E extends Comparable> void quickSort(E[] arr, int head, int tail) {
        int i = head;
        int j = tail;
        E pivot = arr[head];
        while (i < j) {
            while (j > i && arr[j].compareTo(pivot) >= 0) {
                j--;
            }
            if (j > i) {
                arr[i++] = arr[j];
            }
            while (i < j && arr[i].compareTo(pivot) < 0) {
                i++;
            }
            if (i < j) {
                arr[j--] = arr[i];
            }
        }
        arr[i] = pivot;
        if ((i-head) > 1) {
            quickSort(arr, head, i-1);
        }
        if ((tail-j) > 1) {
            quickSort(arr, j+1, tail);
        }
    }

    public static void main(String[] args) {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Integer[] result = QuickSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(result));
    }
}
