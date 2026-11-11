package com.huawei;

public class v260916_1_lei {
    
    static long total;
    
    static long solve(long n, int m, int[] nums) {
        total = 1; // 1 无质因子，计一次
        dfs(0, 1L, nums, n);
        return total;
    }
    
    static void dfs(int start, long curVal, int[] nums, long limit) {
        for (int i = start; i < nums.length; i++) {
            // 卫语句：再乘会超过 n，后面质数更大，直接停
            // 卫语句 = 提前 return /continue/break，把异常、非法条件先拦截，减少嵌套 if-else**
            // 核心思想：失败优先，尽早返回，不要把主逻辑包在大 `if` 里面。
            if (curVal * nums[i] > limit) break;
            
            // long next = curVal * nums[i];
            total++;
            dfs(i, curVal * nums[i], nums, limit); // i 不是 i+1：允许 2*2*2
        }
    }
    
    public static void main(String[] args) {
        long n = 15;
        int m = 3;
        int[] nums = {2, 3, 5};
        System.out.println(solve(n, m, nums)); // 输出 11
    }
}