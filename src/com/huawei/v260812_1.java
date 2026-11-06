package com.huawei;

import java.util.Arrays;

public class v260812_1 {
    /*
    22.【26.8.12-1】LLM推理批次最大化
    题目描述
    大语言模型推理时，显存中有一个 KV Cache，用于存储各请求的键值对。
    现有 N 个推理请求排队，第 i 个请求需要占用 KV Cache 中一段连续位置 [Li,Ri]。每个位置同一时间只能分配给一个请求。
    请选出尽可能多的请求，使它们的区间互不重叠，从而最大化批次的吞吐量。
    
    输入描述
    requests：由 N 个推理请求构成的二维数组，requests[i] 表示第 i 个推理请求，其内容为 [Li,Ri]。
    数组长度 N 满足 1 <= N <= 10^5。
    每个位置 [Li,Ri] 满足 0 <= Li <= Ri <= 10^9。
    输入为一行 JSON-like 二维数组，例如：[[1,3],[2,5],[4,7]]
    
    输出描述
    输出一个整数，表示最多可选多少个不重叠区间的请求。
    
    注意：若两个区间端点相接，例如 [1,3] 和 [3,5]，由于位置 3 被共同占用，仍然认为重叠。
    */
    
    static int solve(int[][] arr) {
        int n = arr.length;
        if (n == 0) return 0;
        // 按右端点升序（闭区间、端点相接算重叠）
        Arrays.sort(arr, (a, b) -> Integer.compare(a[1], b[1]));
        int res = 1;                 // 第一个区间必选
        int lastRight = arr[0][1];   // 记录上一个选中区间的右端点
        for (int i = 1; i < n; i++) {
            if (arr[i][0] > lastRight) {   // 严格大于：相接也算重叠
                res++;
                lastRight = arr[i][1];
            }
        }
        
        return res;
    }
    
    static void main() {
        int[][] modes = {{1, 3}, {2, 5}, {4, 7}, {6, 9}, {8, 10}, {11, 12}};
        int ans = solve(modes);
        System.out.print(ans); // 4
    }
}
