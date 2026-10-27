package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test9extension {
/*
    题目名称：零钱组合总和（DFS 回溯经典题）
    题目描述:
    给定 5种不同面值的货币，面值分别为 `1、5、10、50、100`，每种面值的货币可以无限制选取多张。请你找出所有能够恰好凑出目标总金额的不同货币组合，返回全部合法的组合方案。
    组合不区分选取顺序，例如 `[1,5]`和 `[5,1]`视为同一种组合。
    
    输入参数:
    1. `coins`：整数数组，固定取值为 `[1,5,10,50,100]`，表示 5种货币的面值；
    2. `target`：非负整数，表示需要凑出的目标总金额。
    3. `nums`:对应面值张数
    
    返回值:
    返回一个二维整数数组：
        -每个子数组对应一种凑钱方案，子数组内的面值按从小到大的顺序排列；
        -整体结果无重复组合，所有方案均满足：子数组内元素之和等于 `target`。
        
    示例：
    输入：`coins = [1,5,10,50,100], nums = [2, 3, 5, 7, 99] target = 6`
    输出：`[[1,1,1,1,1,1], [1,5]]`
    
    输入：`coins = [1,5,10,50,100],nums = [0,0,0,0,0] target = 0`
    输出：`[[]]`（目标金额为 0 时，空组合视为合法方案）
        */
    
    static List<List<Integer>> solve(int[] coins, int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        backtrack(coins, nums, target, 0, new ArrayList<>(), res);
        return res;
    }
    /*
     * index是当前遍历的面值和数量
     * remain是剩余的钱
     * path是当前符合target的数组
     * res是二维数组，用于返回所有结果
     */
    
    static void backtrack(int[] coins, int[] nums, int remain, int index, List<Integer> path, List<List<Integer>> res) {
        if (remain == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        if (index == coins.length || remain < 0) {
            return;
        }
        
        // 选 0 个当前硬币
        backtrack(coins, nums, remain, index + 1, path, res);
        
        // 尝试选 1 个、2 个 ... 最多 nums[index] 个
        for (int k = 1; k <= nums[index]; k++) {
            int value = coins[index] * k;
            if (remain < value) break;
            
            path.add(coins[index]);
            backtrack(coins, nums, remain - value, index + 1, path, res);
        }
        
        // 回溯：把刚才加进去的硬币全部移除
        for (int k = 0; k < nums[index]; k++) {
            if (!path.isEmpty() && path.get(path.size() - 1) == coins[index]) {
                path.remove(path.size() - 1);
            }
        }
    }
    
    
    static void main() {
        int[] coins = {1, 5, 10, 50, 100};
        int[] nums = {2, 3, 5, 7, 99};
        int target = 6;
        System.out.print(solve(coins, nums, target));
    }
}
