package com.huawei;

public class v260802_3_todo {
    /*
    32.【26.8.2-3】战士技能规划设计
    题目描述
    你为一款动作游戏设计战士角色的技能：战士每个技能会消耗不同能量，释放技能有 2 个约束：连续释放技能数量不能超过 m 个，技能能量总和不能超过能量上限 k；
    如果超过则必须中断当前技能，进入调息状态（即分段）。
    战士有一项爆发技巧：在单次战斗中有一次能量上限翻倍至 2k 的机会，此项场景下战士需使用连续的 w 个技能（技能数量限制 m 依然生效）。
    
    任务目标：
    作为战术分析师，你需要为战士规划最优的技能释放序列。
    给定一套技能的能量消耗列表 a，请计算在合理使用爆发机会（或选择不使用）的前提下，释放完所有技能所需的最少分段数（即最少调息次数）。
    若存在某个技能的能耗过高，即使开启爆发也无法释放（大于 2k），则判定为无解，返回 −1。

    输入描述
    输入参数说明：
 
    k: 能量上限，正整数
    m: 单次调息最大技能数，正整数
    w: 爆发持续技能数，正整数
    a: 技能能量消耗列表，长度为 n，每个元素为正整数
    输入为一行，格式为：k,m,w,[a1,a2,...,an]
    数组中允许出现空格，例如 [1, 1, 1]。
    
    数据范围：
    n 为技能数量，即 a 的长度
    1≤w≤n
    1≤n≤100000
    1≤m≤n
    1≤k≤1000000000
    1≤a[i]≤1000000000
    
    输出描述
    输出满足条件的最少分段数（最少调息次数）。若无解则输出 −1。
    */
    static int[] buildPrefix(int[] skills, long limit, int maxCount, int inf) {
        int n = skills.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        long sum = 0;
        int left = 0;
        for (int right = 0; right < n; right++) {
            sum += skills[right];
            while (left <= right && (right - left + 1 > maxCount || sum > limit)) {
                sum -= skills[left++];
            }
            if (left > right || dp[left] >= inf) {
                dp[right + 1] = inf;
            } else {
                dp[right + 1] = dp[left] + 1;
            }
        }
        return dp;
    }
    
    static int[] buildSuffix(int[] skills, long limit, int maxCount, int inf) {
        int n = skills.length;
        int[] dp = new int[n + 1];
        dp[n] = 0;
        long sum = 0;
        int right = n - 1;
        for (int left = n - 1; left >= 0; left--) {
            sum += skills[left];
            while (right >= left && (right - left + 1 > maxCount || sum > limit)) {
                sum -= skills[right--];
            }
            if (right < left || dp[right + 1] >= inf) {
                dp[left] = inf;
            } else {
                dp[left] = dp[right + 1] + 1;
            }
        }
        return dp;
    }
    
    static int solve(int k, int m, int w, int[] skills) {
        // 单个技能超过爆发上限时，无论如何都无法释放
        int n = skills.length;
        int inf = n + 1;
        long burstLimit = 2L * k;
        for (int value : skills) {
            if (value > burstLimit) return -1;
        }
        // prefix 和 suffix 分别记录左右两侧在普通上限下的最少分段数
        int[] prefix = buildPrefix(skills, k, m, inf);
        int[] suffix = buildSuffix(skills, k, m, inf);
        int answer = prefix[n];
        // 枚举唯一一次爆发覆盖的连续 w 个技能
        if (w <= m) {
            long sum = 0;
            for (int i = 0; i < w; i++) sum += skills[i];
            for (int left = 0; left + w <= n; left++) {
                int right = left + w;
                if (sum <= burstLimit && prefix[left] < inf && suffix[right] < inf) {
                    answer = Math.min(answer, prefix[left] + 1 + suffix[right]);
                }
                if (right < n) {
                    sum -= skills[left];
                    sum += skills[right];
                }
            }
        }
        return answer >= inf ? -1 : answer;
    }
    
    void main() {
        int k = 5;
        int m = 3;
        int w = 2;
        int[] skills = {3, 4, 3, 4};
        // 3
        System.out.print(solve(k, m, w, skills));
    }
}
