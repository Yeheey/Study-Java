package com.huawei;

public class c22 {
    /*
    【22.查找单入口空闲区域】--【DFS】
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
    
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    static boolean[][] visited;// 标记访问过的'O'
    static int row;
    static int col;
    // 维护总体
    static int count = 0; // 合法区域数量
    static int maxArea = 0;// 最大区域面积
    static int maxCount = 0; // 最大区域面积的个数
    // 记录当前入口坐标
    static int eRow;
    static int eCol;
    static int eCount = 0; // 记录这个区域的入口数量
    static int curArea = 0; // 当前区域面积
    
    static void dfs(int r, int c, char[][] input) {
        // 递归出口:索引越界 or 已经访问过了
        if (r < 0 || r >= row || c < 0 || c >= col || visited[r][c] || input[r][c] == 'X') {
            return;
        }
        
        if (input[r][c] == 'O') {
            // 标记已访问
            visited[r][c] = true;
            // 记录当前面积
            curArea++;
            // 记录入口数量 顺便更新入口值
            if (r == 0 || r == row - 1 || c == 0 || c == col - 1) {
                eCount++;
                eRow = r;
                eCol = c;
            }
        }
        
        // 四连通 上下左右 继续深度搜索
        for (final int[] dir : directions) {
            int newI = r + dir[0];
            int newJ = c + dir[1];
            dfs(newI, newJ, input);
        }
    }
    
    static String solve(int m, int n, char[][] input) {
        // 1.初始化
        row = m;
        col = n;
        visited = new boolean[row][col];
        maxArea = 0;
        maxCount = 0;
        count = 0;
        String res;
        
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                // 只有是O且没被访问过 才进行深度搜索
                if (input[r][c] == 'O' && !visited[r][c]) {
                    // 在本次递归之前 清理当前数据
                    curArea = 0;
                    eCount = 0;
                    eRow = -1;
                    eCol = -1;
                    // 递归
                    dfs(r, c, input);
                    // 递归结束 整理数据 合法单入口 count++
                    if (eRow != -1 && eCol != -1 && eCount == 1) {
                        count++;
                    }
                    
                    if (count > 0) {
                        if (curArea > maxArea) {
                            maxArea = curArea;
                            maxCount = 1;
                        } else if (curArea == maxArea) {
                            maxCount++;
                        }
                    }
                    
                }
            }
        }
        
        // 输出结果
        if (maxCount == 1 && eRow != -1 && eCol != -1) {
            res = eRow + " " + eCol + " " + maxArea;
        } else if (maxCount > 1) {
            res = String.valueOf(maxArea);
        } else {
            res = "NULL";
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
    }
}
