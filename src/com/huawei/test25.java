package com.huawei;

public class test25 {
    /*
    25.【26.8.9-1】查找最佳充电策略
    题目描述
    给定一个一维数组 priceArray，表示未来 priceRecords 小时内每小时的电价（单位：分/kWh）。
    找出充电成本最低的连续 hours 个小时时间段的开始时刻点。
    若存在多种成本最低方案，优先返回最低成本方案的最早的时刻点。
    
    输入描述
    参数 1：整数 priceRecords，表示电价记录数量
    参数 2：整数 hours，表示连续小时数
    参数 3：一维数组 priceArray，表示每小时的电价 price1~priceN
    约束条件：1 <= priceRecords <= 24，1 <= hours <= priceRecords，1 <= price[i] <= 100
    输入有三行：
    priceRecords
    hours
    priceArray
    其中 priceArray 按示例使用英文逗号分隔，允许逗号后有空格。
    
    输出描述
    返回一个整数，表示最优充电时段的起始索引（从 0 开始）。
    */
    
    // 暴力解法 可进阶滑动窗口
    static int solve(int priceRecords, int hours, int[] priceArray) {
        int res = 0;
        
        int curIndex = 0;
        int minSum = 0;
        for (int i = 0; i < hours; i++) {
            minSum += priceArray[i];
        }
        curIndex += 1;
        while (curIndex < priceRecords && curIndex + hours <= priceRecords) {
            int curSum = 0;
            for (int i = curIndex; i < curIndex + hours; i++) {
                curSum += priceArray[i];
                
            }
            if (curSum < minSum) {
                minSum = curSum;
                res = curIndex;
            }
            
            curIndex++;
            
        }
        return res;
    }
    
    static int solve1(int priceRecords, int hours, int[] priceArray) {
        int res = 0;
        // 1.先计算默认初始总电量
        int initSum = 0;
        for (int i = 0; i < hours; i++) {
            initSum += priceArray[i];
        }
        // 2.从1开始 滑动窗口
        int curSum = 0;
        for (int s = 1; s <= priceRecords - hours; s++) {
            curSum = initSum - priceArray[s - 1] + priceArray[s + hours - 1];
            if (curSum < initSum) {
                res = s;
                initSum = curSum;
            }
        }
        
        return res;
    }
    
    static void main() {
        // int priceRecords = 12;
        // int hours = 3;
        // int[] priceArray = {25, 15, 20, 18, 12, 25, 30, 28, 22, 16, 14, 35};
        // // 2
        
        int priceRecords = 4;
        int hours = 2;
        int[] priceArray = {10, 9, 8, 1};
        // 2
        
        int ans = solve(priceRecords, hours, priceArray);
        System.out.println(ans);
        System.out.println(solve1(priceRecords, hours, priceArray));
    }
}
