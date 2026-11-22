package com.huawei;

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
    
    /**
     * n总种植数量
     * m未成活数量
     * died死亡树的编号
     * saveLimit最多补种数量
     */
    static int solve(int n, int m, int[] died, int saveLimit) {
        // 0的索引都作废 alive 下标是编号 value是存活情况 false死true活
        boolean[] alive = new boolean[n + 1];
        
        for (int i = 0; i < n + 1; i++) {
            alive[i] = true;
        }
        
        for (int idx : died) {
            alive[idx] = false;
        }
        
        int s = 1;
        // int e = 1;
        int curLen = 0; // 当前区间长度 e-s+1
        int maxLen = 0; // 维护最长的区间
        int deadNum = 0; // 计数s-e区间有几颗死树 不能超过limit
        
        // 滑动窗口的尾端一直++ 变的只有s
        for (int e = 1; e < n + 1; e++) {
            // 如果遇到死树 计数++
            if (!alive[e]) {
                deadNum++;
            }
            
            // 当死树数量超过limit 开启新的一段
            // 滑动窗口 s往后移动 直到区间内的死树小于limit
            while (deadNum > saveLimit) {
                if (!alive[s]) {
                    deadNum--;
                }
                s++;
            }
            
            curLen = e - s + 1;
            if (curLen > maxLen) {
                maxLen = curLen;
            }
        }
        
        return maxLen;
    }
    
    static void main() {
        System.out.println(solve(5, 2, new int[]{2, 4}, 1)); // 3
        System.out.println(solve(10, 3, new int[]{2, 4, 7}, 1));  // 6
        System.out.println(solve(5, 1, new int[]{2}, 1)); // 5
        System.out.println(solve(5, 0, new int[]{}, 0)); // 5
        System.out.println(solve(5, 1, new int[]{3}, 0)); // 2
    }
}
