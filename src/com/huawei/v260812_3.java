package com.huawei;

public class v260812_3 {
    /*
    24.【26.8.12-3】末世分配资源包
    题目描述
    末世时代，政府为各地分配资源，现有资源分配表 nums[n]，要求按如下规则分配给 k 个营地：

    每个营地只分配一段连续的分配表
    每个营地至少分到一份资源
    所有的资源必须全部分出
    分配方式：尽量平均分配（即：得利最大的营地获得的资源值尽量小）
    
    输入描述
    资源存储数组 nums[n]（资源数 n: 0≤n≤1000，每份资源数：1≤nums[i]≤100000）
    营地数 k（1≤k≤min(50,n)）
    
    输出描述
    在最优平均分配情况下，得利最大团队所获得的资源数
    */
    static int solve(int[] nums, int k) {
        
        int left = 0;
        int right = 0;
        
        for (int i = 0; i < nums.length; i++) {
            // 下界的情况：是每个营地一个资源包 那么结果就是最大值
            left = Math.max(left, nums[i]);
            // 上界的情况：所有的资源包给一个营地 结果就是总和
            right += nums[i];
        }
        
        // 二分+贪心
        while (left < right) {
            int mid = (right - left) / 2 + left;
            int seg = 1; // 把资源分为seg段 至少为1段
            int curSum = 0;  // 当前段总和
            
            // 把mid当做是分段的依据 进行分段处理
            for (int i = 0; i < nums.length; i++) {
                if (curSum + nums[i] > mid) {
                    // 1.如果当前和加上当前值 超过了mid 则需要新开一个段 新开营地 curSum当前总和置为当前值
                    seg++;
                    curSum = nums[i];
                } else {
                    // 2.否则就一直累加到当前和
                    curSum += nums[i];
                }
            }
            
            // 对段进行检查
            if (seg <= k) {
                // 符合题目要求 说明mid找大了 更新right值
                right = mid;
            } else {
                // mid值找小了 导致分的段太多 超过了营地数量 更新left值
                left = mid + 1;
            }
        }
        return left;
    }
    
    static void main() {
        int[] nums = {3, 4, 2, 1};
        int k = 4;
        int ans = solve(nums, k);
        System.out.println(ans);
    }
}
