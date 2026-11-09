package com.huawei;

import java.util.Arrays;

public class v260729_3_todo {
    /*
     路口等待时间
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
          取值范围：
     1≤R,G≤60
     到达时刻 ≤100
     车辆数量 ≤100
     */
    
    static int[] solve(int r, int g, String[] dir, int[] time) {
        int[] res = new int[2];
        int totalTime = 0;
        int lastTime = 0; // totalTime + time[0]
        
        int circle = r + g; // 周期
        int n = dir.length; // 车数量
        int[] lastDirTime = new int[4]; // 四个方向上最后一辆车离开时间 EWSN顺序存放
        
        for (int i = 0; i < n; i++) {
            String direction = dir[i];
            int curTime = time[i];
            
            // 【改动1：先拿到排队约束的最早尝试时间，再算灯】
            switch (direction) {
                case "E" -> curTime = Math.max(curTime, lastDirTime[0]);
                case "W" -> curTime = Math.max(curTime, lastDirTime[1]);
                case "S" -> curTime = Math.max(curTime, lastDirTime[2]);
                default -> curTime = Math.max(curTime, lastDirTime[3]);
            }
            
            // 【改动2：用curTime 去算红绿灯】
            int mod = curTime % circle;
            int passTime = 0; // 当前车辆最早通行时间
            
            if (direction.equals("E") || direction.equals("W")) {
                passTime = mod < r ? r - mod + curTime : curTime;
            } else {
                passTime = mod >= r ? circle - mod + curTime : curTime;
            }
            
            // 同一方向的车辆 需要等待前一个车辆通过才可以开走
            int curStart = passTime;
            switch (direction) {
                case "E" -> {
                    lastDirTime[0] = curStart + 1;
                }
                case "W" -> {
                    lastDirTime[1] = curStart + 1;
                }
                case "S" -> {
                    lastDirTime[2] = curStart + 1;
                }
                default -> {
                    lastDirTime[3] = curStart + 1;
                }
            }
        }
        
        // 计算最后一辆车离开时间
        for (final int j : lastDirTime) {
            lastTime = Math.max(lastTime, j);
        }
        // 计算总耗时
        totalTime = lastTime - time[0];
        res[0] = totalTime;
        res[1] = lastTime;
        return res;
    }
    
    //【改动3：main方法签名修复】
    public static void main(String[] args) {
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
