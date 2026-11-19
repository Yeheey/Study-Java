package com.huawei;

import java.util.Arrays;

public class c5 {
    /*
            【补种未成活胡杨】
            题目描述
            近些年来，我国防沙治沙取得显著成果。某沙漠新种植N棵胡杨（编号1-N），排成一排。
            一个月后，有M棵胡杨未能成活。
            现可补种胡杨K棵，请问如何补种（只能补种，不能新种），可以得到最多的连续胡杨树？
            
            输入描述
            N 总种植数量，1 <= N <= 100000
            M 未成活胡杨数量，M 个空格分隔的数，按编号从小到大排列，1 <= M <= N
            K 最多可以补种的数量，0 <= K <= M
            
            输出描述
            最多的连续胡杨棵树
    */
    static int solve(int n, int m, int[] died, int saveLimit) {
        // 0的索引都作废
        boolean[] alive = new boolean[n + 1];
        
        for (int i = 0; i < n + 1; i++) {
            alive[i] = true;
        }
        
        for (int idx : died) {
            alive[idx] = false;
        }
        
        int s = 1;
        int e = 1;
        int maxLen = 0;
        int deadNum = 0; // 不能超过limit
        
        while (e < n + 1) {
            
            if (!alive[e]) {
                deadNum += 1;
            }
            e++;
            
            // 超过limit 开启新的一段
            if (deadNum > saveLimit) {
                int curLen = e - 1 - s;
                if (curLen > maxLen) {
                    maxLen = curLen;
                    s++;
                    e = s;
                }
            }
        }
        
        return maxLen;
    }
    
    static void main() {
        int n = 5;
        int m = 2;
        int[] died = {2, 4};
        int saveLimit = 1;
        // 3
        System.out.println(solve(n, m, died, saveLimit));
        
        int n1 = 10;
        int m1 = 3;
        int[] died1 = {2, 4, 7};
        int saveLimit1 = 1;
        // 6
        System.out.println(solve(n1, m1, died1, saveLimit1));
    }
}
