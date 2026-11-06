package com.huawei;

import java.util.*;

public class v260812_2 {
    /*
    23.【26.8.12-2】获取二叉树第k层的数值
    题目描述
    给定一棵节点数为 n 的二叉树，树中每个节点均有一个节点编号和整型取值，节点编号取值 0 到 n−1，根节点所在层为第 0 层，其子节点为第 1 层，以此类推。
    已知节点取值以及节点父子关系，请返回第 k 层节点取值列表，按升序排列并去重。
    
    输入描述
    整数 n，表示节点数
    数组 vals，vals[i] 表示编号为 i 的节点取值
    二维数组 edges，edges 的子数组采用 [父节点编号, 子节点编号] 方式描述节点父子关系。
    例如：[[0,1],[0,2]] 中，[0,1] 表示节点 0 是节点 1 的父节点，同理 [0,2] 表示节点 0 是节点 2 的父节点整数 k，表示待返回取值列表的层
    其中 n 取值范围 1≤n≤1000，树的根节点固定编号为 0。节点值 −1000≤vals[i]≤1000。
    
    输出描述
    第 k 层节点的取值列表，升序排列并去除重复值。
    */
    
    static List<Integer> solve(int n, int[] vals, int[][] edges, int k) {
        
        // 1.存一个相邻表
        List<List<Integer>> children = new ArrayList<>();
        
        for (int i = 0; i < vals.length; i++) {
            children.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            int fatherNode = edge[0];
            int childNode = edge[1];
            // 下标是节点编号 值是当前节点的子节点编号
            children.get(fatherNode).add(childNode);
        }
        System.out.println("children:" + children);
        
        // 2.设置一个队列 存当前层的节点编号 根据编号去找vals对应值
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0); // 默认根节点为0
        
        // 设置一个set用于去重
        HashSet<Integer> resSet = new HashSet<Integer>();
        
        int curLevel = 0; // 当前层
        
        while (!queue.isEmpty()) {
            // ✅【重点修复】进入一层，先固定本层节点总数！！
            int levelSize = queue.size();
            
            // 2.1 如果当前层就是要找的目标层
            if (curLevel == k) {
                // 把队里的值拿出来-下标 根据下标去找vals对应值 存到结果中
                for (int i = 0; i < levelSize; i++) {
                    int node = queue.poll();
                    resSet.add(vals[node]);
                    
                }
                // ✅收集完目标层，后面不用继续遍历子节点了，直接跳出while
                break;
            }
            
           
            // 2.2 如果非目标层 则需要遍历当前层
            for (int i = 0; i < levelSize; i++) {
                int node = queue.poll();
                // 拿到当前层的子节点
                // queue.addAll(children.get(node));
                for (int child : children.get(node)) {
                    queue.add(child);
                }
            }
            
            // 2.3 层数加1
            curLevel++;
        }
        
        // 3.数组排序
        List<Integer> res = new ArrayList<>(resSet);
        Collections.sort(res);
        
        return res;
    }
    
    static void main() {
        int n = 5;
        int[] vals = {5, 3, 8, 3, 7};
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}};
        int k = 1;
        
        System.out.println("ans:" + solve(n, vals, edges, k)); // 4
    }
}
