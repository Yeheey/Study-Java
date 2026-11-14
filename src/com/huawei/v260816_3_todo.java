package com.huawei;

import java.util.*;

public class v260816_3_todo {
    /*
    21.【26.8.16-3】智能家居模式调度优化 AI
    题目描述
    在一个智能家居系统四中，用户可以设置多个“自动化联动模式。每个模式 modes[i]=[start_time,end_time,power]描述如下:
    · 模式在左闭右开区间[start_time,end_time)(单位:秒)内处于运行状态
    · 为了简化处理，start_time、end_time已转化为相对于系统启动初始时间点的秒数
    · 运行期间，每秒消耗固定功率 power
    
    已知家里电网的最高承受功率max_power。若同一时刻有多个模式同时运行，则总功率为各模式功率之和。如果总功率大于max_power，则会导致跳闸。
    系统允许永久删除(关闭)任意若干个模式，使剩余模式在任意时刻同时运行时永不跳闸。
    请你计算:最少需要删除多少个模式?
    
    
    输入描述：
    · max_power:整数代表用户电网能承受的最大总电功率，取值1<=max_power<=10^6
    · modes:二维数组，每个子数组nodes[i]-[start_time,end_time,power]，表示第i个智能模式的启动时间、结束时间以及运行它所需要的电功率。
    · start_time,end_time: 取值 0 <= start_time < end_time <= 10^5
    · power:取值1<=power<=10^4
    · 模式数量取值1<=n<=22
    
    输出描述：
    最少需要删除的模式个数。
    */
    
    static int maxPower;
    static int[] segPower;       // 每个离散时间段当前的累计功率
    static int[] startId, endId; // 每个模式覆盖的段下标区间 [startId, endId)
    static int[] power;
    static int n, best;
    
    public static int solve(int max_power, int[][] modes) {
        maxPower = max_power;
        n = modes.length;
        if (n == 0) {
            return 0;
        }
        
        // ---------- 1. 时间离散化 ----------
        TreeSet<Integer> set = new TreeSet<>(); // 用于存每个时间点
        for (int[] m : modes) {
            set.add(m[0]);
            set.add(m[1]);
        }
        List<Integer> ts = new ArrayList<>(set);
        Map<Integer, Integer> idx = new HashMap<>();
        for (int i = 0; i < ts.size(); i++) {
            idx.put(ts.get(i), i);
        }
        
        int segCnt = ts.size() - 1;
        segPower = new int[segCnt];
        startId = new int[n];
        endId = new int[n];
        power = new int[n];
        for (int i = 0; i < n; i++) {
            startId[i] = idx.get(modes[i][0]);
            endId[i] = idx.get(modes[i][1]);
            power[i] = modes[i][2];
        }
        
        // ---------- 2. 功率大的优先尝试保留，便于尽早剪枝 ----------
        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;
        Arrays.sort(order, (a, b) -> power[b] - power[a]);
        
        // ---------- 3. DFS 枚举保留/删除 ----------
        best = 0;
        dfs(0, 0, order);
        return n - best;
    }
    
    /**
     * @param pos 当前决策到 order 中的第几个模式
     * @param cnt 已保留的模式个数
     */
    static void dfs(int pos, int cnt, Integer[] order) {
        // 剪枝：剩余模式全部保留也无法超过当前最优
        if (cnt + (n - pos) <= best) return;
        if (pos == n) {
            best = Math.max(best, cnt);
            return;
        }
        
        int i = order[pos];
        
        // 分支一：尝试保留
        if (add(i)) {
            dfs(pos + 1, cnt + 1, order);
            remove(i);
        }
        
        // 分支二：删除该模式
        dfs(pos + 1, cnt, order);
    }
    
    /**
     * 先校验、后累加，避免超限回滚时漏减（原 bug 所在）
     */
    static boolean add(int i) {
        for (int s = startId[i]; s < endId[i]; s++) {
            if (segPower[s] + power[i] > maxPower) return false;
        }
        for (int s = startId[i]; s < endId[i]; s++) {
            segPower[s] += power[i];
        }
        return true;
    }
    
    static void remove(int i) {
        for (int s = startId[i]; s < endId[i]; s++) {
            segPower[s] -= power[i];
        }
    }
    
    
    public static void main(String[] args) {
        int[] maxPowers = {10, 10, 10, 10, 5, 100, 15, 1000000, 1, 3, 20, 12};
        int[][][] modesArr = {{{0, 500, 5}, {400, 600, 7}, {550, 700, 4}}, {{0, 100, 8}, {100, 200, 8}}, {{0, 100, 6}, {50, 150, 6}, {90, 110, 6}}, {{0, 100, 6}, {0, 100, 6}, {0, 100, 6}}, {{0, 1000, 9}}, {{0, 300, 10}, {100, 400, 20}, {200, 500, 30}}, {{0, 200, 10}, {100, 300, 10}, {250, 400, 10}}, {{0, 100, 10000}, {100, 200, 10000}, {200, 300, 10000}}, {{0, 10, 1}, {10, 20, 1}}, {{0, 50, 2}, {25, 75, 2}, {50, 100, 2}}, {{0, 100000, 5}, {99999, 100000, 20}}, {{0, 60, 4}, {20, 80, 4}, {40, 100, 4}, {70, 90, 4}}};
        int[] expect = {1, 0, 2, 2, 1, 0, 1, 0, 0, 1, 1, 1};
        
        for (int i = 0; i < expect.length; i++) {
            int got = solve(maxPowers[i], modesArr[i]);
            System.out.printf("case %2d: max=%-7d expect=%d got=%d %s%n", i + 1, maxPowers[i], expect[i], got, got == expect[i] ? "OK" : "FAIL");
        }
    }
}
