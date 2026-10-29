package com.huawei;

public class test16 {
    /*
    16.【26.8.19-1】基站低能耗时段统计
    题目描述：
    基站运维团队需要统计某区域基站的低能耗连续运行时段数目，以评估基站的节能优化效果。给定以下信息:
    ·整数数组power:power[i]表示该基站第i小时的能耗值(单位:千瓦时)，power[i]>=0，能耗不会为负;
    ·整数target:低能耗阈值(单位:千瓦时)，要求连续时段的总能耗不超过该阈值;
    ·整数max_hour:最大统计时长(单位:小时)，要求连续时段的时长不超过该值，且至少为1小时。
    请你统计满足以下两个条件的连续运行时段(子数组四)的数目:
    1.连续时段的时长属于[1,max_hour];
    2.该时段内的总能耗<=target。
    补充说明:
    1 <= power.length <= 10^5
    0 <= power[i] <= 100
    0 <= target <= 10^7
    1 <= max_hour < power.length
    ·所有能耗值非负
    输入描述
    输入包含两行:
    ·第一行:整数数组power的表示形式，如[0,0,0];
    ·第二行:两个整数target和max_hour，以逗号分隔，如0,2。
    输出描述：
    输出一个整数，表示满足条件的连续运行时段数目。
    */
    static int solve(int[] power, int target, int max_hour) {
        int res = 0;
        for (int left = 0; left < power.length; left++) {
            int tempSum = 0;
            for (int right = left; right < Math.min(left + max_hour, power.length); right++) {
                tempSum += power[right];
                if (tempSum <= target) {
                    res++;
                } else {
                    break;
                }
            }
        }
        return res;
    }
    
    static void main() {
        // int[] power = {3, 1, 2, 4, 1};
        // int target = 5;
        // int max_hour = 3;
        // output 8
        
        int[] power = {0, 0, 0};
        int target = 0;
        int max_hour = 2;
        // output 5
        System.out.print(solve(power, target, max_hour));
    }
}
