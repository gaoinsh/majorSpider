package com.demo.leetCode.pratice;

import java.util.HashMap;

/**
 * Created by xiang.gao on 2018/4/11.
 * project majorSpider
 * 给定一个整数数列，找出其中和为特定值的那两个数。
 * 你可以假设每个输入都只会有一种答案，同样的元素不能被重用。
 * https://leetcode-cn.com/problems/two-sum/description/
 */
public class TwoNumberArrayCnt {

    public static void main(String[] args) {
        int target = 5;
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] rs = new TwoNumberArrayCnt().twoSum(nums, target);
        System.out.println(rs[0] + " , " + rs[1]);
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> numIndexMap = new HashMap<>();
        int i = 0;
        int len = nums.length;
        int[] result = new int[2];
        for (; i < len; i++) {
            int offset = target - nums[i];
            if (!numIndexMap.containsKey(offset)) {
                numIndexMap.put(nums[i], i);
            } else {
                result[0] = numIndexMap.get(offset);
                result[1] = i;
                break;
            }
        }
        return result;
    }

    public int[] twoSumOwn(int[] nums, int target) {

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return null;
    }
}
