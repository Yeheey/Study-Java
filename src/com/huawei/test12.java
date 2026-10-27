package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test12 {
    /*
     12.【26.8.30-3】部门绩效汇总
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
    
    private static int[] leftChild;
    private static int[] rightChild;
    private static int[] baseScores;
    private static int[] result;
    
    static List<Integer> solve(int n, int[] left, int[] right, int[] base) {
        // 0. 全局变量赋值，初始化结果数组
        leftChild = left;
        rightChild = right;
        baseScores = base;
        result = new int[n]; // 必须分配长度，否则空指针
        
        // 1. 查找根节点：标记所有出现过的子节点，未被标记的就是根
        boolean[] isChild = new boolean[n]; // 用基本类型boolean，默认false，避免空值问题
        for (int i = 0; i < n; i++) {
            if (left[i] != -1) {
                isChild[left[i]] = true; // left[i] 是子节点，标记
            }
            if (right[i] != -1) {
                isChild[right[i]] = true; // right[i] 是子节点，标记
            }
        }
        int root = -1;
        for (int i = 0; i < n; i++) {
            if (!isChild[i]) { // 从未当过子节点 → 根节点
                root = i;
                break;
            }
        }
        
        // 2. 后序遍历递归计算
        dfs(root);
        
        // 3. 数组转List返回
        List<Integer> res = new ArrayList<>();
        for (int num : result) {
            res.add(num);
        }
        return res;
    }
    
    static int dfs(int node) {
        // 空节点，绩效和为0
        if (node == -1) {
            return 0;
        }
        // 后序：先计算左右子树总和
        int leftSum = dfs(leftChild[node]);
        int rightSum = dfs(rightChild[node]);
        
        // 当前节点最终绩效 = 自身基础分 + 左子树和 + 右子树和
        int current = baseScores[node] + leftSum + rightSum;
        result[node] = current;
        
        // 返回当前子树总和，供父节点计算
        return current;
    }
    
    public static void main(String[] args) {
        // 测试用例：5,[1,3,-1,-1,-1],[2,-1,4,-1,-1],[5,3,2,4,7]
        // 预期输出：[21, 7, 9, 4, 7]
        int n = 5;
        int[] left = {1, 3, -1, -1, -1};
        int[] right = {2, -1, 4, -1, -1};
        int[] base = {5, 3, 2, 4, 7};
        
        System.out.println(solve(n, left, right, base));
    }
}
