package com.huawei;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class v260909_2_lei_extension_2 {
    /*
    * 给你二叉树的根节点 root ，返回其节点值的 层序遍历。（即逐层地，从左到右访问所有节点）。
    */
    public static List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        
        Queue<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            // 层数
            List<Integer> level = new ArrayList<Integer>();
            // 固定队的长度 当前层数
            int currentLevelSize = queue.size();
            // 从根节点下面遍历
            for (int i = 0; i < currentLevelSize; i++) {
                TreeNode curNode = queue.poll();
                level.add(curNode.val);
                if (curNode.left != null) {
                    queue.offer(curNode.left);
                }
                if (curNode.right != null) {
                    queue.offer(curNode.right);
                }
            }
            
            res.add(level);
        }
        return res;
    }
    
    public static void main(String[] args) {
        // 从下往上构造
        TreeNode node9 = new TreeNode(9);
        TreeNode node15 = new TreeNode(15);
        TreeNode node7 = new TreeNode(7);
        TreeNode node20 = new TreeNode(20, node15, node7);
        TreeNode root = new TreeNode(3, node9, node20);
        // [[3],[9,20],[15,7]]
        System.out.println(levelOrder(root));
    }
    
    // 给你二叉树的根节点 root ，返回其节点值的 层序遍历（即逐层地，从左到右访问所有节点）。
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode() {
        }
        
        TreeNode(int val) {
            this.val = val;
        }
        
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
