package com.huawei;

public class c24 {
    /*
        【24.最大利润/贪心的商人】--【贪心】--30min 【自己再写一遍力扣-已写】--二刷6min
        题目描述
        商人经营一家店铺，有number种商品，由于仓库限制每件商品的最大持有数量是item[index]，每种商品的价格是item-price[item_index][day]
        通过对商品的买进和卖出获取利润，请给出商人在days天内能获取的最大的利润
        ☢️注：同一件商品可以反复买进和卖出（有的股票题只能持有一张股票 ）
        
        输入描述
        第一行输入商品的数量number，比如3
        第二行输入商品售货天数 days，比如3
        第三行输入仓库限制每件商品的最大持有数量是item[index]，比如4 5 6
        后面继续输入number行days列，含义如下：
        第一件商品每天的价格，比如1 2 3
        第二件商品每天的价格，比如4 3 2
        第三件商品每天的价格，比如1 5 3
        
        输出描述
        输出商人在这段时间内的最大利润。
        */
    public static int solve(int number, int days, int[] items, int[][] prices) {
        int maxRes = 0;
        // 遍历商品
        for (int i = 0; i < number; i++) {
            int curMax = 0; // 当前商品的最大利润
            // 遍历当前商品每天金额
            for (int j = 1; j < days; j++) {
                // 我只要获利的 舍去负利润
                curMax += Math.max(0, prices[i][j] - prices[i][j - 1]);
            }
            // 乘上对应数量
            maxRes += curMax * items[i];
        }
        return maxRes;
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
