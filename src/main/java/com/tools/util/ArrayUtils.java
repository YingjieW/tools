package com.tools.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Descripe:
 *
 * @author yingjie.wang
 * @since 17/3/1 下午10:54
 */
public class ArrayUtils {
    public static <T> List<T> asList(T...t) {
        List<T> list = new ArrayList<T>();
        Collections.addAll(list, t);
        return list;
    }

    public static void main(String[] args) {
        List<Integer> list = ArrayUtils.<Integer>asList(1,2);
    }
}
