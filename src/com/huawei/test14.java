package com.huawei;

public class test14 {
    /*
    14.【26.8.23-2】小牛牛超市选品
    题目描述:
    牛牛在超市采购商品，每件商品有对应价格(均为正整数)。给定预算1imit，要求选出连续一段商品，满足两条约束:
    1.这段商品总价格严格大于limit
    2.这段商品中最贵商品价格<=limit
    请找出满足以上两条规则的最短连续商品长度;不存在合法区间输出0
    
    约束条件:
    0<limit<100
    0<n<=10
    0<Ai<=10
    
    输入描述:
    参数1:商品数量n
    参数2:预算上限limit
    参数3:商品价格数组，数组元素代表每件商品价格Ai
    
    输出描述:
    合法最短区间长度;无合法区间输出0。
    */
    
    static int solve(int n, int limit, int[] prices) {
        int res = Integer.MAX_VALUE;
        
        for (int start = 0; start < n; start++) {
            
            int tempSum = 0;
            int end = start;
            
            while (end < n && prices[end] <= limit) {
                tempSum += prices[end];
                
                int len = end - start + 1;
                if (tempSum > limit) {
                    res = Math.min(res, len);
                    break;
                }
                end++;
                
            }
            
        }
        
        return res == Integer.MAX_VALUE ? 0 : res;
    }
    
    
    public static void main(String[] args) {
        // 6
        // 7
        // 2,3,1,2,4,3
        
        // 3
        int n = 6;
        int limit = 7;
        int[] price = {2, 3, 1, 2, 4, 3};
        System.out.print(solve(n, limit, price));
    }
    
    
}
