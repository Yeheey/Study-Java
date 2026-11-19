package com.huawei;

public class v261104_1 {
    /*
        【汽车出行路线重复检测】--【滑动窗口】--20min--需要自己再写一遍break continue 逻辑混乱
        题目描述
        汽车智能导航系统按行驶顺序记录车辆经过的地点编号，得到数组locations。请统计其中的闭环路线条数。
        一条闭环路线是起止下标分别为i和j的连续子路线，满足0≤i<j<n，且两端都计入路线。它同时满足以下条件。
            1.起点与终点的地点编号相同。
            2.中间的地点编号两两不同。
            3.中间各地点的编号均不同于起点编号。
        中间段可以为空，因此两个相邻的相同地点也构成闭环。按起止下标计数，地点编号序列相同但下标区间不同的闭环分别计入结果。不同闭环可以重叠。
        
        输入描述
        输入一行，内容为方括号包围的非负整数数组，元素用英文逗号分隔。括号和逗号旁允许有额外空白。空数组写作0。
        数组长度n满足0≤n≤10。每个地点编号满足0≤locations[i]≤10。
        输出描述
        输出一行一个十进制整数，表示闭环路线的条数。没有闭环时输出0。
        */
    static boolean isValid(int s, int e, int[] input) {
        // 自闭环 合法
        if (e - s == 1) {
            return true;
        }
        
        boolean res = true;
        int start = input[s];
        // 地点编号满足0≤locations[i]≤10 下标是编号 访问过变为true
        boolean[] locations = new boolean[11];
        // 遍历中间的值 要求每个编号都不一样
        for (int i = s + 1; i < e ; i++) {
            int cur = input[i];
            // 如果访问过 说明中间有编号重复 当前编号和起始编号一样  都不合法
            if (locations[cur] || cur == start) {
                res = false;
                break;
            }
            locations[cur] = true;
        }
        
        return res;
    }
    
    static int solve(int[] input) {
        int n = input.length;
        if (n == 0 || n == 1) {
            return 0;
        }
        
        // n>=2时
        int res = 0;
        
        // 每一个都可以当做起点去检查
        for (int s = 0; s < n; s++) {
            int e = s + 1;
            
            while (e < n) {
                int start = input[s];
                int end = input[e];
                // 始末不一致 继续找终点
                if (start != end) {
                    e++;
                    continue;
                }
                // 始末一致 检查是否是合法闭环
                if (input[s] == input[e] && isValid(s, e, input)) {
                    res++;
                }
                break;
            }
        }
        
        return res;
    }
    
    static void main() {
        int[] input1 = {1, 2, 1, 3, 4};
        // 1
        System.out.println(solve(input1));
        
        int[] input2 = {2, 5, 3, 2, 4, 6, 4};
        // 2
        System.out.println(solve(input2));

        int[] input3 = {1, 1, 1};
        // 2
        System.out.println(solve(input3));

        int[] input4 = {1, 2, 3, 4};
        // 0
        System.out.println(solve(input4));

        int[] input5 = {1, 2, 2, 1};
        // 1
        System.out.println(solve(input5));

        int[] input6 = {1, 2, 1, 2, 1};
        // 3
        System.out.println(solve(input6));
    }
}
