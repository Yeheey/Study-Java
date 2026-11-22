package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260729_2_ysy {
    /*
    27.【26.7.29-2】基于上报数据的实时最大值
    题目描述
    在工业物联网监控系统中，由于网络抖动、边缘节点缓存重发等原因，传感器上报数据往往是乱序到达；
    系统接收到一段数据流数据，流中的每个数据包包含一个发生时刻（单位秒）的时间戳和一个测量值；
    对于流中到达的每一个数据点，系统需要以该数据点的时间戳为基准，回溯过去一段时间内（包含当前时间戳），数据点的最大测量值是多少；
    请设计一段程序，按数据流 data 中各点的到达顺序，依次输出每个点对应回溯区间中的最大测量值。
    
    输入描述
    数据流 data：二维数组格式，每个数组元素包括两个参数，发生时刻和该时刻的测量值；如 [1,10] 表示时刻 1 的测量值为 10；
    回溯时间 interval：整型格式，如当前时刻为 1，回溯时间为 5，那么表示回溯时间起点为 −4，回溯区间为 [−4,1]。
    
    输出描述
    每个时刻对应回溯区间的最大测量值组成的数组。
    
    约束条件
    1≤interval≤10^9
    1≤data.length≤10^3
    −10^9≤value≤10^9
    */
    
    /**
     * data数据流 长度1-1000 值-10^9-10^9
     * interval回溯时间 值1-10^9
     */
    static List<Integer> solve(int[][] data, int interval) {
        List<Integer> res = new ArrayList<>();
        int len = data.length;
        
        res.add(data[0][1]); // 第一个一定加进去
        if (len == 1) {
            return res;
        }
        // 长度大于1
        for (int i = 1; i < len; i++) {
            // 本次[curTimeStart,curTime] 值curValue
            int curTime = data[i][0];
            int curTimeStart = curTime - interval;
            int curValue = data[i][1];
            
            // 回溯之前的时间点
            for (int j = 0; j < i; j++) {
                int preTime = data[j][0];
                int preValue = data[j][1];
                
                // 旧的结束时间落在本次时间段内 && 旧值>新值 维护更新新值
                if (curTimeStart <= preTime && preTime <= curTime && curValue < preValue) {
                    curValue = preValue;
                }
            }
            
            res.add(curValue);
        }
        
        return res;
    }
    
    static void main() {
        // 回溯区间是 [当前时刻 - interval, 当前时刻]，只看已经到达的点
        
        int[][] matrix = {{1, 10}, {6, 12}, {3, 5}, {10, 7}, {5, 8}};
        int interval = 4;
        // [10, 12, 10, 12, 10]
        System.out.println(solve(matrix, interval));
        
        int[][] one = {{5, -3}};
        // [-3]
        System.out.println(solve(one, 1));
        
        int[][] onEdge = {{1, 4}, {4, 8}};
        // 时刻 1 落在 [4-3, 4] 上，计入。[4, 8]
        System.out.println(solve(onEdge, 3));
        
        int[][] justOut = {{1, 4}, {5, 1}};
        // 时刻 1 比 5-3 早 1 秒，不计入。[4, 1]
        System.out.println(solve(justOut, 3));
        
        int[][] negative = {{1, -5}, {2, -1}, {3, -8}};
        // [-5, -1, -1]
        System.out.println(solve(negative, 10));
        
        int[][] future = {{8, 3}, {1, 20}, {9, 4}};
        // 处理时刻 1 时，时刻 8 还在未来。[3, 20, 20]
        System.out.println(solve(future, 10));
        
        int[][] expire = {{1, 100}, {2, 1}, {4, 3}, {5, 2}};
        // 到时刻 4，100 已经滑出 [2, 4]。[100, 100, 3, 3]
        System.out.println(solve(expire, 2));
        
        int[][] sameTime = {{3, 1}, {3, 9}, {3, 4}};
        // [1, 9, 9]
        System.out.println(solve(sameTime, 5));
        
        int[][] bigInterval = {{5, 1}, {1, 50}, {4, 7}};
        // interval 很大，未来的时刻 5 仍然不能算进时刻 1 和 4。[1, 50, 50]
        System.out.println(solve(bigInterval, 1_000_000_000));
        
        int[][] mixed = {{10, 6}, {1, 100}, {12, 3}, {11, 9}};
        // [6, 100, 6, 9]
        System.out.println(solve(mixed, 4));
    }
}



