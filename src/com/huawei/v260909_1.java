package com.huawei;

public class v260909_1 {
    /*
    【26.9.9-1】数列求值
    题目描述
    有一个连续的数列，它的前 7 个数为 1,2,3,4,5,6,7。从第 8 个数开始，每个数的值等于它所在位置前面最近连续 7 个数中，最大的两个数之和减去最小的两个数之和。
    例如：第 8 个数的值，是它前面的 7 个数（1,2,3,4,5,6,7）中，最大的两个数（6、7）之和减去最小的两个数（1、2）之和，即 6+7−1−2=10。
    现在给定一个位置 n，请返回该位置上的数值。
    
    请实现以下接口：
   
    int getResult(int n)
    n：数列中的位置，1≤n≤1000
    返回：数列第 n 个位置上的数值
    输入描述
    输入一个整数 n，表示数列的位置。
    
    输出描述
    输出一个整数，表示该位置上数字的值。
    */
    
    static int solve(int n) {
        int ans = 0;
        int[] list = new int[100_000_000];
        for (int i = 0; i < 100_000_000 - 1; i++) {
            if (i < 7) {
                list[i] = i + 1;
            } else {
                list[i] = list[i - 1] + list[i - 2] - list[i - 6] - list[i - 7];
            }
        }
        
        if (n == 1) {
            return 1;
        }
        ans = list[n - 1];
        return ans;
    }
    
    static void main() {
        int n = 10;
        System.out.print(solve(n)); // output: 15
    }
    
}
