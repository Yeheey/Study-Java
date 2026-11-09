package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v260909_2 {
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
    
    static void dfs(int node, List<List<Integer>> graph, boolean[] visited, List<Integer> res) {
        visited[node] = true; // 标记当前node已访问
        res.add(node); // 存入遍历值
        
        // ✅遍历【当前node】的每一个邻居（重点！）
        for (int next : graph.get(node)) {
            if (!visited[next]) {
                dfs(next, graph, visited, res);
            }
        }
    }
    
    static List<Integer> solve(int n, int m, int[][] edges) {
        // 邻接表：下标1~n，graph[i]存i的所有邻接点
        List<List<Integer>> graph = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }
        
        // 双向加边
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        
        // 邻接点排序，保证字典序优先
        for (int i = 1; i <= n; i++) {
            Collections.sort(graph.get(i));
        }
        
        // 深度搜索
        List<Integer> res = new ArrayList<>();
        boolean[] visited = new boolean[n + 1];
        // 从顶点1出发
        dfs(1, graph, visited, res);
        
        return res;
    }
    
    public static void main(String[] args) {
        int n = 6;
        int m = 5;
        int[][] edges = {{1, 2}, {1, 3}, {2, 4}, {3, 5}, {3, 6}};
        List<Integer> ans = solve(n, m, edges);
        System.out.print(ans);
        // 预期输出：[1, 2, 4, 3, 5, 6]
    }
}
