package com.huawei;

public class v260809_1_ysy {
    /*
    25.【26.8.9-1】查找最佳充电策略--【滑动窗口+贪心】--13min
    题目描述
    给定一个一维数组 priceArray，表示未来 priceRecords 小时内每小时的电价（单位：分/kWh）。
    找出充电成本最低的连续 hours 个小时时间段的开始时刻点。
    若存在多种成本最低方案，优先返回最低成本方案的最早的时刻点。
    
    输入描述
    参数 1：整数 priceRecords，表示电价记录数量
    参数 2：整数 hours，表示连续小时数
    参数 3：一维数组 priceArray，表示每小时的电价 price1~priceN
    约束条件：1 <= priceRecords <= 24，1 <= hours <= priceRecords，1 <= price[i] <= 100
    输入有三行：priceRecords hours priceArray
    其中 priceArray 按示例使用英文逗号分隔，允许逗号后有空格。
    
    输出描述
    返回一个整数，表示最优充电时段的起始索引（从 0 开始）。
    */
    static int solve(int hours, int[] priceArray) {
        int bestIdx = 0;
        int len = priceArray.length;
        int winSum = 0;
        // 初始化窗口值
        int sum = 0;
        for (int i = 0; i < hours; i++) {
            sum += priceArray[i];
        }
        int start = 1;
        while (start + hours < len + 1) {
            winSum = winSum - priceArray[start - 1] + priceArray[start - 1 + hours];
            // 如果窗口小于sum 更新sum 更新下标
            if (winSum < sum) {
                sum = winSum;
                bestIdx = start;
            }
            start++;
        }
        
        return bestIdx;
    }
    
    static void main() {
        int hours1 = 3;
        int[] priceArray1 = {25, 15, 20, 18, 12, 25, 30, 28, 22, 16, 14, 35};
        // 2
        System.out.println(solve(hours1, priceArray1));
        
        int hours = 2;
        int[] priceArray = {10, 9, 8, 1};
        // 2
        System.out.println(solve(hours, priceArray));
    }
}
