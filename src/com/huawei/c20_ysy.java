package com.huawei;

import java.util.ArrayDeque;
import java.util.Queue;

public class c20_ysy {
/*
    【20.机器人活动区域】--【BFS】--二刷28min
    题目描述
    现有一个机器人，可放置于 M × N 的网格中任意位置，每个网格包含一个非负整数编号，当相邻网格的数字编号差值的绝对值小于等于 1 时，机器人可以在网格间移动。
    问题： 求机器人可活动的最大范围对应的网格点数目。
    说明：网格左上角坐标为 (0,0) ,右下角坐标为(m−1,n−1)，机器人只能在相邻网格间上下左右移动
    
    输入描述
    第 1 行输入为 M 和 N
    
    M 表示网格的行数  1 ≤ M
    N 表示网格的列数  N ≤ 150
    之后 M 行表示网格数值，每行 N 个数值（数值大小用 k 表示），数值间用单个空格分隔，行首行尾无多余空格。
    M、 N、 k 均为整数  0 ≤ k ≤ 50
    
    输出描述
    输出1行，包含1个数字，表示最大活动区域的网格点数目，行首行尾无多余空格。
    */
    
    // 四连通方向
    public static int[][] directions = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    
    public static int solve(int[][] arr) {
        int row = arr.length; // 行
        int col = arr[0].length; // 列
        int max = 0; // 最大的活动区域
        boolean[][] visited = new boolean[row][col]; // 访问过都置为true
        
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                // 每一个点都当做起始点搜索
                int count = 0; // 计数以当前为起点 合法的区域个数
                Queue<int[]> q = new ArrayDeque<>();
                q.offer(new int[]{i, j});
                
                while (!q.isEmpty()) {
                    // 当前坐标 已经是合法的了
                    int[] cur = q.poll();
                    int x = cur[0];
                    int y = cur[1];
                    
                    count++;
                    visited[x][y] = true;
                    
                    for (final int[] direction : directions) {
                        int newI = x + direction[0];
                        int newJ = y + direction[1];
                        // 索引未越界 且 没有访问过 且 两个差值小于等于1
                        if (newI >= 0 && newI < row && newJ >= 0 && newJ < col && !visited[newI][newJ] && Math.abs(arr[x][y] - arr[newI][newJ]) <= 1) {
                            // 继续入队 计数 设置访问过了
                            q.offer(new int[]{newI, newJ});
                            visited[newI][newJ] = true;
                        }
                    }
                    if (count > max) {
                        max = count;
                    }
                }
            }
        }
        return max;
    }
    
    static void main() {
        // case11 期望输出：1
        int[][] arr11 = {{1, 3, 5}, {4, 1, 3}};
        System.out.println(solve(arr11));
        
        // case1 样例，期望输出：6
        int[][] arr1 = {{1, 2, 5, 2}, {2, 4, 4, 5}, {3, 5, 7, 1}, {4, 6, 2, 4}};
        System.out.println(solve(arr1));
        
        // case2 全连通，全部差<=1，期望输出：9
        int[][] arr2 = {{1, 2, 3}, {2, 3, 4}, {3, 4, 5}};
        System.out.println(solve(arr2));
        
        // case3 单个格子，期望输出：1
        int[][] arr3 = {{5}};
        System.out.println(solve(arr3));
        
        // case4 完全互不连通，每个格子独立，期望输出：1
        int[][] arr4 = {{1, 3, 5}, {7, 9, 11}};
        System.out.println(solve(arr4));
        
        // case5 一条横向连通，期望输出：3
        int[][] arr5 = {{2, 3, 4, 6, 7}};
        System.out.println(solve(arr5));
        
        // case6 两条区域，一大一小，期望输出：4
        int[][] arr6 = {{1, 1, 5, 5}, {1, 2, 5, 6}, {9, 9, 3, 3}};
        System.out.println(solve(arr6));
        
        // case7 对角线连通，期望输出：5
        int[][] arr7 = {{1, 5, 5}, {2, 2, 5}, {5, 3, 3}};
        System.out.println(solve(arr7));
        
        // case8 边界，2行2列，期望输出：4
        int[][] arr8 = {{0, 1}, {3, 2}};
        System.out.println(solve(arr8));
        
        // case9 阶梯状，期望输出：6
        int[][] arr9 = {{1, 1, 1, 8}, {2, 2, 8, 8}, {3, 8, 8, 8}};
        System.out.println(solve(arr9));
        
        // case10 相同值整片，期望输出：9
        int[][] arr10 = {{4, 4, 4}, {4, 4, 4}, {4, 4, 4}};
        System.out.println(solve(arr10));
    }
}
