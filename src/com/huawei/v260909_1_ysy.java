package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260909_1_ysy {
/*
    【26.9.9-1】数列求值--【模拟计算+滑动窗口】--18min
    题目描述
    有一个连续的数列，它的前 7 个数为 1,2,3,4,5,6,7。从第 8 个数开始，每个数的值等于它所在位置前面最近连续 7 个数中，最大的两个数之和减去最小的两个数之和。
    例如：第 8 个数的值，是它前面的 7 个数（1,2,3,4,5,6,7）中，最大的两个数（6、7）之和减去最小的两个数（1、2）之和，即 6+7−1−2=10。
    现在给定一个位置 n，请返回该位置上的数值。

    请实现以下接口：int getResult(int n)
    n：数列中的位置，1≤n≤1000
    返回：数列第 n 个位置上的数值

    输入描述:输入一个整数 n，表示数列的位置。
    输出描述:输出一个整数，表示该位置上数字的值。
*/
    
    // 想复杂了 不用滑动窗口也可以
    static int getResult(int n) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }
        int eight = list.get(6) + list.get(5) - list.get(0) - list.get(1);
        list.add(eight);
        
        for (int i = 8; i < 1001; i++) {
            int sum = list.get(i - 1) - list.get(i - 3) + list.get(i - 1) + list.get(i - 8) - list.get(i - 6);
            list.add(sum);
        }
        
        return list.get(n - 1);
    }
    
    static void main() {
        System.out.println(getResult(1));  // 1
        System.out.println(getResult(2));  // 2
        System.out.println(getResult(3));  // 3
        System.out.println(getResult(4));  // 4
        System.out.println(getResult(5));  // 5
        System.out.println(getResult(6));  // 6
        System.out.println(getResult(7));  // 7
        System.out.println(getResult(8));  // 10
        System.out.println(getResult(9));  // 12
        System.out.println(getResult(10)); // 15
        System.out.println(getResult(11)); // 18
        System.out.println(getResult(12)); // 22
        System.out.println(getResult(13)); // 27
        System.out.println(getResult(14)); // 32
        System.out.println(getResult(15)); // 37
    }
    
}
