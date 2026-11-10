package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260916_2 {
    /*
        【26.9.16-2】矩阵螺旋遍历
        题目描述
        给定一个M行N列的矩阵，矩阵中每个元素是一个非负整数，从左上角(0,0)开始，按顺时针螺旋顺序遍历矩阵，对于遍历的每个数字，计算器二进制表示中1的个数，
        如果1的个数是3的倍数，则记录该数字的坐标。请按照顺序输出满足条件的数字的坐标列表。
        补充说明
        螺旋遍历按照从外到内的顺序进行：从 (0,0) 出发，先向右走，遇到边界或已访问过的元素后转向，按右 → 下 → 左 → 上循环转向。
        输入描述
        参数 matrix 为一个 M 行 N 列的二维整数数组 (1≤M,N≤10)，每个元素 matrix[i][j] (0≤matrix[i][j]≤10^9) 为非负整数。

        输出描述
        返回一个二维整数数组，按遍历顺序依次记录满足条件的坐标，每个坐标写作 [行索引,列索引]。
        如果没有满足条件的数字，返回空数组。数字 0 的二进制中 1 的个数为 0，按满足条件统计。
    */
    
    static List<int[]> solve(int[][] matrix) {
        List<int[]> res = new ArrayList<>();
        
        int n = matrix.length;
        int m = matrix[0].length;
        
        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = m - 1;
        
        
        while (top <= bottom && left <= right) {
            // 1. 从左到右，遍历上边界
            for (int j = left; j <= right; j++) {
                int curVal = matrix[top][j];
                if (Integer.bitCount(curVal) % 3 == 0) {
                    int[] temp = new int[2];
                    temp[0] = top;
                    temp[1] = j;
                    res.add(temp);
                }
            }
            top++;  // 上边界下移
            
            // 2. 从上到下，遍历右边界
            for (int i = top; i <= bottom; i++) {
                int curVal = matrix[i][right];
                if (Integer.bitCount(curVal) % 3 == 0) {
                    int[] temp = new int[2];
                    temp[0] = i;
                    temp[1] = right;
                    res.add(temp);
                }
            }
            right--;  // 右边界左移
            
            // 3. 从右到左，遍历下边界（需判断 top <= bottom，防止重复）
            if (top <= bottom) {
                for (int j = right; j >= left; j--) {
                    int curVal = matrix[bottom][j];
                    if (Integer.bitCount(curVal) % 3 == 0) {
                        int[] temp = new int[2];
                        temp[0] = bottom;
                        temp[1] = j;
                        res.add(temp);
                    }
                }
                bottom--;
            }
            
            // 4. 从下到上，遍历左边界（需判断 left <= right，防止重复）
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    int curVal = matrix[i][left];
                    if (Integer.bitCount(curVal) % 3 == 0) {
                        int[] temp = new int[2];
                        temp[0] = i;
                        temp[1] = left;
                        res.add(temp);
                    }
                }
                left++;
            }
        }
        
        return res;
    }
    
    static void main() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        // [[2,2],[1,2]]
        List<int[]> ans = solve(matrix);
        for (int[] p : ans) {
            System.out.print("[" + p[0] + "," + p[1] + "] ");
        }
        
    }
}
