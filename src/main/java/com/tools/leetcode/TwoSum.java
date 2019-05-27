package com.tools.leetcode;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 *
 * 示例:
 *
 * 给定 nums = [2, 7, 11, 15], target = 9
 *
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 *
 * 所以返回 [0, 1]
 *
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hash = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer diff = target - nums[i];
            if (hash.containsKey(diff)) {
                int[] result = {hash.get(diff), i};
                return result;
            }
            hash.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 18;
        int[] result = twoSum(nums, target);
        System.out.println("result: " + JSON.toJSONString(result));

        nums = new int[] {3, 3};
        target = 6;
        result = twoSum(nums, target);
        System.out.println("result: " + JSON.toJSONString(result));
    }
}
/* -----------------------------------------------------
result: [1,2]
result: [0,1]
------------------------------------------------------*/
