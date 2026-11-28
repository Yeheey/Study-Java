package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class c22_ysy {
    /*
    【22.查找单入口空闲区域】--【DFS】--二刷50min = 40min写+10min调试
    题目描述
    给定一个 m x n 的矩阵，由若干字符 ‘X’ 和 ‘O’构成，’X’表示该处已被占据，’O’表示该处空闲，请找到最大的单入口空闲区域。
    
    解释：
    空闲区域是由连通的’O’组成的区域，位于边界的’O’可以构成入口，
    单入口空闲区域：有且只有一个位于边界的’O’作为入口的由连通的’O’组成的区域。
    如果两个元素在水平或垂直方向相邻，则称它们是“连通”的。
    
    输入描述
    第一行输入为两个数字，第一个数字为行数m，第二个数字为列数n，两个数字以空格分隔，1<=m,n<=200。
    剩余各行为矩阵各行元素，元素为‘X’或‘O’，各元素间以空格分隔。
    
    输出描述
    1.若存在唯一最大单入口空闲区域: 输出三个数字:入口行坐标、入口列坐标、区域大小,三个数字以空格分隔；
    2.若存在多个符合要求的最大区域: 输出区域大小，不需要输出入口坐标。
    3.若没有任何符合条件的单入口区域: 输出NULL
    */
    
    static int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    static boolean[][] visited;
    // 用于存合法单入口的区域 key-[x,y]坐标 value-面积大小
    static HashMap<List<Integer>, Integer> map; // key值不要用int[]
    
    static int inCnt = 0; // 入口数量
    static int x = -1;  // 入口横坐标
    static int y = -1;// 入口纵坐标
    static int area = 0; // 本次搜索的区域面积
    
    /**
     * r 当前横坐标
     * c 当前纵坐标
     * matrix 区域
     * m 区域横坐标长度
     * n 区域纵坐标长度
     * inCnt 当前区域搜索的入口数量
     * area 当前区域的面积
     **/
    static void dfs(int r, int c, char[][] matrix, int m, int n) {
        if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c] || matrix[r][c] == 'X') {
            return;
        }
        
        visited[r][c] = true;
        // 计算面积
        area++;
        // System.out.println("面积计数" + area);
        
        // 计算入口数量 并保存入口坐标
        if (r == 0 || r == m - 1 || c == 0 || c == n - 1) {
            inCnt++;
            x = r;
            y = c;
        }
        
        
        for (final int[] direction : directions) {
            int newI = r + direction[0];
            int newJ = c + direction[1];
            // if (newI >= 0 && newI < m && newJ >= 0 && newJ < n && !visited[newI][newJ] && matrix[newI][newJ] == 'O') {
            dfs(newI, newJ, matrix, m, n);
            // }
        }
        
        
    }
    
    static String solve(int m, int n, char[][] matrix) {
        if (m == 0 || n == 0 || matrix == null) {
            return "NULL";
        }
        
        // 初始化
        map = new HashMap<>();
        visited = new boolean[m][n];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 'O' && !visited[i][j]) {
                    // 只有是O 且没有访问过的 才深度搜索
                    inCnt = 0;
                    x = -1;
                    y = -1;
                    area = 0;
                    dfs(i, j, matrix, m, n);
                    // 这一片区域搜索完毕了 检查入口数量是否仅有1个 符合的话存入map
                    if (inCnt == 1 && x != -1 && y != -1) {
                        List<Integer> point = new ArrayList<>();
                        point.add(x);
                        point.add(y);
                        map.put(point, area);
                    }
                }
            }
        }
        
        // map.forEach((key, val) -> {
        //     System.out.println("key" + Arrays.toString(key));
        //     System.out.println("面积：" + val);
        // });
        
        String res = "NULL";
        List<List<Integer>> list = new ArrayList<>(); // 把map转为数组 放 key value 3个数字
        if (!map.isEmpty()) {
            map.forEach((key, value) -> {
                List<Integer> temp = new ArrayList<>();
                temp.add(key.get(0));
                temp.add(key.get(1));
                temp.add(value);
                list.add(temp);
            });
        }
        list.sort((a, b) -> {
            return b.get(2) - a.get(2);
        });
        // System.out.println(list);
        
        if (list.size() == 1 || (list.size() > 1 && list.get(0).get(2) > list.get(1).get(2))) {
            res = list.getFirst().get(0) + " " + list.getFirst().get(1) + " " + list.getFirst().get(2);
        }
        if (list.size() > 1 && Objects.equals(list.get(0).get(2), list.get(1).get(2))) {
            res = String.valueOf(list.getFirst().get(2));
        }
        return res;
    }
    
    static void main() {
        int m = 4;
        int n = 4;
        char[][] input = {{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'O', 'X', 'X'}};
        // 3 1 5
        System.out.println(solve(m, n, input));
        
        char[][] input1 = {{'O', 'O'}, {'X', 'X'}};
        // NULL
        System.out.println(solve(2, 2, input1));
        
        char[][] matrix = {{'X', 'X', 'X', 'O'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'X', 'O', 'X'}};
        // 3 2 4
        System.out.println(solve(4, 4, matrix));
    }
}
