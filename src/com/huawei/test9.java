package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test9 {

/*
    9.【26.8.30-3】单词搜索计数
    题目描述：
    给定一个mxn的二维字符网格board 和一个字符串单词word。
    请计算单词word在网格中出现的总次数。
    单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻"单元格是那些水平相邻或垂直相邻的单元格。同一个单
    元格内的字母在一个搜索路径中不允许被重复使用。
    输入描述：
    bord:二维字符列表，每个元素为大写英文字母，1≤m,n≤10。
    word:字符串，由大写英文字母组成，1≤len(word)≤100。
    输出描述：
    返回一个整数，表示单词在网格中出现的路径总数。
    */
    
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    private static int wordCounts = 0;
    private static int m = 0;
    private static int n = 0;
    
    // 深度优先搜索
    public static void dfs(int i, int j, String current, String target, List<List<Boolean>> visited, List<List<String>> bord) {
        // 目标字符
        if (i < 0 || i >= m || j < 0 || j >= n || visited.get(i).get(j) == Boolean.TRUE) {
            return;
        }
        current += bord.get(i).get(j);
        visited.get(i).set(j, Boolean.TRUE);
        if (current.equals(target)) {
            wordCounts += 1;
            return;
        }
        // 剪枝，避免无效递归
        if (!target.startsWith(current)) {
            return;
        }
        
        for (final int[] direction : directions) {
            int newI = i + direction[0];
            int newJ = j + direction[1];
            dfs(newI, newJ, current, target, visited, bord);
        }
        visited.get(i).set(j, Boolean.FALSE);
        current = current.substring(0, current.length() - 1);
    }
    
    public static int solve(List<List<String>> bord, String word) {
        m = bord.size();
        n = bord.get(0).size();
        
        // 构建与bord大小一致的visited
        List<List<Boolean>> visited = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            List<Boolean> row = new ArrayList<>(n);
            for (int j = 0; j < n; j++) {
                row.add(false);
            }
            visited.add(row);
        }
        
        
        for (int i = 0; i < bord.size(); i++) {
            for (int j = 0; j < bord.get(i).size(); j++) {
                dfs(i, j, "", word, visited, bord);
            }
        }
        
        
        return wordCounts;
    }
    
    static void main() {
        List<List<String>> board = List.of(List.of("A", "B", "C", "E"), List.of("S", "F", "C", "S"), List.of("A", "D", "E", "E"));
        String s = "ABCCED";
        // output:1
        System.out.print(solve(board, s));
    }
}
