package com.huawei;

public class test27 {
    /*
    27.【26.8.9-3】云南菌子加工
    题目描述
    云南的菌子加工厂要加工一批野生菌，所有菌子同时进厂。由于菌子新鲜度随时间流失，价值不断衰减。
    工厂不能同时加工菌子，即逐个串行加工，正在加工的菌子价值不再衰减，加工完毕立即售卖。
    
    已知每个菌子的初始市场价值和新鲜度衰减速度（每小时损失的价值），菌子的实际价值 = 初始价值 - 衰减速度 × 从进入工厂到开始加工的等待时间。
    在给定时间内，合理安排加工顺序使得加工完成的菌子总价值最大化。
    
    每个菌子的加工时间固定为 5 小时，加工所有菌子的总耗时不能超过给定的总加工时间。若菌子的实际价值衰减至零或负值，则不能再售卖，即不需要参与加工。
    
    输入描述
    输入为四行：count total values decays
    count 表示菌子数量，1 <= count <= 15
    total 表示可用总加工时间，5 <= total <= 75，单位小时
    values[i] 表示第 i 个菌子的初始市场价值
    decays[i] 表示第 i 个菌子的衰减速度
    数组 values 和 decays 按示例使用英文逗号分隔。
    
    输出描述
    输出一个整数，表示在给定时间内能加工完成的菌子的最大总价值。
    */
    
    static int nums = 0;
    
    static int solve(int count, int total, int[] value, int[] decays) {
        nums = total / 5; // 一共可加工的菌子数量
        int waitTime = 0; // 等待加工的时间
        int maxPrice = 0;
        return maxPrice;
    }
    
    
    static void main() {
        int count = 3;
        int total = 20;
        int[] value = {20, 10, 15};
        int[] decays = {3, 1, 2};
        // 25
        System.out.print(solve(count, total, value, decays));
    }
}
