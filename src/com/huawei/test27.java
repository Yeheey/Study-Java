package com.huawei;

import java.util.Arrays;

public class test27 {
    /*
    27.【26.8.9-3】【一维状态压缩DP】云南菌子加工-test6
    题目描述
    云南的菌子加工厂要加工一批野生菌，所有菌子同时进厂。由于菌子新鲜度随时间流失，价值不断衰减。
    工厂不能同时加工菌子，即逐个串行加工，正在加工的菌子价值不再衰减，加工完毕立即售卖。
    
    已知每个菌子的初始市场价值和新鲜度衰减速度（每小时损失的价值），菌子的实际价值 = 初始价值 - 衰减速度 × 从进入工厂到开始加工的等待时间。
    在给定时间内，合理安排加工顺序使得加工完成的菌子总价值最大化。
    
    每个菌子的加工时间固定为 5 小时，加工所有菌子的总耗时不能超过给定的总加工时间。若菌子的实际价值衰减至零或负值，则不能再售卖，即不需要参与加工。
    
    输入描述
    输入为四行：count total values decays
    count 表示菌子数量，1 <= count <= 15
    total 表示可用总加工时间，5 <= total <= 75，单位小时
    values[i] 表示第 i 个菌子的初始市场价值
    decays[i] 表示第 i 个菌子的衰减速度
    数组 values 和 decays 按示例使用英文逗号分隔。
    
    输出描述
    输出一个整数，表示在给定时间内能加工完成的菌子的最大总价值。
    */
    
    // dp[mask] 的含义：恰好加工完 mask 这个集合里的菌子（按某种最优顺序）时，能获得的最大累计收益；
    // neg 表示该状态不可达。
    static int solve(int count, int total, int[] values, int[] decays) {
        // mask 表示已经加工的菌子集合，集合大小决定当前等待时间
        int maxCount = total / 5; // 时间上限换算成"最多能加工几个"
        int size = 1 << count; // 状态总数 2^count，count≤15 → 最多 32768
        int neg = Integer.MIN_VALUE / 4; // 负无穷哨兵（除以4防止后面相加溢出）
        
        // 设置dp状态数组
        int[] dp = new int[size];
        // 把数组里所有值 初始化为极小值
        Arrays.fill(dp, neg);
        dp[0] = 0;  // 空集：没加工任何菌子，收益0，是唯一合法起点
        int answer = 0;
        
        for (int mask = 0; mask < size; mask++) {
            if (dp[mask] < 0) continue;  // 不可达状态剪枝
            // 任意中间状态都可能是答案（不必加工满）
            answer = Math.max(answer, dp[mask]);
            
            int used = Integer.bitCount(mask); // 已加工个数 = 当前时刻 / 5
            if (used == maxCount) continue; // 时间用尽，不能再加
            int wait = used * 5;   // 下一个菌子的等待时间
            
            for (int i = 0; i < count; i++) {
                if ((mask & (1 << i)) == 0) {  // 第 i 个还没加工
                    int gain = values[i] - decays[i] * wait;
                    if (gain <= 0) continue; // 衰减到 0 或负，放弃
                    int next = mask | (1 << i); // 置位，加入集合
                    dp[next] = Math.max(dp[next], dp[mask] + gain);
                }
            }
        }
        return answer;
    }
    
    
    static void main() {
        int count = 3;
        int total = 20;
        int[] value = {20, 10, 15};
        int[] decays = {3, 1, 2};
        // 25
        System.out.print(solve(count, total, value, decays));
    }
}
