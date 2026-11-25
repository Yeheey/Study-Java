package com.huawei;

public class c24_answer {
    public static int solve(int itemNumber, int days, int[] maxItems, int[][] prices) {
        // 贪心算法
        int maxProfit = 0;
        for (int i = 0; i < itemNumber; i++) { // 遍历每件商品
            for (int j = 1; j < days; j++) { // 遍历商品价格列表，求出每天的利润
                int profit = Math.max(0, prices[i][j] - prices[i][j - 1]);
                // 当前价格减去前一天价格，如果为负数则代表亏本，不计入利润
                maxProfit += profit * maxItems[i]; // 求出当前商品能够获取的最大利润
            }
        }
        return maxProfit;
    }
    
    static void main() {
        int number = 3; // 商品的数量
        int days = 3; // 商品售货天数
        int[] items = {4, 5, 6}; // 限制每件商品的最大持有数量
        int[][] price = {{1, 2, 3}, {4, 3, 2}, {1, 5, 2}}; // 第number件商品每天days的价格
        // 32
        System.out.println(solve(number, days, items, price));
        
        int number1 = 3; // 商品的数量
        int days1 = 4; // 商品售货天数
        int[] items1 = {4, 5, 6}; // 限制每件商品的最大持有数量
        int[][] price1 = {{1, 3, 2, 6}, {4, 3, 2, 10}, {1, 5, 3, 6}}; // 第number件商品每天days的价格
        // 106
        System.out.println(solve(number1, days1, items1, price1));
    }
}
