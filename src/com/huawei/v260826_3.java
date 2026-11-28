package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260826_3 {
    /*
     【26.8.26-3】部门绩效汇总--【二叉树+DFS】
     题目描述:
     某公司使用二叉树结构管理组织汇报关系:每个节点代表一名员工，员工的“左下属”为研发组成员，“右下属”为产品组成员。
     年终绩效考核时，每位员工有一个基础绩效分。管理者的最终绩效分=自身基础分+左子树所有员工的最终绩效分之和+右子树所有员工的最终绩效分之和。
     
     输入描述:
     n:员工总数(1≤n≤1000)，编号0到n-1。
     left:长度为n的整数数组，left[i]表示其左下属的编号，-1表示无。
     right:长度为n的整数数组，right[i]表示其右下属的编号，-1表示无。
     base:长度为n 的整数数组，base[i]表示该员工的基础绩效分。
     
     输出描述:
     长度为n的数组，第i个元素表示员工i的最终绩效分。顺序与员工编号顺序一致。
    */
    
    static int[] result; // 存每个节点的总绩效
    static int[] leftNode; // 左树孩子编号
    static int[] rightNode; // 右树孩子编号
    static int[] baseScores; // 基础绩效分
    
    static int dfs(int node) {
        if (node == -1) {
            return 0;
        }
        
        int leftIdx = leftNode[node];
        int rightIdx = rightNode[node];
        
        int leftSum = 0;
        int rightSum = 0;
        
        if (leftIdx != -1) {
            leftSum = dfs(leftIdx);
        }
        if (rightIdx != -1) {
            rightSum = dfs(rightIdx);
        }
        
        int res = baseScores[node] + leftSum + rightSum;
        result[node] = res;
        
        return res;
    }
    
    static List<Integer> solve(int n, int[] left, int[] right, int[] base) {
        // 初始化
        leftNode = left;
        rightNode = right;
        baseScores = base;
        result = new int[n];
        
        // 找根节点
        boolean[] visited = new boolean[n];
        for (int i = 0; i < n; i++) {
            if (leftNode[i] != -1) {
                visited[leftNode[i]] = true;
            }
            if (rightNode[i] != -1) {
                visited[rightNode[i]] = true;
            }
        }
        int root = -1; // 设置根节点 n>=1 一定存在根节点
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                root = i;
                break;
            }
        }
        
        // 从根节点递归查找
        dfs(root);
        
        List<Integer> res = new ArrayList<>();
        for (final int r : result) {
            res.add(r);
        }
        return res;
    }
    
    static void main() {
        int n = 5;
        int[] left = {1, 3, -1, -1, -1};
        int[] right = {2, -1, 4, -1, -1};
        int[] base = {5, 3, 2, 4, 7};
        // [21, 7, 9, 4, 7]
        System.out.println(solve(n, left, right, base));
    }
}
