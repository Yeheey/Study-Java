package leetcode;

public class leetcode_122 {
    /*
        【122.买卖股票的最佳时机 II】--【贪心】--2min
        ☢️注意和力扣121的区别 那题只能买卖一次 本题可以多次买卖
        给你一个整数数组 prices ，其中 prices[i] 表示某支股票第 i 天的价格。
        在每一天，你可以决定是否购买和/或出售股票。你在任何时候 最多 只能持有 一股 股票。
        然而，你可以在 同一天 多次买卖该股票，但要确保你持有的股票不超过一股。
        返回 你能获得的最大利润。
        */
    public static int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 1; i < prices.length; i++) {
            int cur = Math.max(0, prices[i] - prices[i - 1]);
            max += cur;
        }
        return max;
    }
    
    static void main() {
        int[] prices = {7, 1, 5, 3, 6, 4};
        // 7
        System.out.println(maxProfit(prices));
        
        int[] prices1 = {1, 2, 3, 4, 5};
        // 4
        System.out.println(maxProfit(prices1));
        
        int[] prices2 = {7, 6, 4, 3, 1};
        // 0
        System.out.println(maxProfit(prices2));
    }
}
