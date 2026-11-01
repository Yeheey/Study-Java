package com.huawei;

import java.util.Arrays;
import java.util.List;

public class test6 {
    /*
    6.【26.9.2-3】无人机巡检航线规划
    题目描述：
    某电力公司使用无人机Q对n个电力塔进行巡检。每个电力塔i位于坐标(xi,yi)，无人机园从基地(坐标(0,0)出发，
    需要依次飞达每个电力塔完成巡检后结束任务，无需返回基地。
    无人机同一时刻只能飞向一个电力塔，请规划巡检顺序，使无人机飞行的总距离最短(两个坐标点间按曼哈顿距离计算，即|x1-x2|+ly1-y2|)，输出最短总距离。

    输入描述：
    ·n:电力塔数量，1≤n≤15
    ·xi,yi:第i个电力塔的坐标，0≤xi,yis200
    
    输出描述：
    输出最短总距离(整数)
    */
    
    static int solve(int n, List<List<Integer>> nums) {
        // n:电力塔数量，1≤n≤15
        // xi,yi:第i个电力塔的坐标，0≤xi,yi≤200
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
            x[i] = nums.get(i).get(0);
            y[i] = nums.get(i).get(1);
        }
        
        int full = (1 << n) - 1;
        int[][] dp = new int[1 << n][n];
        for (int[] row : dp) Arrays.fill(row, Integer.MAX_VALUE / 2);
        
        // 从基地(0,0)出发到第i个塔
        for (int i = 0; i < n; i++) {
            dp[1 << i][i] = Math.abs(x[i]) + Math.abs(y[i]);
        }
        
        for (int mask = 1; mask <= full; mask++) {
            for (int last = 0; last < n; last++) {
                if ((mask & (1 << last)) == 0) continue;
                if (dp[mask][last] >= Integer.MAX_VALUE / 2) continue;
                for (int next = 0; next < n; next++) {
                    if ((mask & (1 << next)) != 0) continue;
                    int newMask = mask | (1 << next);
                    int dist = Math.abs(x[last] - x[next]) + Math.abs(y[last] - y[next]);
                    dp[newMask][next] = Math.min(dp[newMask][next], dp[mask][last] + dist);
                }
            }
        }
        
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            ans = Math.min(ans, dp[full][i]);
        }
        return ans;
    }
    
    static void main() {
        int n = 3;
        List<List<Integer>> nums = List.of(List.of(1, 2), List.of(3, 1), List.of(2, 3));
        System.out.print(solve(n, nums));
    }
    
/*  解题思路
    核心思想
    这题本质上是一个小规模旅行商问题，但起点固定在基地(0,0)，且巡检结束后不需要回基地。
    因为n<=15，可以用状态压缩动态规划四。dp[mask][i]表示已经访问过的塔集合Q为mask，并且最后停在塔i时的
    最短总距离。起点距离则是从基地直接飞到第一座塔的曼哈顿距离。
    算法Q步骤
        1.预处理任意两座塔之间的曼哈顿距离。
        2.初始化所有单点状态:基地到第i座塔的距离。
        3.枚举访问集合mask，再枚举当前停留塔i。
        4.尝试把未访问的塔j接到后面，更新 dp[mask|(1<<j)][j]。
        5.最终答案是访问完全部塔后，停在任意塔的最小值。
    复杂度分析
    设塔数为n。
        ·时间复杂度:0(2^n*n^~2)
        ·空间复杂度:0(2^n*n)*/
}
