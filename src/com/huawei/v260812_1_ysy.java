package com.huawei;

import java.util.Arrays;

public class v260812_1_ysy {
    /*
    【26.8.12-1】LLM推理批次最大化--【贪心】--13min
    【❌️想不到右端点排序】
    题目描述
    大语言模型推理时，显存中有一个 KV Cache，用于存储各请求的键值对。
    现有 N 个推理请求排队，第 i 个请求需要占用 KV Cache 中一段连续位置 [Li,Ri]。每个位置同一时间只能分配给一个请求。
    请选出尽可能多的请求，使它们的区间互不重叠，从而最大化批次的吞吐量。
    
    输入描述
    modes：由 N 个推理请求构成的二维数组，modes[i] 表示第 i 个推理请求，其内容为 [Li,Ri]。
    数组长度 N 满足 1 <= N <= 10^5。
    每个位置 [Li,Ri] 满足 0 <= Li <= Ri <= 10^9。
    输入为一行 JSON-like 二维数组，例如：[[1,3],[2,5],[4,7]]
    
    输出描述
    输出一个整数，表示最多可选多少个不重叠区间的请求。
    
    注意：若两个区间端点相接，例如 [1,3] 和 [3,5]，由于位置 3 被共同占用，仍然认为重叠。
    */
    
    static int solve(int[][] modes) {
        int len = modes.length; // 长度1 <= len <= 10^5
        if (len == 1) {
            return 1;
        }
        
        // 右端点升序
        Arrays.sort(modes, (a, b) -> a[1] - b[1]);
        
        // 先选第一个 右端点最小 后面可选的空间越大
        int res = 1;
        // 初始化前一个右端点 是第一个区间的右端点 当不重叠时 更新
        int lastRight = modes[0][1];
        
        for (int i = 1; i < len; i++) {
            int curLeft = modes[i][0];
            int curRight = modes[i][1];
            
            if (curLeft > lastRight) {
                lastRight = curRight;
                res += 1;
            }
        }
        
        return res;
    }
    
    static void main() {
        int[][] modes = {{1, 3}, {2, 5}, {4, 7}, {6, 9}, {8, 10}, {11, 12}};
        // 4
        System.out.print(solve(modes));
    }
}
