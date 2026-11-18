package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class v260913_2_ysy {
    /*
    【26.9.13-2】受限序列重排--【DFS】
    题目描述
    给定一个包含 n 个整数的数组 nums 和一个整数 k，你需要将数组 nums 中的所有元素重新排列，
    生成一个新的序列数组，其中下标第 k−1 个元素和下标第 k 个元素不能数值相同。
    请统计重新排列后符合条件的数组个数（相同的数组排列需去重统计）；如果无法构造出满足上述所有条件的数组，结果为 0。
    
    输入描述
    本题为核心代码模式，你需要实现函数 countArrangements(nums, k)。
    参数 nums 为一个整数数组，长度 n（1≤n≤15），元素 nums[i]（1≤nums[i]≤100）。
    参数 k（1≤k≤n−1）为一个整数，表示限制索引。
    为了便于在线判题，第一行输入以英文逗号分隔的数组元素，第二行输入整数 k。
    
    输出描述
    输出符合条件的不同数组个数；无法构造出满足条件的数组时输出 0。
    */
    
    static int target; // 目标索引
    static int n;
    
    /**
     * start：检索起始
     * k：目标索引 k-1和k的值要求不同
     * nums：给定的原数组
     * set：存放的排列组合
     */
    // 会超时！！还是得用数学的方式  DFS可以参考leetcode46全排列
    static void dfs(int start, int[] nums, HashSet<List<Integer>> set) {
        if (start == n) {
            // 递归出口
            List<Integer> r = new ArrayList<>();
            for (int num : nums) {
                r.add(num);
            }
            // 当k-1与k下标不同的时候 合法值 存起来
            if (!r.get(target - 1).equals(r.get(target))) {
                set.add(r);
            }
            
            return;
        }
        
        for (int i = start; i < n; i++) {
            swap(i, start, nums);
            dfs(start + 1, nums, set);
            swap(i, start, nums);
        }
    }
    
    // 交换位置
    static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    static long countArrangements(int[] nums, int k) {
        // 1.初始化数据
        target = k; // 目标索引
        n = nums.length;
        Arrays.sort(nums); // 数组排序
        HashSet<List<Integer>> set = new HashSet<>(); // 存所有的排列组合（自带去重）
        
        // 2.DFS深度搜索 从0开始 遇到target-1和target相同值时 剪枝
        dfs(0, nums, set);
        
        return set.size();
    }
    
    static void main() {
        int[] nums = {2, 2, 3};
        int k = 1;
        // 2 322 223
        System.out.println("ans====" + countArrangements(nums, k));
        
        int[] nums1 = {1, 1, 2, 2};
        int k1 = 2;
        // 4
        System.out.println("ans====" + countArrangements(nums1, k1));
    }
}
