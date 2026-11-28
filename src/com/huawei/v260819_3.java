package com.huawei;


import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;

public class v260819_3 {
    /*
    17.【26.8.19-3】点亮战争迷雾--【二叉树+BFS+DFS+动态规划+贪心】
    题目描述：
    有一张二叉树的地图，每一个节点都被战争迷雾所覆盖。在二叉树节点上插上一个侦察守卫，可以照亮该节点自身以及它的父节点和它的子节点的战争迷雾。
    在每个节点上插上侦察守卫的成本并不一样，用cost[i]表示节点i上的成本，每个节点的成本是正整数。
    
    要求:
    1.所有的战争迷雾都被驱散。
    2.所花费的总成本最小。
    
    补充说明:
    1.单个节点的成本,1<=cost[i]<=100;
    2.节点总数量<=1000。
    
    输入描述：
    一行，一个层序遍历字符串(含首尾圆括号)，空节点用#表示，例如(5,1,10,2,8,#,3)。
    
    输出描述：
    输出最小的总费用。
    */
    
    /**
     * 这一题就先会怎么建树即可
     * */
    static TreeNode buildTree(String s) {
        // 解析字符串
        String ss = s.substring(1, s.length() - 1);
        if (ss.isEmpty()) {
            return null;
        }
        String[] arr = ss.split(",");
        if (arr.length == 0) {
            return null;
        }
        // 去除首位空串
        String[] tree = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            tree[i] = arr[i].trim();
        }
        if (tree[0].equals("#")) {
            return null;
        }
        // tree根节点 非空
        TreeNode root = new TreeNode(Integer.parseInt(tree[0]));
        // 设置一个队列 BFS遍历树 tree
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        
        int node = 1;
        while (!queue.isEmpty() && node < tree.length) {
            TreeNode t = queue.poll();
            // 如果不是空子节点 加入
            if (!tree[node].equals("#")) {
                t.left = new TreeNode(Integer.parseInt(tree[node]));
                queue.offer(t.left);
                node++;
            }
            if (!tree[node].equals("#")) {
                t.right = new TreeNode(Integer.parseInt(tree[node]));
                queue.offer(t.right);
                node++;
            }
        }
        
        System.out.println(Arrays.toString(arr));
        return root;
    }
    
    static int solve(String s) {
        TreeNode tree = buildTree(s);
        
        return 0;
    }
    
    static void main() {
        String s = "(5,1,10,2,8,#,3)";
        // 4
        System.out.println("ans:" + solve(s));
        
        String s1 = "(3,1,1)";
        // 2
        System.out.println("ans:" + solve(s1));
    }
    
    
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
}
