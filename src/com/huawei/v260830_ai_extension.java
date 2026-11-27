package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class v260830_ai_extension {
    static int[] remains;
    static int targetNum;
    
    // idx：当前处理第几种纸币，path：当前方案
    static void dfs(int idx, List<Integer> path, List<List<Integer>> res, int[] coins) {
        if(idx == coins.length) {
            // 到最后一种面值，判断总和
            int sum = 0;
            for(int x : path) sum += x;
            if(sum == targetNum) {
                res.add(new ArrayList<>(path));
            }
            return;
        }
        // 当前面值最多拿 cnt 张
        int maxTake = remains[idx];
        // 枚举拿 0 ~ maxTake 张
        for(int take = 0; take <= maxTake; take++) {
            // 拿take张，加入path
            for(int t = 0; t < take; t++) {
                path.add(coins[idx]);
            }
            dfs(idx+1, path, res, coins);
            // 回溯：删掉刚刚加的take张
            for(int t = 0; t < take; t++) {
                if(!path.isEmpty()) path.removeLast();
            }
        }
    }
    
    static List<List<Integer>> solve(int[] coins, int[] nums, int target) {
        remains = Arrays.copyOf(nums, coins.length);
        targetNum = target;
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        dfs(0, path, res, coins);
        return res;
    }
    
    static void main() {
        int[] coins = {1, 5, 10, 50, 100};
        int[] nums = {2, 3, 5, 7, 99};
        int target = 6;
        // [[1, 5]]
        System.out.print("ans:" + solve(coins, nums, target));
    }
}
