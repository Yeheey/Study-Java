package com.huawei;

public class v260823_2_ysy {
    /*
    【26.8.23-2】小牛牛超市选品--【双指针】--30min
    题目描述:
    牛牛在超市采购商品，每件商品有对应价格(均为正整数)。给定预算limit，要求选出连续一段商品，满足两条约束:
    1.这段商品总价格严格大于limit
    2.这段商品中最贵商品价格<=limit
    请找出满足以上两条规则的最短连续商品长度;不存在合法区间输出0
    约束条件: 0<limit<100 0<n<=10 0<Ai<=10
    
    输入描述:
    参数1:商品数量n
    参数2:预算上限limit
    参数3:商品价格数组，数组元素代表每件商品价格Ai
    
    输出描述:
    合法最短区间长度;无合法区间输出0。
    */
    
    static int solve(int limit, int[] prices) {
        int res = Integer.MAX_VALUE;
        int n = prices.length;
        
        for (int i = 0; i < n; i++) {
            // 滑动窗口
            int winSum = 0;
            for (int j = i; j < n; j++) {
                if (prices[j] <= limit && winSum + prices[j] > limit) {
                    int winLen = j - i + 1;
                    res = Math.min(res, winLen);
                    break;
                } else if (prices[j] > limit) {
                    break;
                } else {
                    winSum += prices[j];
                }
            }
        }
        
        return res == Integer.MAX_VALUE ? 0 : res;
    }
    
    public static void main(String[] args) {
        // case1
        int limit = 7;
        int[] price = {2, 3, 1, 2, 4, 3};
        // 3
        System.out.println(solve(limit, price));
        
        // case2：区间里面有一个高价商品>limit，整段作废
        int limit4 = 5;
        int[] price4 = {2, 6, 2, 2};
        // 0
        System.out.println(solve(limit4, price4));
        
        // case2：多个可选区间，取最短
        int limit5 = 6;
        int[] price5 = {1, 4, 1, 3, 2};
        // 3
        System.out.println(solve(limit5, price5));
        
        
    }
}
