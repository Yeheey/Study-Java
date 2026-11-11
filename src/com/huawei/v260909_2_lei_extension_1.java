package com.huawei;

import java.util.*;

public class v260909_2_lei_extension_1 {
    /*
     【26.9.9-2】图的遍历--【层序遍历BFS用队列来做-40min】
     题目描述
     给定一个无向图，顶点编号从 1 到 n；从顶点 1 出发，进行层序优先搜索(BFS)
     当某个顶点有多个邻接点时，按照编号从小到大的顺序依次访问，输出遍历过程中访问顶点的顺序。
     1≤n≤100，0≤m≤100。
     若不连通，DFS 从顶点 1 出发无法遍历所有顶点，输出只包含可达顶点。输入保证没有自环如 (i,i)，即顶点到自身的边；
     同时输入保证不会有多条相同的边，如 (1,2) 出现两次。

     输入描述
     整数 n, m：表示顶点数和边数；
     二维数组 graph：每个元素有两个整数 u, v，表示 u 和 v 之间有一条无向边；
     输出描述
     数组：数组元素表示层序优先搜索(BFS)访问顶点的顺序（从 1 开始）；
     */
    
    /**
     * n是顶点数
     * m是边数
     * edge是给定的边
     */
    static List<Integer> solve(int n, int m, int[][] edges) {
        // 邻接表 索引是顶点 值是可到达的顶点数组
        List<List<Integer>> adj = new ArrayList<>();
        // 初始化一个n+1的邻接表 索引0废弃不用
        for (int i = 0; i < n + 1; i++) {
            adj.add(new ArrayList<>());
        }
        // 赋值
        for (final int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        // 邻接表可到达的顶点 从小到大排序
        for (int i = 0; i < n + 1; i++) {
            Collections.sort(adj.get(i));
        }
        
        List<Integer> res = new ArrayList<>();
        // 标记访问 索引是顶点 访问过置为true
        boolean[] visited = new boolean[n + 1];
        
        // 设置一个队列用于存层序遍历的值
        Queue<Integer> queue = new ArrayDeque<>();
        // 默认先塞一个1 并把索引1置为访问过true
        queue.offer(1);
        visited[1] = true;
        
        while (!queue.isEmpty()) {
            // 取出队首 存入结果中
            int cur = queue.poll();
            res.add(cur);
            // 遍历cur的邻接数组和访问标记数组
            for (int i = 0; i < adj.get(cur).size(); i++) {
                int node = adj.get(cur).get(i);
                // 访问过 跳出循环 进行下一次
                if (visited[node]) {
                    continue;
                }
                // 没访问过 标记访问 并入队
                visited[node] = true;
                queue.offer(node);
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        int n = 9;
        int m = 8;
        int[][] edges = {{3, 6}, {2, 3}, {2, 4}, {2, 5}, {4, 7}, {5, 8}, {5, 9}, {1, 2}};
        List<Integer> ans = solve(n, m, edges);
        System.out.print(ans);
        // 预期输出：[1,2,3,4,5,6,7,8,9]
    }
}
