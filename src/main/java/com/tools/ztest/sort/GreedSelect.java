package com.tools.ztest.sort;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/1 下午4:16
 */
public class GreedSelect {

    public static void main(String[] args) {
        greedSelect();
    }

    private static void greedSelect() {
        int[] st = {1,3,0,5,3,5,6,8,8,2,12};
        int[] ft = {4,5,6,7,8,9,10,11,12,13,14};
        int length = st.length;
        int current = 0;
        System.out.println("0 is selected");
        for (int i = 1; i < length; i++) {
            //因为活动结束时间已经按从小到大顺序排列，那么只要活动开始时间大于current结束时间
            //那么此活动一定会是最优的。有可能其它活动开始时间也满足要求，但他们的结束时间一定比i大。
            if (st[i] >= ft[current]) {
                current = i;
                System.out.println("[" + current + "]:" + st[current] + " is selected");
            }
        }
    }
}
