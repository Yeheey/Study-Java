package com.huawei;

import java.util.Arrays;

public class v260729_3_ysy {
    /*
     【26.7.29-3】路口等待时间--【模拟计算】--60min
     【到达时间 并不是实际的到达时间 有可能还要等上一辆车通过 再等红绿灯 再+1 去更新离开时间】
     题目描述
     十字路口的红绿灯分为东西向（E/W）和南北向（S/N）两组，两组状态始终相反。东西向红灯亮 R 秒，然后绿灯亮 G 秒，不断循环；
     南北向则相反——绿灯亮 R 秒，然后红灯亮 G 秒。刚开始时（0秒）东西方向是红灯，南北方向是绿灯。
     红绿灯切换无过渡期，红灯结束时绿灯立即开始，无需额外等待。
     车辆到达路口时，遇到绿灯直接走，通过路口需要 1 秒，如遇到红灯停下来等。E/W/S/N 四个方向各有一条独立车道，各自排队，互不干扰，而同向后车必须等前车走完才能走。
     求从第一辆车到达路口，到最后一辆车完全离开，共同花费多少秒，和最后一辆离开的时间是第几秒。

     输入描述
     输入共 4 行：
          第一行输入整数 R，表示东西向红灯持续秒数。
          第二行输入整数 G，表示东西向绿灯持续秒数。
          第三行输入车辆方向列表，使用大写字母 E/W/S/N 表示，方向之间用英文逗号分隔。
          第四行输入车辆到达时间列表，整数之间用英文逗号分隔。
          
     输出描述
     输出数组 [总耗时, 最后一辆车离开时间]。
     
     补充说明
     保证车辆的来向数量和到达时刻数量相等，且到达时刻按非递减顺序排列。
     取值范围：1≤R,G≤60 到达时刻 ≤100 车辆数量 ≤100
     */
    
    static int directionToIndex(String dir) {
        return switch (dir) {
            case ("E") -> 0;
            case ("W") -> 1;
            case ("S") -> 2;
            default -> 3;
        };
    }
    
    static int[] solve(int r, int g, String[] dir, int[] time) {
        int[] res = new int[2]; // 总耗时 = 最后一辆车离开时间 - time[0]
        int lastCarLeaveTime = 0; // 最后一辆车离开时间
        
        int[] dirLeaveTime = new int[4]; // 按E/W/S/N方向存最后一辆车离开的时间
        int len = dir.length; // 车的数量
        int circle = r + g; // 等待红绿灯周期
        
        // 遍历车辆
        for (int i = 0; i < len; i++) {
            int wait = 0;  // 等待时间
            int dirIdx = directionToIndex(dir[i]); // 方向下标
            int arriveTime = time[i]; // 到达时间
            // ⭐车辆排在该方向第一个的时间
            int passTime = Math.max(arriveTime, dirLeaveTime[dirIdx]);
            // 计算红绿灯等待时长
            int mod = passTime % circle; // 余数
            if (dir[i].equals("E") || dir[i].equals("W")) {
                // 东西方向 判断等待时间
                wait = mod >= r ? passTime : r - mod + passTime;
            } else {
                // 南北方向 判断等待时间
                wait = mod < r ? passTime : circle - mod + passTime;
            }
            // 更新离开时间 = 车辆排在该方向第一个的时间+等待红绿灯时间+1(自己通过的时间)
            dirLeaveTime[dirIdx] = wait + 1;
        }
        
        for (final int j : dirLeaveTime) {
            lastCarLeaveTime = Math.max(lastCarLeaveTime, j);
        }
        
        res[1] = lastCarLeaveTime;
        res[0] = lastCarLeaveTime- time[0];
        
        return res;
    }
    
    static void main() {
        int r = 3;
        int g = 5;
        String[] dir = {"E", "S", "W", "N"};
        int[] time = {0, 1, 3, 6};
        // [9,9]
        System.out.println(Arrays.toString(solve(r, g, dir, time)));
        int r1 = 2;
        int g1 = 3;
        String[] dir1 = {"E", "E", "S"};
        int[] time1 = {1, 3, 4};
        // [5,6]
        System.out.println(Arrays.toString(solve(r1, g1, dir1, time1)));
    }
}
