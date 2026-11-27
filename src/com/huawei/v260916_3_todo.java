package com.huawei;

public class v260916_3_todo {
/*
    【26.9.16-3】园区基站节能部署规划--【二分法】
    题目描述
    网络规划团队正在为某一线型工业园区的网络建设制定方案。园区由一条笔直的道路贯穿，沿途分布着 n 个关键业务区域，其位置坐标由整数数组 positions 表示（数组成员严格递增）。
    为了保证无缝漫游体验，方案要求相邻两个基站之间必须有足够的重叠覆盖区域，重叠区域长度至少为 minOverlap 米（即：相邻两个基站覆盖区间的交集长度至少为 minOverlap）。
    团队手中有 m 个新型基站可用，为践行绿色节能理念，团队希望将基站的发射功率调整到最低（即覆盖半径最小）。
    现给定关键区域的位置数组 positions（-10^9≤positions[i]≤10^9）、必须满足的最小重叠区域 minOverlap（0≤minOverlap≤10^9）以及可用的基站数量 m（1≤m≤10^5）。
    请计算在满足所有关键区域被覆盖、且相邻基站重叠满足要求的条件下，基站所需的最小信号覆盖半径 k。k 是非负整数，所有基站使用相同半径。
    
    输入描述
    输入共三行：
    第一行是严格递增的关键区域坐标，坐标之间使用英文逗号分隔。
    第二行是最小重叠长度 minOverlap。
    第三行是最多可以使用的基站数量 m。
    
    输出描述
    输出一个整数，表示满足条件的最小覆盖半径 k。
    */
    public static int solve(int[] inputs,int minOverlap,int m) {
        return 0;
    }
    
    static void main() {
        int[] inputs = {0, 10, 20};
        int minOverlap = 0;
        int m = 2;
        System.out.println(solve(inputs,minOverlap,m));
    }
}
