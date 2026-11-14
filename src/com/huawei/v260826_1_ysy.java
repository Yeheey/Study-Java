package com.huawei;

import java.util.*;

public class v260826_1_ysy {
/*
    【26.8.26-1】仓库查询--【模拟计算】--8min
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
    
    // 方法1：暴力解法 容易超时
    static int find(int s, int i, int[] price) {
        List<Integer> temp = new ArrayList<>();
        for (int j = 0; j < s; j++) {
            temp.add(price[j]);
        }
        Collections.sort(temp);
        return temp.get(i);
    }
    
    static List<Integer> solve(int[] price, int[] query) {
        List<Integer> res = new ArrayList<>();
        
        int n = price.length;
        int m = query.length;
        
        for (int i = 0; i < m; i++) {
            // query[i]:找前s位进库第i大的值
            int s = query[i];
            int r = find(s, i, price);
            res.add(r);
        }
        
        return res;
    }
    
    // 方法2：维护一个有序队列
    static List<Integer> solve1(int[] price, int[] query) {
        List<Integer> res = new ArrayList<>();
        
        int n = price.length;
        int m = query.length;
        
        // 空间换时间 从第一个货物入场 就排序add进sorted里面
        List<List<Integer>> sorted =new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for (final int j : price) {
            temp.add(j);
            Collections.sort(temp);
            // 注意要重新new一个对象
            sorted.add(new ArrayList<>(temp));
        }
        
        for (int i = 0; i < m; i++) {
            // query[i]:找前s位进库第i大的值
            int s = query[i]-1;
            int r = sorted.get(s).get(i);
            res.add(r);
        }
        
        return res;
    }
    
    static void main() {
        int[] price = {9, 7, 2, 8, 14, 1, 8};
        int[] query = {1, 2, 6, 6}; // query[3]找前6位进库第3大的值
        // [9,9,7,8]
        System.out.println(solve1(price, query));
    }
}
