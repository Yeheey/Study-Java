package com.huawei;

public class v260916_1_ysy {
    /*
    【26.9.16-1】统计特殊数字--【想不到递归啊，【26.9.13-2】】
    题目描述
    已知所有大于 1 的整数都可以被唯一分解成质数的乘积，我们把这组质数称为该整数的质因子；如：12=2×2×3，12 的质因子为 [2,3]，7=1×7，7 的质因子为 [7]。
    现给定一个正整数 n 和一个质数列表 nums（数字严格递增），请统计数字 1∼n 之中，有哪些数字的质因子仅出现在列表 nums 内，返回符合条件的数字数量。
    注意：数字 1 无质因子，作为符合条件的数字纳入统计。
    质数定义：大于 1 的自然数，除了 1 和它自身外不能被其他自然数整除的数。
    数据范围：整数 n 范围：1≤n≤10^12；列表 nums 长度 m 范围：1≤m≤5。
    
    输入描述
    输入分为三行：第一行是正整数 n，第二行是质数列表长度 m，第三行是以英文逗号分隔的质数列表 nums。
    
    输出描述
    输出一个整数，表示 1∼n 中质因子仅出现在列表 nums 内的正整数数量。
    */
    
    static int ans = 1;
    static int len = 0;
    
    static int solve(int n, int m, int[] nums) {
        len = m;
        dfs(0, nums, 1, n);
        return ans;
    }
    
    /**
     * idx:质数列表下标
     * nums：质数列表
     * curVal：当前乘积
     * limit:上限
     */
    static void dfs(int idx, int[] nums, long curVal, int limit) {
        for (int i = idx; i < len; i++) {
            if (curVal * nums[i] > limit) {
                break;
            }
            ans++;
            dfs(i, nums, curVal * nums[i], limit);
        }
    }
    
    static void main() {
        int n = 15;
        int m = 3;
        int[] nums = {2, 3, 5};
        System.out.print(solve(n, m, nums));
    }
}
