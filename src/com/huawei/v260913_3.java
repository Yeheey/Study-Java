package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260913_3 {
    /*
    【26.9.13-3】路由器网络连通性检测 【26.9.9-2】
    题目描述
    某公司机房部署了编号为 0∼n−1 的 n 台路由器，用于组成公司网络。
    网络工程师通过网桥桥接路由器，将路由器两两连接。网络连通性具备传递性：如果 A 和 B 连通，B 与 C 连通，那么 A 与 C 也可通过 B 进行连通。
    现在网络管理员需要进行连通性测试：指定测试的一台路由器，给出有多少台路由器与它连通（不包含自身）。
    
    输入描述
    参数 n（1≤n≤10000）为一个整数，表示路由器的数量。
    参数 links 为一个二维整数数组，每个元素 [u,v]（0≤u,v≤n−1）表示编号 u 与编号 v 的两台路由器连通，如 [1,2] 表示 1 号与 2 号路由器连通。
    参数 target（0≤target≤n−1）为一个整数，表示需要测量的指定路由器编号。
    为了便于在线判题，第一行输入路由器数量 n，第二行输入 JSON 风格的连接关系数组 links，第三行输入目标路由器编号 target。
    
    输出描述
    输出一个整数，表示与指定路由器连通的路由器数量（不包含其本身）。
    */
    static boolean[] visited;
    static List<List<Integer>> adj;
    
    // 深度搜索
    static int dfs(int cur) {
        visited[cur] = true;
        int cnt = 1;
        for (int neighbor : adj.get(cur)) {
            if (!visited[neighbor]) {
                cnt += dfs(neighbor);
            }
        }
        return cnt;
    }
    
    static int solve(int n, int[][] links, int target) {
        // 初始化
        visited = new boolean[n];
        adj = new ArrayList<>();
        
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }
        // 无向边：u加v，v加u
        for (int[] link : links) {
            int u = link[0];
            int v = link[1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }
        int total = dfs(target);
        return total - 1;
    }
    
    static void main() {
        int n = 6;
        int[][] links = {{0, 1}, {1, 2}, {3, 4}, {4, 5}};
        int target = 0;
        // output 2
        System.out.print(solve(n, links, target));
    }
}
