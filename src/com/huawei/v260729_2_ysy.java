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
    
    static List<Integer> solve(int[][] matrix, int interval) {
        List<Integer> res = new ArrayList<>();
        
        return res;
    }
    
    static void main() {
        int[][] matrix = {{1, 10}, {6, 12}, {3, 5}, {10, 7}, {5, 8}};
        int interval = 4;
        // [10,12,10,12,10]
        System.out.print(solve(matrix, interval));
    }
}



