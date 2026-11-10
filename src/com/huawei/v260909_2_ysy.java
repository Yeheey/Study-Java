package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v260909_2_ysy {
    /*
     【26.9.9-2】图的遍历
     题目描述
     给定一个无向图，顶点编号从 1 到 n；从顶点 1 出发，进行深度优先搜索(DFS)
     当某个顶点有多个邻接点时，按照编号从小到大的顺序依次访问，输出遍历过程中访问顶点的顺序。
     1≤n≤100，0≤m≤100。
     若不连通，DFS 从顶点 1 出发无法遍历所有顶点，输出只包含可达顶点。输入保证没有自环如 (i,i)，即顶点到自身的边；
     同时输入保证不会有多条相同的边，如 (1,2) 出现两次。

     输入描述
     整数 n, m：表示顶点数和边数；
     二维数组 graph：每个元素有两个整数 u, v，表示 u 和 v 之间有一条无向边；
     输出描述
     数组：数组元素表示深度优先搜索访问顶点的顺序（从 1 开始）；
     */
    static List<Integer> res = new ArrayList<>();
    
    static List<Integer> solve(int n, int m, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        for (final int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        for (int i = 0; i < n; i++) {
            Collections.sort(graph.get(i));
        }
        
        boolean[] visited = new boolean[n + 1];
        // res.clear();
        dfs(1, visited, graph);
        
        return res;
    }
    
    static void dfs(int i, boolean[] visited, List<List<Integer>> graph) {
        if (visited[i]) return;
        // 当前节点没有访问过
        visited[i] = true;
        res.add(i);
        List<Integer> tmp = graph.get(i); // 当前节点的下一个节点集合
        for (Integer integer : tmp) {
            dfs(integer, visited, graph);
        }
        return;
    }
    
    
    public static void main(String[] args) {
        int n = 6;
        int m = 5;
        int[][] edges = {{1, 2}, {1, 3}, {2, 4}, {3, 5}, {3, 6}};
        System.out.print(solve(n, m, edges));
        // 预期输出：[1, 2, 4, 3, 5, 6]
    }
}
