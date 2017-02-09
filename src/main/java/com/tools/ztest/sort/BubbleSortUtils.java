package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/3 下午5:53
 */
public class BubbleSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        E temp = null;
        for (int i = arr.length -1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j].compareTo(arr[j+1]) > 0) {
                    temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Integer[] result = BubbleSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(result));
    }
}
