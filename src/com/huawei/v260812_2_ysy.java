package com.huawei;

import java.util.*;

public class v260812_2_ysy {
    /*
    【26.8.12-2】获取二叉树第k层的数值--【树的BFS+邻接表+队列】--60min
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
    
    static List<Integer> solve(int[] vals, int[][] edges, int k) {
        List<Integer> res = new ArrayList<>();
        // 遍历当前层数
        int level = 0;
        
        Queue<Integer> queue = new ArrayDeque<>();
        // 先把根节点入队
        queue.offer(0);
        
        while (!queue.isEmpty()) {
            // ⭐先固定队列长度 固定的是父节点的个数
            int curSize = queue.size();
            
            // 如果正好是要找的层数 直接存入结果 跳出循环
            if (level == k) {
                List<Integer> resIdx = new ArrayList<>(queue);
                for (Integer idx : resIdx) {
                    int curVal = vals[idx];
                    res.add(curVal);
                }
                break;
            }
            
            // 如果不是目标层 继续遍历 找孩子
            for (int i = 0; i < curSize; i++) {
                // 当前节点
                int node = queue.poll();
                
                for (final int[] edge : edges) {
                    if (edge[0] == node) {
                        // 找到孩子 入队
                        int child = edge[1];
                        queue.offer(child);
                    }
                }
            }
            
            
            // 找到所有的孩子 更新当前层数
            level++;
        }
        
        // 先去重，再排序
        HashSet<Integer> set = new HashSet<>(res);
        List<Integer> ans = new ArrayList<>(set);
        Collections.sort(ans);
        return ans;
    }
    
    /**
     * 假如说我想不到要固定长度来写 那么可以设置 父节点队列和子节点队列 不断更新维护
     * 优化：去重+排序  用treeSet来写
     */
    static List<Integer> solve1(int[] vals, int[][] edges, int k) {
        List<Integer> res = new ArrayList<>();
        // 遍历当前层数
        int level = 0;
        Queue<Integer> preQueue = new ArrayDeque<>();
        
        // 先把根节点入队
        preQueue.offer(0);
        
        // 遍历
        while (!preQueue.isEmpty()) {
            // 如果已经是目标层 存结果 退出循环
            if (level == k) {
                for (int idx : preQueue) {
                    res.add(vals[idx]);
                }
                // 去重+排序
                Set<Integer> set = new TreeSet<>(res);
                return new ArrayList<>(set);
            }
            
            // 找孩子
            int preLen = preQueue.size();
            Queue<Integer> curQueue = new ArrayDeque<>();
            
            for (int i = 0; i < preLen; i++) {
                int node = preQueue.poll();
                
                for (int[] edge : edges) {
                    if (edge[0] == node) {
                        curQueue.offer(edge[1]);
                    }
                }
            }
            // 更新前一层队列 为当前队列 层数+1
            preQueue = new ArrayDeque<>(curQueue);
            level++;
        }
        
        return res;
    }
    
    static void main() {
        int[] vals = {10, 20, 30, 40, 50, 60, 70};
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {2, 6}};
        int k = 1;
        // [3, 8]
        System.out.println("ans:找" + k + "层" + solve1(vals, edges, k));
        int k1 = 2;
        // [3, 7, 6, 4]
        System.out.println("ans:找" + k1 + "层" + solve1(vals, edges, k1));
    }
}
