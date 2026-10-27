package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test10 {
    /*
        10.【26.10.26-1】仓库查询
        题目描述:
        有一批货物A会按顺序进入仓库，第i件货物的价格是A[i]。
        管理员会发起一组查询请求B，对于第j次查询，你需要回答:在当前已经进入仓库的前Bj件货物中，按价格从小到大排序后，第j位的价格是多少。
        题目保证所有查询合法，也就是说在第j次查询时，当前货物数量不少于j。
        其中1≤j≤B[j]≤5000,1≤A[i]≤1000000。
        
        输入描述:
        ·参数1:整数n，表示货物数量;
        ·参数2:整数m，表示查询次数;
        ·参数3:一组货物价格的列表A，其中元素A[i]表示货物的价格;
        ·参数4:一组查询指令列表B，
        其中元素B[j] 表示第j次查询的货物范围区间。
        
        输出描述:
        一个数组Q，其中每个元素为查询列表B中，每次指令查询的价格结果。
        */
    static List<Integer> solve(int n, int m, int[] price, int[] query) {
        List<Integer> res = new ArrayList<>();
        // 按照下标排序
        List<List<Integer>> order = new ArrayList<>();
        List<Integer> orderItem = new ArrayList<>();
        // 遍历货物
        for (int i = 0; i < price.length; i++) {
            orderItem.add(price[i]);
            List<Integer> sorted = new ArrayList<>(orderItem);  // 复制一份
            sorted.sort((a, b) -> a - b);
            order.add(sorted);
        }
        System.out.println(order);
        // 遍历查询
        for (int i = 0; i < query.length; i++) {
            int resItem = order.get(query[i] - 1).get(i);
            System.out.println(resItem);
            res.add(resItem);
        }
        
        return res;
    }
    
    static void main() {
        int n = 7;
        int m = 4;
        int[] price = {9, 7, 2, 8, 14, 1, 8};
        int[] query = {1, 2, 6, 6}; // query[3]找前6位进库第3大的值
        // [9,9,7,8]
        System.out.println(solve(n, m, price, query));
    }
}
