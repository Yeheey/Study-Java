package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test9_lei_example {
    static List<List<Integer>> res = new ArrayList<>();
    static int[] mCoins;
    static int[] mNums;
    
    static List<List<Integer>> solve(int[] coins, int[] nums, int target) {
        res.clear();
        mCoins = coins;
        mNums = nums;
        for (int i = 0; i < coins.length; i++) {
            DFS(0, target, new ArrayList<>(List.of(0, 0, 0, 0, 0)), i);
        }
        return res;
    }
    
    static void DFS(int cur, int target, List<Integer> path, int index) {
        if (mNums[index] <= 0) return;
        if (cur + mCoins[index] > target) return;
        cur += mCoins[index];
        mNums[index] -= 1;
        path.set(index, path.get(index) + 1);
        if (cur == target) {
            res.add(new ArrayList<>(path));
        }
        for (int i = 0; i < mCoins.length; i++) {
            DFS(cur, target, path, i);
        }
        cur -= mCoins[index];
        mNums[index] += 1;
        path.set(index, path.get(index) - 1);
        return;
        
    }
    
    static void main() {
        int[] coins = {1, 5, 10, 50, 100};
        int[] nums = {2, 3, 5, 7, 99};
        int target = 6;
        System.out.print(solve(coins, nums, target));
    }
}
