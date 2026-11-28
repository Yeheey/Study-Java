package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class c31 {
    /*
        【31.最佳信号覆盖问题】--【模拟计算】--40min
        题目描述
        模拟AP安装，将AP的位置投影到二维坐标系中，给出每个AP的WIFI信号强度，信号强度会随着距离的增加而减弱。
        给定： 第一行是2个整数N,D(N<=100,D<=100)，其中N表示AP数量，D表示AP能够的信号能够覆盖的最大距离。
        接下来的N行里，每行包含3个整数x,y,s，表示这个AP在坐标系的位置为(x,y)，x,y > 0，信号强度为s。
        所有坐标点是在X-Y坐标系内的整数坐标。
        为了简化计算，两个坐标之间的距离用切比雪夫距离表示（在二维空间内，两个点之间的切比雪夫距离为它们横坐标之差的绝对值与纵坐标之差的绝对值的最大值）。
        需要你计算WIFI信号最好的坐标。
        信号衰减计算方式：
        如果第i个AP能到达(x,y)，那么该AP在此处的信号为 ⌊s / (1 + d)⌋（向下取整），d表示这个AP跟此坐标的切比雪夫距离。
        一个坐标的信号强度是所有能到达该坐标的AP的信号强度之和。
        
        输入描述
        第一行是2个整数N,D(N<=100,D<=100)，其中N表示AP数量，D表示AP能够的信号能够覆盖的最大距离
        接下来N行，每行包含3个整数x,y,s，表示这个AP在坐标系的位置为(x,y)，x,y > 0，信号强度为s
        
        输出描述
        WIFI信号最好的坐标，如果存在多个信号一致的坐标，输出字典序最小的非负坐标 坐标(x1,y1)字典序比另一个坐标(x2,y2)小，需满足以下条件之一：
        要么 x1 < x2，
        要么 x1 == x2 且 y1 < y2
        */
    
    public static List<Integer> solve(int n, int d, int[][] inputs) {
        // key [x,y] 坐标 value 信号强度总和
        HashMap<List<Integer>, Integer> map = new HashMap<>();
        for (final int[] input : inputs) {
            List<Integer> point = new ArrayList<>();
            point.add(input[0]);
            point.add(input[1]);
            int signal = input[2];
            map.put(point, signal);
        }
        
        for (int i = 0; i < inputs.length; i++) {
            for (int j = 0; j < inputs.length; j++) {
                if (i == j) {
                    continue;
                }
                int x1Point = inputs[i][0];
                int y1Point = inputs[i][1];
                int x2Point = inputs[j][0];
                int y2Point = inputs[j][1];
                int distance = Math.max(Math.abs(x1Point - x2Point), Math.abs(y1Point - y2Point));
                int s = 0;
                if (distance <= d) {
                    // 如果可达 计算信号强度
                    s = inputs[i][2] / (1 + d);
                    // 累加到外层坐标上
                    List<Integer> point1 = new ArrayList<>();
                    point1.add(x1Point);
                    point1.add(y1Point);
                    map.merge(point1, s, Integer::sum);
                }
                
            }
        }
        
        // map.forEach((key, value) -> {
        //     System.out.println("key--" + key + "value--" + value);
        // });
        List<List<Integer>> list = new ArrayList<>();
        
        map.forEach((key, value) -> {
            List<Integer> tmp = new ArrayList<>();
            tmp.add(key.get(0));
            tmp.add(key.get(1));
            tmp.add(value);
            list.add(tmp);
        });
        
        list.sort((a, b) -> {
            if (a.get(2).equals(b.get(2))) {
                return a.get(0).equals(b.get(0)) ? a.get(1) - b.get(2) : a.get(0) - b.get(0);
            } else {
                return a.get(2) - b.get(2);
            }
        });
        
        List<Integer> res = new ArrayList<>();
        if (!list.isEmpty()) {
            res.add(list.getFirst().get(0));
            res.add(list.getFirst().get(1));
        }
        return res;
    }
    
    static void main() {
        int n = 3; // AP数量
        int d = 2; // AP能够的信号能够覆盖的最大距离
        int[][] inputs = {{1, 2, 3}, {2, 1, 3}, {3, 1, 3}}; // {x,y,s} x,y是坐标 s是信号强度
        // 1 2
        System.out.println(solve(n, d, inputs));
    }
}
