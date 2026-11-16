package com.huawei;

import java.util.*;

public class v260830_2_ysy {
    /*
        【26.8.30-2】快递驿站计费系统--【模拟计算+数组排序】--30min
        题目描述：
        请开发一个快递驿站计费系统，根据给定的一组快递存取记录，系统计费每位业主要缴纳的总费用四，并按总费用从高到低输出。
        系统计费规则如下:
        1.免费存放时间为12小时，
        从存件时间开始计时，总存放时间≤12小时不收费，存放时间=取件时间-存件时间;
        2.超出免费存放时间的，每12小时收取1元，不足12小时按12小时计算，比如存放了25个小时，超出免费时间13小时，需要收费2元;
        3.同一个房间号的业主，可能有多条存取记录，需要将业主所有快递的超时费用累加得到总费用。
        
        输入描述：
        存取时间已简化处理采用整型数字，非24小时制格式;
        一组快递存取记录列表，其中一条存取记录对应一个数组，该数组包含3个非负整数字段:存件时间、取件时间和业主房间号;
        1.0≤存件时间≤取件时间≤100000;
        2.100<房间号<10000;
        3.输入记录条数的范围:1≤N≤1000
        
        输出描述：
        一个二维数组口/例表，表述一组收费记录，每一条收费记录对应一个数组，该数组包含2个整数字段:业主房间号和总费用;
        收费按总费用从高到低排序，总费用相同的，按房间从小到大排序;
    */
    static int calculate(int start, int end) {
        int total;
        int a = end - start;
        if (a <= 12) {
            total = 0;
        } else {
            int b = a - 12;
            total = b % 12 == 0 ? b / 12 : (b / 12) + 1;
        }
        return total;
    }
    
    static List<List<Integer>> solve(int[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        int n = matrix.length;
        // key 房间号 value 总费用
        HashMap<Integer, Integer> map = new HashMap<>();
        
        for (final int[] ints : matrix) {
            int start = ints[0];
            int end = ints[1];
            int room = ints[2];
            
            int sum = calculate(start, end);
            
            // ✅️ getOrDefault 不存在返回默认值
            int curSum = map.getOrDefault(room, 0);
            map.put(room, curSum + sum);
            // ⭐merge版本（一行） 优化写
            // map.merge(room, sum, Integer::sum);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            
            List<Integer> roomSum = new ArrayList<>();
            roomSum.add(key);
            roomSum.add(value);
            
            res.add(roomSum);
        }
        
        
        // 排序：按金额从大到小 相同金额按房间从小到大
        Collections.sort(res, (a, b) -> {
            if (!Objects.equals(a.get(1), b.get(1))) {
                return b.get(1) - a.get(1);
            } else {
                return a.get(0) - b.get(0);
            }
            
        });
        
        return res;
    }
    
    public static void main(String[] args) {
        int[][] matrix = {{100, 110, 1111}, {200, 210, 2222}, {150, 165, 3333}, {180, 190, 1111}, {190, 210, 1111}};
        
        System.out.print(solve(matrix));
    }
    
}
