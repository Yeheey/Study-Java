package com.huawei;

import java.util.Arrays;

public class c1 {
    /*
        【螺旋数字矩阵】--【模拟计算+二维矩阵】--40min--完全自己AC--注意向左和向上的边界要判断一下
        题目描述
        疫情期间，小明隔离在家，百无聊赖，在纸上写数字玩。他发明了一种写法： 给出数字个数n和行数m（0 < n ≤ 999，0 < m ≤ 999），
        从左上角的1开始，按照顺时针螺旋向内写方式，依次写出2,3...n，最终形成一个m行矩阵。 小明对这个矩阵有些要求：
        1.每行数字的个数一样多
        2.列的数量尽可能少
        3.填充数字时优先填充外部
        4.数字不够时，使用单个*号占位
        输入描述：输入一行，两个整数，空格隔开，依次表示n、m
        
        输出描述：符合要求的唯一矩阵
    */
    static String[][] solve(int n, int row) {
        // 列数
        int col = n % row == 0 ? (n / row) : (n / row + 1);
        String[][] res = new String[row][col];
        System.out.println(col);
        
        // 设置边界
        int top = 0;
        int bottom = row - 1;
        int left = 0;
        int right = col - 1;
        
        int curNum = 1;
        // 右→下→左→上 缩边界
        while (top <= bottom && left <= right) {
            // 向右 缩top
            for (int j = left; j <= right; j++) {
                res[top][j] = curNum > n ? "*" : String.valueOf(curNum);
                curNum++;
            }
            top++;
            
            // 向下 缩right
            for (int i = top; i <= bottom; i++) {
                res[i][right] = curNum > n ? "*" : String.valueOf(curNum);
                curNum++;
            }
            right--;
            
            // 向左 缩bottom
            if (left < right) {
                for (int j = right; j >= left; j--) {
                    res[bottom][j] = curNum > n ? "*" : String.valueOf(curNum);
                    curNum++;
                }
                bottom--;
            }
            
            // 向上 缩left
            if (top < bottom) {
                for (int i = bottom; i >= top; i--) {
                    res[i][left] = curNum > n ? "*" : String.valueOf(curNum);
                    curNum++;
                }
                left++;
            }
            
        }
        
        return res;
    }
    
    static void main() {
        int n = 9;
        int m = 4;
        // 1 2 3
        // * * 4
        // 9 * 5
        // 8 7 6
        System.out.println(Arrays.deepToString(solve(n, m)));
        System.out.println(Arrays.deepToString(solve(8, 4)));
    }
}
