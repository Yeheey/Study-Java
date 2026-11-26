package com.huawei;

public class c27_todo {
    /*
        题目描述
        小明有n个可选运动，每个运动有对应卡路里，想选出其中k个运动且卡路里和为t。
        k，t，n都是给定的。求出可行解数量
        
        输入描述
        第一行输入 n t k
        第一行输入 每个运动的卡路里 按照空格进行分割
        
        备注：0 < n < 10，t > 0, 0 < k <= n
        每个运动量的卡路里 > 0
        输出描述：求出可行解数量
    */
    
    static int count = 0; // 合法数量
    static int reNum = 0; // 剩余个数
    static int reCa = 0; // 剩余卡路里
    static int num = 0; // 目标个数
    
    public static int solve(int n, int t, int k, int[] ca) {
        if (ca == null || k <= 0 || k > n || t <= 0 || ca.length < n) {
            return 0;
        }
        for (int i = 0; i < ca.length; i++) {
        
        }
        
        return count;
    }
    
    static void main() {
        int n = 4; // 可选运动
        int t = 3; // 卡路里和
        int k = 2; // 目标选中
        int[] ca = {1, 1, 2, 3};
        System.out.println(solve(n, t, k, ca));
    }
}
