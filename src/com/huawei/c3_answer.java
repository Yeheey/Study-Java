package com.huawei;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringJoiner;
import java.util.stream.Stream;

public class c3_answer {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in).useDelimiter("[,\n]");
        int m = sc.nextInt(); // 评委数量
        int n = sc.nextInt(); // 选手数量
        if (m < 3 || m > 10 || n < 3 || n > 100) { // 判断输入是否合法
            System.out.println("-1");
            return;
        }
        int[][] scores = new int[m][n]; // 评委打分
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                scores[i][j] = sc.nextInt();
                if (scores[i][j] > 10 || scores[i][j] < 1) { // 判断队员得分是否合法
                    System.out.println("-1");
                    return;
                }
            }
        }
        HashMap<Integer, Integer[]> players = new HashMap<>(); // 选手编号和得分
        for (int j = 0; j < n; j++) {
            Integer[] playerScores = new Integer[m];
            for (int i = 0; i < m; i++) {
                playerScores[i] = scores[i][j];
            }
            Arrays.sort(playerScores, (a, b) -> b - a); // 将每个队员的得分降序排序
            players.put(j, playerScores);
        }
        StringJoiner sj = new StringJoiner(",");
        players.entrySet().stream().sorted((a, b) -> {
            Integer[] playerA = a.getValue(); // 选手A的得分
            Integer[] playerB = b.getValue(); // 选手B的得分
            int sumA = Stream.of(playerA).mapToInt(Integer::intValue).sum(); // 计算总分
            int sumB = Stream.of(playerB).mapToInt(Integer::intValue).sum();
            if (sumA != sumB) { // 按总分降序排列
                return sumB - sumA;
            }
            for (int i = 0; i < m; i++) {
                if (playerA[i] == playerB[i]) continue;
                return playerB[i] - playerA[i]; // 按最高分降序排列
            }
            return 0;
        }).limit(3).forEach(p -> sj.add(p.getKey() + 1 + "")); // 将前三名的选手编号加入StringJoiner
        System.out.println(sj.toString()); // 输出前三名的选手编号
    }
}


