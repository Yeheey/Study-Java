package com.huawei;

public class c23 {
    /*
        【23.小华地图寻宝】--【DFS】--40min
        题目描述
        小华按照地图去寻宝，地图上被划分成 m 行和 n 列的方格，横纵坐标范围分别是 [0, n-1] 和 [0, m-1]。
        在横坐标和纵坐标的数位之和不大于 k 的方格中存在黄金（每个方格中仅存在一克黄金），但横坐标和纵坐标数位之和大于 k 的方格存在危险不可进入。
        小华从入口 (0,0) 进入，任何时候只能向左，右，上，下四个方向移动一格。请问小华最多能获得多少克黄金？
        
        输入描述
        坐标取值范围如下：0 ≤ m ≤ 50   0 ≤ n ≤ 50
        k的取值范围如下：0 ≤ k ≤ 100
        输入中包含3个字数，分别是m, n, k
        
        输出描述
        输出小华最多能获得多少克黄金
        */
    
    static boolean[][] visited;
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static int count;
    
    // 辅助函数 统计数位之和 最多两位数
    static int sum(int i, int j) {
        int i1 = i / 10;
        int i2 = i % 10;
        
        int j1 = j / 10;
        int j2 = j % 10;
        
        return i1 + i2 + j1 + j2;
    }
    
    static void dfs(int r, int c, int m, int n, int k) {
        if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c] || sum(r, c) > k) {
            return;
        }
        
        visited[r][c] = true;
        
        if (sum(r, c) <= k) {
            count++;
        }
        
        // 四连通
        for (final int[] dir : directions) {
            int newI = r + dir[0];
            int newJ = c + dir[1];
            if (sum(newI, newJ) <= k) {
                dfs(newI, newJ, m, n, k);
            }
        }
    }
    
    static int solve(int m, int n, int k) {
        visited = new boolean[m][n];
        count = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (sum(i, j) <= k) {
                    dfs(i, j, m, n, k);
                }
            }
        }
        
        return count;
    }
    
    
    static void main() {
        System.out.println(solve(40, 40, 18)); // 1484
        System.out.println(solve(5, 4, 7)); // 20
        System.out.println(solve(5, 4, 4)); // 14
        System.out.println(solve(4, 4, 3)); // 10
    }
}
