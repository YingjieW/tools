package com.tools.util;

import com.alibaba.fastjson.JSON;
import com.yeepay.g3.utils.common.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/7/25 上午11:47
 */
public class ListUtils {

    /**
     * 按指定大小切割list
     * @param list
     * @param pageSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        if (pageSize <= 0) {
            throw new IllegalArgumentException("pageSize must be greater than 0.");
        }
        List<List<T>> listArray = new ArrayList<List<T>>();
        if (pageSize >= list.size()) {
            listArray.add(list);
            return listArray;
        }
        List<T> subList = null;
        for (int i = 0; i < list.size(); i++) {
            if (i % pageSize == 0) {
                subList = new ArrayList<T>();
                listArray.add(subList);
            }
            subList.add(list.get(i));
        }
        return listArray;
    }

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
        List<List<Integer>> lists = splitList(list, 10);
        for (List<Integer> subList : lists) {
            System.out.println(JSON.toJSONString(subList));
        }
    }
}
