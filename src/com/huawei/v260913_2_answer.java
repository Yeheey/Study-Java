package com.huawei;

public class v260913_2_answer {
    static long solve(int[] nums, int k) {
        int n = nums.length;
        if (n < 2 || k < 1 || k >= n) {
            return 0;
        }
        
        // 固定值域频次数组比哈希表更直接
        int[] frequency = new int[101];
        for (int value : nums) {
            frequency[value]++;
        }
        
        // 预处理阶乘，并计算多重集合的全部去重排列数
        long[] factorial = new long[n + 1];
        factorial[0] = 1;
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        
        long totalArrangements = factorial[n];
        long equalOrderedPairs = 0;
        for (int count : frequency) {
            if (count > 0) {
                totalArrangements /= factorial[count];
                equalOrderedPairs += (long) count * (count - 1);
            }
        }
        
        // 所有位置具有对称性，按合法有序元素对所占比例得到答案
        long allOrderedPairs = (long) n * (n - 1);
        long differentOrderedPairs = allOrderedPairs - equalOrderedPairs;
        return totalArrangements * differentOrderedPairs / allOrderedPairs;
    }
    
    public static void main(String[] args) {
        int[] nums = {2, 2, 3};
        int k = 1;
        // 2 322 223
        System.out.println("ans====" + solve(nums, k));
        
        int[] nums1 = {1, 1, 2, 2};
        int k1 = 2;
        // 4
        System.out.println("ans====" + solve(nums1, k1));
        
        int[] nums2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int k2 = 14;
        // 4
        System.out.println("ans3====" + solve(nums2, k2));
    }
    
    
}
