package com.huawei;


import java.util.ArrayDeque;
import java.util.Queue;

public class test18 {
    /*
    17.【26.8.19-3】点亮战争迷雾
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
    
    static final long INF = (long) 1e18;
    
    // 字符串转为树
    static TreeNode buildTree(String s) {
        // 先获取括号内数据
        s = s.trim();
        String sBoody = s.substring(1, s.length() - 1);
        // 如果为空 返回空树
        if (sBoody.isEmpty()) {
            return null;
        }
        // 字符串转为数组
        String[] arr = sBoody.split(",");
        // 如果根节点是# 也返回空树
        if (arr[0].trim().equals("#")) {
            return null;
        }
        // 如果不是,则设置root根节点
        TreeNode root = new TreeNode(Integer.parseInt(arr[0].trim()));
        // 定义一个队列 设置左右子节点
        Queue<TreeNode> queue = new ArrayDeque<>();
        // 根节点入队
        queue.offer(root);
        
        int i = 1;
        while (i < arr.length && !queue.isEmpty()) {
            // 根 出队
            TreeNode cur = queue.poll();
            if (!arr[i].trim().equals("#")) {
                cur.left = new TreeNode(Integer.parseInt(arr[i].trim()));
                queue.offer(cur.left);
                i++;
            }
            if (!arr[i].trim().equals("#")) {
                cur.right = new TreeNode(Integer.parseInt(arr[i].trim()));
                queue.offer(cur.right);
                i++;
            }
        }
        return root;
    }
    
    static int solve(String s) {
        TreeNode root = buildTree(s);
        
        if (root == null) {
            return 0;
        }
        // 根节点只能被覆盖或放守卫
        long[] state = dfs(root);
        
        return Math.toIntExact(Math.min(state[1], state[2]));
    }
    
    // 深度搜索
    // 返回 long[3]: [placed, wait, covered]
    // placed:  当前节点自己放守卫
    // wait:    当前节点靠父节点照亮（自身不放）
    // covered: 当前节点被子节点照亮（自身不放）
    static long[] dfs(TreeNode node) {
        if (node == null) {
            return new long[]{INF, 0, 0};
        }
        long[] left = dfs(node.left);
        long[] right = dfs(node.right);
        
        long lPlaced = left[0], lWait = left[1], lCovered = left[2];
        long rPlaced = right[0], rWait = right[1], rCovered = right[2];
        
        // placed: 自己放守卫，子节点可以是任意状态
        long placed = node.val + Math.min(lPlaced, Math.min(lWait, lCovered)) + Math.min(rPlaced, Math.min(rWait, rCovered));
        
        // wait: 靠父节点照亮，自身不放守卫，所以不能照亮子节点
        // 子节点只能靠自己放(placed)或被孙子照亮(covered)，不能靠当前节点(wait)
        long wait = Math.min(lPlaced, lCovered) + Math.min(rPlaced, rCovered);
        
        // covered: 被子节点照亮，至少有一个子节点必须放守卫(placed)
        long baseL = Math.min(lPlaced, lCovered);
        long baseR = Math.min(rPlaced, rCovered);
        long covered = baseL + baseR;
        
        boolean leftChoseCovered = (baseL == lCovered);
        boolean rightChoseCovered = (baseR == rCovered);
        
        if (leftChoseCovered && rightChoseCovered) {
            // 两个子节点都没放守卫，强制选一个切换成 placed，选差价小的
            long diffL = lPlaced - lCovered;
            long diffR = rPlaced - rCovered;
            covered += Math.min(diffL, diffR);
        }
        
        return new long[]{placed, wait, covered};
    }
    
    static void main() {
        String s = "(5,1,10,2,8,#,3)";
        // output 4
        System.out.print(solve(s));
    }
    
    
    // 定义树结构
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        
        TreeNode(int val) {
            this.val = val;
        }
    }
}
