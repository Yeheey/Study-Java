package com.huawei;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test8 {
    /*题目描述
请开发一个快递驿站计费系统，根据给定的一组快递存取记录，系统计费每位业主要缴纳的总费用四，并按总费用从高到低输出。
系统计费规则如下:
1.免费存放时间为12小时，从存件时间开始计时，总存放时间≤12小时不收费，存放时间=取件时间-存件时间;
2.超出免费存放时间的，每12小时收取1元，不足12小时按12小时计算，比如存放了25 个小时，超出免费时间13小时，需要收费2元;
3.同一个房间号的业主，可能有多条存取记录，需要将业主所有快递的超时费用累加得到总费用。

输入描述
存取时间已简化处理采用整型数字，非24小时制格式;
一组快递存取记录列表，其中一条存取记录对应一个数组，该数组包含3个非负整数字段:存件时间、取件时间和业主房间号;
1.0≤存件时间≤取件时间≤100000;
2.100<房间号<10000;
3.输入记录条数的范围:1≤N≤1000

输出描述
一个二维数组口/例表，表述一组收费记录，每一条收费记录对应一个数组，该数组包含2个整数字段:业主房间号和总费用;
收费按总费用从高到低排序，总费用相同的，按房间从小到大排序;
*/
    static int cacl(int startTime, int endTime) {
        int total = 0;  // 计算金额
        if (endTime - startTime <= 12) {
            return 0;
        } else {
            total = (endTime - startTime - 12) / 12 + 1;
        }
        return total;
    }
    
    static int findIndex(List<List<Integer>> res, int target) {
        for (int i = 0; i < res.size(); i++) {
            if (res.get(i).get(0).equals(target)) {
                return i;
            }
        }
        return -1;
    }
    
    static List<List<Integer>> solve(List<List<Integer>> matrix) {
        List<List<Integer>> res = new ArrayList<>();
        // 先按照房间从小到大排序
        Collections.sort(matrix, (a, b) -> a.get(2) - b.get(2));
        
        
        if (matrix.size() == 1) {
            return matrix;
        } else {
            // 遍历数组
            int i = 0;
            while (i < matrix.size()) {
                int room = matrix.get(i).get(2); // 房间号
                int curRoomSum = 0;  // 房间金额
                int idx = findIndex(res, room);
                
                if (idx > -1) {
                    curRoomSum = res.get(idx).get(1);
                    // 如果当前遍历房间和前一个房间一样
                    curRoomSum += cacl(matrix.get(i).get(0), matrix.get(i).get(1));
                    // 房间一样的下标 更新金额
                    res.set(idx, List.of(room, curRoomSum));
                } else {
                    res.add(List.of(room, cacl(matrix.get(i).get(0), matrix.get(i).get(1))));
                }
                i++;
            }
        }
        
        // 排序输出
        res.sort((a, b) -> {
            if (!a.get(1).equals(b.get(1))) {
                return b.get(1) - a.get(1); // 下标1 从大到小 费用
            }
            return a.get(0) - b.get(0);     // 下标0 从小到大 房间号
        });
        return res;
    }
    
    public static void main(String[] args) {
        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(new ArrayList<>(List.of(100, 110, 1111)));
        matrix.add(new ArrayList<>(List.of(200, 210, 2222)));
        matrix.add(new ArrayList<>(List.of(150, 165, 3333)));
        matrix.add(new ArrayList<>(List.of(180, 190, 1111)));
        
        System.out.print(solve(matrix));
    }
    
}
