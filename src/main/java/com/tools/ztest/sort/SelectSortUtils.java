package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/3 下午6:08
 */
public class SelectSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        int min = -1;
        E temp = null;
        for (int i = 0; i < arr.length - 1; i++) {
            min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[min]) < 0) {
                    min = j;
                }
            }
            if (min != i) {
                temp = arr[min];
                arr[min] = arr[i];
                arr[i] = temp;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Integer[] result = SelectSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(result));
    }
}
