package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class c27_ai_answer {
    /*
        【27.小明减肥】--【动态规划+DFS+回溯】
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
     * idx-下一个运动从这里开始选，保证下标递增，组合不重复
     * path-可行解 path.size()就是已选的个数
     * remainsCalorie = targetCalorie - path总和
     * remainsNum = targetNum - path.size()
     * calorie-卡路里 长度为n
     */
    public static void dfs(int idx, List<Integer> path, int remainsCalorie, int remainsNum, int[] calorie) {
        // 已经选够 targetNum 个，剩余卡路里刚好为 0 才是可行解
        if (remainsNum == 0) {
            if (remainsCalorie == 0) {
                // ✅️不要忘记重新new一个
                res.add(new ArrayList<>(path));
            }
            return;
        }
        // 运动不够再选，或剩余卡路里已经不可能（后面都是正数）
        if (idx >= caLen || remainsCalorie <= 0 || caLen - idx < remainsNum) {
            return;
        }
        
        for (int i = idx; i < caLen; i++) {
            // 数组已排序，当前运动都超了，后面更大，不用再试
            if (calorie[i] > remainsCalorie) {
                break;
            }
            path.add(calorie[i]);
            // 选了 i 之后只能从 i+1 继续，避免 [1,2] 和 [2,1] 各算一次
            dfs(i + 1, path, remainsCalorie - calorie[i], remainsNum - 1, calorie);
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
        // 从下标 0 开始选。循环放在 dfs 里面，每个起点都会被试到
        dfs(0, path, targetCalorie, targetNum, calorie);
        
        System.out.println("res:--" + res);
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
