package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v261101_2 {
    /*
    【26.11.1-2】获取利润第K大的值-【暴力枚举全部子数组和 + 排序+前缀和】--27min
    ⭐注意：64位整数用long防止溢出 存前缀和降低时间复杂度
    
    题目描述
    小张是一名金融交易员，记录了某只股票连续 n 个交易日每天的涨跌金额，形成整数数组 a。上涨记为正数，下跌记为负数，横盘记为 0。
    一次交易选择在第 i 个交易日买入，在第 j 个交易日卖出，其中 0 ≤ i ≤ j < n。这次交易的利润为从第 i 天到第 j 天的涨跌金额之和，即 a[i] + a[i+1] + … + a[j]。
    小张希望列出所有可行交易的利润，并按照从大到小的顺序排名。请计算排名第 K 的利润值。
    
    只要买入日或卖出日不同，就视为不同交易。利润相同的交易也分别占据一个名次，不进行去重。同一天买入并卖出也是可行交易；
    不允许选择空区间。利润可以为正数、零或负数，均按照数值大小参与排名。
    
    输入描述
    第一行输入两个整数 n 和 K，以空格分隔，分别表示交易日数量和目标排名。
    第二行输入 n 个整数，以空格分隔，依次表示每天的涨跌金额。
    数据范围：1 ≤ n ≤ 1000，1 ≤ K ≤ n(n+1)/2。K 保证合法，答案一定存在，无需输出特殊值。
    
    截图未给出单日涨跌金额的绝对值上限。本整理版约定输入金额及所有连续区间的利润均可使用 64 位有符号整数表示。
    
    输出描述
    输出一行一个整数，表示所有非空连续子数组和中，按从大到小排名第 K 的利润值。
    */
    
    static long solve(int n, int k, int[] nums) {
        // 索引是第i天 值是第i天的前缀和
        long[] preTotal = new long[n + 1];
        for (int i = 0; i < n; i++) {
            preTotal[i + 1] = preTotal[i] + nums[i];
        }
        
        List<Long> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                res.add(preTotal[j + 1] - preTotal[i]);
            }
        }
        
        // ⭐改为long比较器
        res.sort((a, b) -> Long.compare(b, a));
        
        return res.get(k - 1);
    }
    
    static void main() {
        int n = 3;
        int k = 1;
        int[] nums = {1, 2, 3};
        // 6
        System.out.println(solve(n, k, nums));
    }
}
