package com.tools.ztest.sort;

import com.alibaba.fastjson.JSON;

/**
 * Description:
 *
 * @author yingjie.wang
 * @since 17/10/1 下午2:54
 */
public class CoinsChange {

    public static void main(String[] args) {
        int[] coins = {25, 21, 10, 5, 1 };
        System.out.println(JSON.toJSONString(change(coins, 61)));
    }

    private static int[] change(int[] coins, int totalMoney) {
        // changeCount[0] = 0; 起点
        int[] changeCount = new int[totalMoney + 1];
        for (int eachMoney = 1; eachMoney <= totalMoney; eachMoney++) {
            int minCount = eachMoney / 1; // 最小的零钱先写死为1
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= eachMoney) {
                    int temp = changeCount[eachMoney - coins[j]] + 1;
                    if (temp < minCount) {
                        minCount = temp;
                    }
                }
            }
            changeCount[eachMoney] = minCount;
        }
        return changeCount;
    }
}
