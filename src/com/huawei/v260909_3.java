package com.huawei;

import java.util.Arrays;

public class v260909_3 {
    /*
    【26.9.9-3】受限任务分配
    题目描述
    某部门有一批待处理任务，数量为 x；系统按轮次处理任务，每次平分后只保留其中一组进入下一轮，当 x=1 时，处理结束，不再执行任何操作。
    每一轮按照以下规则处理：
    当 x 为偶数时，执行一次平分操作：
    任务数变为 x/2，消耗 1 点成本，操作次数增加 1。
    当 x 为奇数时，先按如下选择调整任务数为偶数，然后再执行一次平分操作：
    新增 1 次任务：任务数变为 x+1，消耗 add_cost 点成本，操作次数增加 1；
    减少 1 次任务：任务数变为 x−1，消耗 return_cost 点成本，操作次数增加 1。
    当给定一组 new_tasks，请找出平分任务至 1 时，总成本消耗最少的方案；若达到最低总成本的方案有多种，选择其中操作次数最少的方案。
 
    输入描述
    new_tasks：当前任务数，1≤new_tasks≤10000
    budget：成本预算上限，1≤budget≤50
    add_cost：获取 1 个任务的成本，1≤add_cost≤10
    return_cost：回收 1 个任务的成本，1≤return_cost≤10
    
    输出描述
    输出 [最低总成本, 最少操作次数]，如果最低总成本超过 budget，输出 [-1,-1]；如果输入任务数为 1，直接输出 [0,0]。
    */
    static int[] solve(int newTasks, int budget, int addCost, int returnCost) {
        int[] res = new int[2];
        
        // 如果输入任务数为 1，直接输出 [0,0]
        if (newTasks == 1) {
            return new int[]{0, 0};
        }
        
        final int INF = 1_000_000_000;
        int[] cost = new int[newTasks + 1]; // 成本
        int[] steps = new int[newTasks + 1]; // 操作数
        for (int i = 0; i < newTasks + 1; i++) {
            // 初始化每一个任务数的成本和操作数都为最大
            cost[i] = INF;
            steps[i] = INF;
        }
        // 当task数量为1时 需要花费的成本为0 操作步数为0
        cost[1] = 0;
        steps[1] = 0;
        
        // 反向DP
        for (int tasks = 2; tasks < newTasks + 1; tasks++) {
            if (tasks % 2 == 0) {
                // 偶数：直接平分到 half
                int half = tasks / 2;
                // 成本 +1 操作数 +1
                cost[tasks] = cost[half] + 1;
                steps[tasks] = steps[half] + 1;
            } else {
                // 奇数两条分支：+1后平分 / -1后平分
                int up = (tasks + 1) / 2;
                int down = (tasks - 1) / 2;
                int upCost = cost[up] + addCost + 1;
                int upSteps = steps[up] + 2;
                int downCost = cost[down] + returnCost + 1;
                int downSteps = steps[down] + 2;
                // 优先成本小；成本相同，操作次数小
                if (upCost < downCost || (upCost == downCost && upSteps <= downSteps)) {
                    cost[tasks] = upCost;
                    steps[tasks] = upSteps;
                } else {
                    cost[tasks] = downCost;
                    steps[tasks] = downSteps;
                }
            }
        }
        
        // 如果最低总成本超过 budget，输出 [-1,-1]
        if (cost[newTasks] > budget) {
            return new int[]{-1, -1};
        }
        
        res[0] = cost[newTasks];
        res[1] = steps[newTasks];
        
        return res;
    }
    
    static void main() {
        int new_tasks = 5;
        int budget = 5;
        int add_cost = 2;
        int return_cost = 1;
        // output [3,3]
        System.out.print(Arrays.toString(solve(new_tasks, budget, add_cost, return_cost)));
    }
}
