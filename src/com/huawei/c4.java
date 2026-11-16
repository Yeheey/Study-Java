package com.huawei;

import java.util.Arrays;

public class c4 {
    /*
    【查找接口成功率最优时间段】--【前缀和】--38min
    题目描述
    服务之间交换的接口成功率作为服务调用关键质量特性，某个时间段内的接口失败率使用一个数组表示，
    数组中每个元素都是单位时间内失败率数值，数组中的数值为0~100的整数，
    给定一个数值(minAverageLost)表示某个时间段内平均失败率容忍值，即平均失败率小于等于minAverageLost，
    找出数组中最长时间段，如果未找到则直接返回NULL。
    
    输入描述
    输入有两行内容，第一行为{minAverageLost}，第二行为{数组}，数组元素通过空格(” “)分隔，
    minAverageLost及数组中元素取值范围为0~100的整数，数组元素的个数不会超过100个。
    
    输出描述
    找出平均值小于等于minAverageLost的最长时间段，输出数组下标对，格式{beginIndex}-{endIndx}(下标从0开始)，
    如果同时存在多个最长时间段，则输出多个下标对且下标对之间使用空格(” “)拼接，多个下标对按下标从小到大排序。
    */
    static String solve(int minAverageLost, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return "";
        }
        if (n == 1) {
            return nums[0] <= minAverageLost ? "0" : "";
        }
        // 前缀和 0-idx的和
        int[] preSum = new int[n];
        int temp = 0;
        for (int i = 0; i < nums.length; i++) {
            temp += nums[i];
            preSum[i] = temp;
        }
        
        int maxLen = 0;
        StringBuilder result = new StringBuilder();
        
        // 双重for 列出所有的情况
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                int curLen = j - i + 1;
                int curSum;
                
                if (i == 0) {
                    curSum = preSum[j];
                } else {
                    curSum = preSum[j] - preSum[i - 1];
                }
                
                double curAvg = (double) curSum / curLen;
                if (curAvg <= minAverageLost) {
                    if (curLen >= maxLen) {
                        if (curLen > maxLen) {
                            result = new StringBuilder();
                        }
                        maxLen = curLen;
                        result.append(i).append("-").append(j).append(" ");
                    }
                }
            }
        }
        return result.toString();
    }
    
    static void main() {
        int minAverageLost = 1;
        int[] nums = {0, 1, 2, 3, 4};
        // "0-2"
        System.out.println(solve(minAverageLost, nums));
        
        int minAverageLost1 = 2;
        int[] nums1 = {0, 0, 100, 2, 2, 99, 0, 2};
        // 0-1 3-4 6-7
        System.out.println(solve(minAverageLost1, nums1));
    }
}
