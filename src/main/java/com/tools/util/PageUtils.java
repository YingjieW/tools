package com.tools.util;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/9/5 上午10:27
 */
public class PageUtils {

    /**
     * 得到总分页数
     *
     * @return
     */
    public static int getTotalPageNum(long count, int pageSize) {
        if (count < 1) {
            return 1;
        }
        int totalPageNum = (int) ((count - 1) / pageSize + 1);
        return totalPageNum;
    }

    /**
     * 得到当前页数数据码
     *
     * @param size
     * @param pageNum
     * @return
     */
    public static Pair<Integer, Integer> getNumForPage(int size, int pageNum) {
        int left = (pageNum - 1) * size + 1;
        int right = (pageNum - 1) * size + size;
        return Pair.of(left, right);
    }
}

