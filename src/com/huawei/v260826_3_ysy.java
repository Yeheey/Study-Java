package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260826_3_ysy {
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
    
    static void dfs(int idx) {
        if (idx == -1) {
            return;
        }
        int baseScore = baseScores[idx];
        
        int leftIdx = leftNode[idx]; // 当前左孩子编号
        int rightIdx = rightNode[idx]; // 当前右孩子编号
        
        int leftSum = 0; // 当前节点左和
        int rightSum = 0; // 当期节点右和
        
        if (leftIdx != -1) {
            dfs(leftIdx);
            leftSum = result[leftIdx];
        }
        
        if (rightIdx != -1) {
            dfs(rightIdx);
            rightSum = result[rightIdx];
        }
        
        result[idx] = baseScore + leftSum + rightSum;
    }
    
    static List<Integer> solve(int n, int[] left, int[] right, int[] base) {
        // 初始化
        result = new int[n];
        leftNode = left;
        rightNode = right;
        baseScores = base;
        
        for (int i = 0; i < n; i++) {
            // 如果在外层定义左和 右和 说明是全树共用的
            dfs(i);
        }
        
        List<Integer> res = new ArrayList<>();
        for (int num : result) {
            res.add(num);
        }
        return res;
    }
    
    public static void main(String[] args) {
        int n = 5;
        int[] left = {1, 3, -1, -1, -1};
        int[] right = {2, -1, 4, -1, -1};
        int[] base = {5, 3, 2, 4, 7};
        // [21, 7, 9, 4, 7]
        System.out.println(solve(n, left, right, base));
    }
}
