package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class v260830_3_ysy_extension {
/*
    【零钱组合总和】--【动态规划+DFS+回溯】--【c27】
    题目描述：
    给定 5 种面值的货币，面值依次为 `1、5、10、50、100`。每种面值货币有数量上限，不能无限取用。
    请找出所有恰好凑出目标总金额的货币组合。
    组合不区分选取顺序，`[1,5]` 和 `[5,1]` 算作同一个组合，结果里只保留一种。
    
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
    输出：`[1,5]]`
    
    输入：`coins = [1,5,10,50,100],nums = [0,0,0,0,0] target = 0`
    输出：`[[]]`（目标金额为 0 时，空组合视为合法方案）
        */
    
    static int[] remains; // 剩余面值的张数
    static int targetNum; // 目标金额总数
    
    /**
     * idx - 遍历面值的下标 0-4
     * remains - 剩余张数
     * nums - 实际对应面值张数
     * targetNum - 目标金额
     * coins = [1,5,10,50,100] 对应就是idx 的0 1 2 3 4
     */
    static void dfs(int idx, List<Integer> path, List<List<Integer>> res, int[] coins) {
        // 全部面值遍历完毕，判断是否满足
        if (idx == coins.length) {
            int curTotal = 0;
            for (int pa : path) {
                curTotal += pa;
            }
            if (curTotal == targetNum) {
                res.add(new ArrayList<>(path));
            }
            return;
        }
        
        // 当前面值最多可以取多少张
        int maxTake = remains[idx];
        // 枚举：取 0张、1张 ... maxTake张
        for (int take = 0; take <= maxTake; take++) {
            // 拿take张，加入path
            for (int t = 0; t < take; t++) {
                path.add(coins[idx]);
            }
            
            // 去下一个面值（idx+1，保证只往后走，不会重复组合）
            dfs(idx + 1, path, res, coins);
            // 回溯：删掉刚刚加的take张
            for (int t = 0; t < take; t++) {
                if (!path.isEmpty()) {
                    path.removeLast();
                }
            }
        }
    }
    
    /**
     * coins = [1,5,10,50,100]
     * nums - 对应面值张数
     * target - 目标金额
     */
    static List<List<Integer>> solve(int[] coins, int[] nums, int target) {
        // 初始化 剩余张数
        remains = Arrays.copyOf(nums, 5);
        targetNum = target;
        
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        
        // 特殊：target=0，空方案
        if (target == 0) {
            res.add(new ArrayList<>());
            return res;
        }
        
        // 从第0种面值开始，顺序遍历，不再循环多次起点（避免重复组合）
        dfs(0, path, res, coins);
        return res;
    }
    
    static void main() {
        int[] coins = {1, 5, 10, 50, 100};
        int[] nums = {2, 3, 5, 7, 99};
        int target = 6;
        // [[1, 5]]
        System.out.print("ans:" + solve(coins, nums, target));
        
        int[] nums1 = {0, 1, 0, 0, 0};
        int target1 = 5;
        // [[5]]
        System.out.print("ans:" + solve(coins, nums1, target1));
    }
}
