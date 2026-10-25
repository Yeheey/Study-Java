package com.huawei;

public class test7 {
/*
7.【26.8.30-1】小花获胜的奶茶
题目描述：
小菊和小花是好朋友，他们经常一起玩游戏。这天他们玩一个数字游戏，获胜可以获得对方1杯奶茶。
游戏规则:
小菊在纸上写了一排数组，小花需要从中选择连续k个数字，使得这k个数字的和最大。
小花正确找到最大的值就是获胜，小菊则提供1杯奶茶;小花想获胜喝奶茶，请你帮助她。

输入描述：
参数1:给出的一排数字
参数2:k
约束:
·-2147483648<=单个数字<=21474836472
·0<一排数字长度<1063
，需小花选择连续的数字个数k，1<=k<=小菊给出的一排数字的长度

输出描述：
返回连续k个数字的最大和
*/
    
    // 方法1：暴力拆解
    static int solve(int[] nums, int k) {
        int res = Integer.MIN_VALUE;
        int i = 0;
        while (i < nums.length - k + 1) {
            int curTotal = 0;
            for (int j = i; j < i + k; j++) {
                curTotal += nums[j];
            }
            res = Math.max(res, curTotal);
            i++;
        }
        
        return res;
    }
    
    // 方法2：滑动窗口
    static int solve1(int[] nums, int k) {
        int curSum = 0;
        for (int i = 0; i < k; i++) {
            // 先默认当前窗口是[0,k-1]
            curSum += nums[i];
            
        }
        // 先默认res也是第一个窗口总和
        int res = curSum;
        for (int j = 1; j + k - 1 < nums.length; j++) {
            curSum = curSum - nums[j - 1] + nums[j + k - 1];
            res = Math.max(res, curSum);
        }
        
        return res;
    }
    
    static void main() {
        int[] nums = {1, 3, 8, 6, 4, 10};
        int k = 3;
        // output 20
        
        int[] nums1 = {-1, -2, -3, -4, -5};
        int k1 = 3;
        // output -6
        
        System.out.print(solve1(nums1, k1));
        
        
    }
}
