package com.huawei;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class v260913_2 {
    /*
    【26.9.13-2】受限序列重排
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
    
    // 暴力枚举会超时！
    static int countArrangements(int[] nums, int k) {
        HashSet<List<Integer>> set = new HashSet<>();
        backtrack(nums, 0, set, k);
        
        return set.size();
    }
    
    static void backtrack(int[] nums, int start, HashSet<List<Integer>> set, int k) {
        // 递归的出口 存自身
        if (start == nums.length) {
            List<Integer> r = new ArrayList<>();
            for (int num : nums) {
                r.add(num);
            }
            // 剪枝
            if (!Objects.equals(r.get(k - 1), r.get(k))) {
                set.add(r);
            }
            return;
        }
        
        // 从start开始交换递归
        for (int i = start; i < nums.length; i++) {
            swap(i, start, nums);
            backtrack(nums, start + 1, set, k);
            // 回溯
            swap(i, start, nums);
        }
        
        
    }
    
    static void swap(int i, int j, int[] nums) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    // 方法2
    static long solve(int[] nums, int k) {
        int n = nums.length;
        // 边界判断：数组长度小于2，不存在一对相邻位置；k越界直接返回0
        if (n < 2 || k < 1 || k >= n) {
            return 0;
        }
        
        // frequency[val]：记录数字val出现的次数，题目规定数字范围1~100，开101够用
        int[] frequency = new int[101];
        for (int value : nums) {
            frequency[value]++;
        }
        
        // factorial[i] = i! 阶乘数组，用来计算多重集合排列数
        long[] factorial = new long[n + 1];
        factorial[0] = 1; // 0! = 1
        for (int i = 1; i <= n; i++) {
            factorial[i] = factorial[i - 1] * i;
        }
        
        // total：多重集合，所有【去重之后】的排列总数
        long total = factorial[n];
        // samePair：统计：任选两个位置，放同一个数字的有序对数量
        // 比如某个数出现ci次，从中有序选2个：ci*(ci-1)
        long samePair = 0;
        for (int cnt : frequency) {
            if (cnt > 0) {
                // 多重排列公式：除以重复元素的阶乘，消除相同元素带来的重复排列
                total /= factorial[cnt];
                samePair += (long) cnt * (cnt - 1);
            }
        }
        
        // allPair：从n个元素里有序选2个放到这两个位置，总共有多少种有序组合
        long allPair = (long) n * (n - 1);
        // diffPair：这两个位置数字不相等的有序组合数量
        long diffPair = allPair - samePair;
        
        // 优化：先约分，再相乘，避免 total * diffPair 先乘导致long溢出
        // 数学上保证：total * diffPair 一定可以被 allPair 整除，不会出现小数
        long g = gcd(diffPair, allPair);
        diffPair /= g;
        allPair /= g;
        
        return total * diffPair / allPair;
    }
    
    /**
     * 辗转相除法 求最大公约数gcd
     * 用来约分，降低数值大小，减少溢出风险
     */
    static long gcd(long a, long b) {
        return b == 0 ? a : gcd(b, a % b);
    }
    
    static void main() {
        int[] nums = {2, 2, 3};
        int k = 1;
        System.out.print(countArrangements(nums, k));
        // 2 322 223
        
        System.out.println(solve(nums, k));
    }
    
    
}
