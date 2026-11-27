package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class c27 {
    /*
        【27.小明减肥】--【动态规划+DFS+回溯】--【v260830_3_ysy_extension】
        题目描述
        小明有n个可选运动，每个运动有对应卡路里，想选出其中k个运动且卡路里和为t。
        k，t，n都是给定的。求出可行解数量
        
        输入描述
        第一行输入 n t k
        第一行输入 每个运动的卡路里 按照空格进行分割
        
        备注：0 < n < 10，t > 0, 0 < k <= n
        每个运动量的卡路里 > 0
        输出描述：求出可行解数量
    */
    
    static int targetCalorie = 0; // 目标选中的卡路里
    static int targetNum = 0; // 目标选中的数量
    static int caLen = 0; // 可选运动数量
    static List<List<Integer>> res = new ArrayList<>(); // 可行解存结果中 res=temp.size()
    
    /**
     * idx-当前选中的calorie下标
     * path-可行解 path.size()就是已选的个数
     * remainsCalorie = targetCalorie - path总和
     * remainsNum = targetNum - path.size()
     * n-可选运动个数
     * t-卡路里总和
     * k-目标选中运动个数
     * calorie-卡路里 长度为n
     */
    public static void dfs(int idx, List<Integer> path, int remainsCalorie, int remainsNum, int[] calorie) {
        // 递归出口 ✅️当前运动已经大于剩余卡路里。
        if (idx == caLen || remainsNum <= 0 || calorie[idx] > remainsCalorie) {
            return;
        }
        
        path.add(calorie[idx]);
        // 计算当前卡路里总和
        int total = 0;
        for (final int p : path) {
            total += p;
        }
        remainsNum = targetNum - path.size();
        remainsCalorie = targetCalorie - total;
        
        // 可行解存入结果中
        if (total == targetCalorie && path.size() == targetNum) {
            // ✅️不要忘记重新new一个
            res.add(new ArrayList<>(path));
        }
        
        // 递归下一个
        for (int i = idx + 1; i < caLen; i++) {
            dfs(i, path, remainsCalorie, remainsNum, calorie);
        }
        
        // 不合法 回溯
        if (!path.isEmpty()) {
            path.removeLast();
        }
    }
    
    public static int solve(int n, int t, int k, int[] calorie) {
        if (calorie == null || k <= 0 || k > n || t <= 0 || calorie.length < n) {
            return 0;
        }
        Arrays.sort(calorie);
        // 初始化
        res = new ArrayList<>();
        targetCalorie = t;
        targetNum = k;
        caLen = n;
        
        List<Integer> path = new ArrayList<>(); // 可行解 要求长度为k 和为t
        // ✅️改：每个下标都作为起点试一次。
        for (int i = 0; i < caLen; i++) {
            dfs(i, path, targetCalorie, targetNum, calorie);
        }
        
        System.out.println("res" + res);
        return res.size();
    }
    
    static void main() {
        int n = 4; // 可选运动
        int t = 3; // 卡路里和
        int k = 2; // 目标选中
        int[] calorie = {1, 1, 2, 3};
        System.out.println(solve(n, t, k, calorie));
    }
}
