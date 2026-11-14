package com.huawei;

public class v260823_1_ysy {
    /*
    【26.8.23-1】直线冲刺--【模拟计算】-6min
    题目描述:
    在一条长度为n的直线跑道上，每个格子的坐标从0到n-1，每个格子上写着一个整数，存放在数组tiles中。这些数字有特殊含义:
    tiles[i]=0，代表这是普通空地，可以通过;
    tiles[i]=1，代表这是弹簧加速垫;
    tiles[i]=-1，代表这是陷阱。
    你控制的角色从坐标0出发，目标是成功到达或越过终点坐标n-1(即坐标>=n-1即算通关)。
    角色移动规则:默认情况下，角色每一步只能向右移动1格。如果角色踩到弹簧加速垫(1)，那么下一步可以向右移动2格。如果角色踩到陷阱(-1)，游戏结束。
    
    输入描述:
    给定一个整数数组tiles，长度为n，其中1<=n<=10000。数组中的元素取值为0、1或-1。
    输入为一行，数组元素使用英文逗号分隔，例如:1 0,1,-1,0,0
    输出描述:
    如果角色从坐标0出发能够成功通关(到达或越过坐标n-1)，返回通关所需的步数;否则返回 -1。
    */
    
    static int solve(int[] tiles) {
        int steps = 0;
        int cur = 0;
        int n = tiles.length;
        // 不知道循环几次 也不知道什么时候结束 那就先做循环 在循环体内判断结束时间
        while (true) {
            // 通关返回步数
            if (cur >= n - 1) {
                return steps;
            }
            // 踩到陷阱
            if (tiles[cur] == -1) {
                return -1;
            }
            if (tiles[cur] == 0) {
                steps += 1;
                cur += 1;
            }
            if (tiles[cur] == 1) {
                steps += 1;
                cur += 2;
            }
        }
    }
    
    
    public static void main(String[] args) {
        int[] tiles = {1, 0, 1, -1, 0, 0};
        // 3
        System.out.print(solve(tiles));
    }
}
