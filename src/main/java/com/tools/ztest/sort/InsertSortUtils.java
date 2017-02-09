package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/2/3 下午5:37
 */
public class InsertSortUtils {

    public static <E extends Comparable> E[] sort(E[] arr) {
        if (arr == null || arr.length == 1) {
            return arr;
        }
        E sentry = null;
        int j = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i-1].compareTo(arr[i]) > 0) {
                sentry = arr[i];
                for (j = i-1; j >=0 && arr[j].compareTo(sentry) > 0; j--) {
                    arr[j+1] = arr[j];
                }
                arr[j+1] = sentry;
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        Integer[] datas = {0, 2, 7, 3, 4, 9, 8, 1, 5, 6};
        System.out.println("---> " + JSON.toJSONString(datas));
        Integer[] result = InsertSortUtils.sort(datas);
        System.out.println("---> " + JSON.toJSONString(result));
    }
}
