package com.itheima.test;

import java.util.Scanner;

public class VariableDemo1 {
    static void main() {
// 9*9乘法表
        for (int i = 1; i <= 9; i++) {
            for (int j = 1; j <= i; j++) {
                int res = 0;
                res = i*j;
                System.out.print(j+ "×" + i + "=" + res);
                System.out.print("  ");
            }
            System.out.println();
        }
    }
}
