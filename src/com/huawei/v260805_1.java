package com.huawei;

public class v260805_1 {
    /*
    28.【26.8.5-1】最长不连续子串
    题目描述
    给定一个整数数组，请找出最长的子串，使得该子串中任意两个相邻元素的绝对差都严格大于 1。输出这个最长子串的长度。
    例如，数组 [1, 3, 4, 5, 6, 5, 4] 中，子串 [1, 3] 满足条件 (|1-3|=2>1)，且是最长的，因此答案为 2。
    数组长度为 n，0≤n≤1000
    数组范围 nums[]，0≤nums[i]≤10000
    
    输入描述
    输入为一个整数数组，数组元素在一行中给出，使用英文逗号分隔。数组中可以包含空格，例如：1, 3, 4, 5
    当输入为空行时，表示空数组。
    
    输出描述
    输出满足条件的最长子串的长度。
    */
    
    static int solve(int[] nums) {
        int n = nums.length;
        if (n == 0 || n == 1) {
            return n;
        }
        int left = 0;
        int right = 1;
        int answer = 1;
        int curLen;
        while (right < n) {
            if (Math.abs(nums[right] - nums[right - 1]) > 1) {
                curLen = right - left + 1;
                answer = Math.max(curLen, answer);
                right++;
            } else {
                left = right;
                right += 1;
            }
        }
        return answer;
    }
    
    
    static void main() {
        int[] nums = {1, 3, 4, 5, 7, 9, 11};
        System.out.print(solve(nums));
    }
}
