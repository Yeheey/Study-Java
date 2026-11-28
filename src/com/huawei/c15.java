package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c15 {
/*
    【15.统计射击比赛成绩】--【字符串+排序】--30min
    题目描述
    给定一个射击比赛成绩单，包含多个选手若干次射击的成绩分数，请对每个选手按其最高3个分数之和进行降序排名，输出降序排名后的选手ID序列。
    
    一个选手可以有多个射击成绩的分数，且次序不固定。
    如果一个选手成绩少于3个，则认为选手的所有成绩无效，排名忽略该选手。
    如果选手的成绩之和相等，则成绩之和相等的选手按照其ID降序排列。
    输入描述
    输入第一行，一个整数N，表示该场比赛总共进行了N次射击，产生N个成绩分数（2<=N<=100）。
    输入第二行，一个长度为N整数序列，表示参与每次射击的选手ID（0<=ID<=99）。
    输入第三行，一个长度为N整数序列，表示参与每次射击的选手对应的成绩（0<=成绩<=100）。
    输出描述
    符合题设条件的降序排名后的选手ID序列。
    */
    
    public static String solve(int n, int[] ids, int[] scores) {
        // 下标是选手ID 值是对应分数 分数个数小于3 重置为空数组
        List<List<Integer>> playerScores = new ArrayList<>(); // size = 100
        // 用于存放所有选手最高分总成绩 空数组设置为-1
        int[] total = new int[100];
        // 存最高3个分数之和降序 相同分数ID降序 二维数组 [scores,id]
        List<int[]> res = new ArrayList<>(2);
        
        for (int i = 0; i < 100; i++) {
            // ID是0-99
            playerScores.add(new ArrayList<>());
        }
        
        for (int i = 0; i < n; i++) {
            int curId = ids[i];
            int curScore = scores[i];
            playerScores.get(curId).add(curScore);
        }
        
        for (int i = 0; i < 100; i++) {
            List<Integer> tmp = playerScores.get(i);
            if (tmp.isEmpty()) {
                total[i] = -1;
                continue;
            }
            if (tmp.size() < 3) {
                continue;
            }
            // 成绩降序排列
            tmp.sort((a, b) -> b - a);
            // 存总分
            total[i] = tmp.get(0) + tmp.get(1) + tmp.get(2);
            res.add(new int[]{i, total[i]});
        }
        
        // ✅️优化写法
        res.sort((a, b) -> a[1] != b[1]
            ? Integer.compare(b[1], a[1])          // 总分降序
            : Integer.compare(b[0], a[0]));        // 同分 ID 降序
        
        // res.sort((a, b) -> {
        //     if (a[1] - b[1] > 0) {
        //         return -1;
        //     } else if (a[1] - b[1] < 0) {
        //         return 1;
        //     } else {
        //         return b[0] - a[0];
        //     }
        // });
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < res.size(); i++) {
            sb.append(res.get(i)[0]);
            if (i != res.size() - 1) {
                sb.append(",");
            }
        }
        
        return sb.toString();
    }
    
    static void main() {
        int n = 14;
        int[] ids = {3, 3, 7, 4, 4, 4, 4, 7, 7, 3, 5, 5, 5, 6};
        int[] scores = {53, 80, 68, 24, 39, 76, 66, 16, 100, 55, 53, 80, 55, 33};
        // 5,3,7,4
        System.out.println(solve(n, ids, scores));
    }
}
