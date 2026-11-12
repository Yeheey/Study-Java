package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v261101_3_answer {
    /*
    【26.11.1-3】山区救援路线规划
    题目描述
    某山区建立了一套应急救援通道网络。由于地形限制，从指挥中心（根节点）出发，每条通道在岔路口最多分为两条分支，最终抵达不同的救援集结点（叶子节点）。
    整个网络形成一棵二叉树，每个节点记录其海拔高度。救援队需要从指挥中心出发，选择一条到达救援集结点的路线，同时考虑体力消耗和海拔安全要求。
    相邻两节点之间的体力消耗定义为：
    上坡时为 alpha×海拔升高值，下坡时为 beta × 海拔降低值，海拔不变时为0。整条路线的体力消耗是每段消耗之和。
    路线上的最高海拔与最低海拔之差称为海拔跨度，必须不超过安全阈值 S。该要求检查整条路线，包括根节点和叶子节点。

    请在所有安全的根到叶路线中，选择总体力消耗最小的路线。如果多条路线消耗相同，选择节点编号序列字典序最小的路线：
    从前向后比较编号，首次不同的位置编号较小者优先，编号按整数大小比较。
    只有 left 和 right 同时为 -1 的节点才是叶子节点，只有一个子节点的节点不能作为路线终点。

    输入描述
    输入仅一行，格式如下：n,alpha,beta,S,[[id,value,left,right],...]
    前四个整数依次表示节点总数、上坡体力消耗权重、下坡体力消耗权重和安全海拔跨度阈值，后面是包含 n 行节点信息的二维数组。
    每个节点包含四个整数：id 为节点编号，value 为海拔，left、right 分别为左右子节点编号，不存在的子节点使用 -1 表示。

    数据范围及保证：
    3 ≤ n ≤ 10000，1 ≤ alpha、beta ≤ 10，1 ≤ S ≤ 4000。100 ≤ value ≤ 4000，海拔单位为米。
    节点按 id 升序给出，即 nodes[i][0] = i+1，编号为 1 到 n，1 号节点为根。
    树结构合法，每个非根节点恰有一个父节点，不存在环，根节点可以到达所有节点。

    输出描述
    输出一行节点编号数组，表示选定的从根到叶路线。元素之间用英文逗号分隔，不添加空格。例如 [1,3,6]。
    如果不存在安全路线，输出空数组 []。
    整理说明：原截图未完整显示的节点字段定义、海拔范围及无安全路线的输出形式，按所给改写说明补全；无安全路线统一输出 []。
    */
    
    /**
     * 核心求解函数：迭代DFS（数组模拟栈，节省空间，适合n=10000大数据）
     *
     * @param alpha 上坡系数
     * @param beta  下坡系数
     * @param span  安全海拔跨度阈值S
     * @param nodes 节点二维数组，nodes[i]对应id=i+1的节点：[id,海拔,左孩子,右孩子]
     * @return 最优路径List，无合法路径返回空list
     */
    public static List<Integer> solve(int alpha, int beta, int span, int[][] nodes) {
        int n = nodes.length;
        
        // parent[child] = u：记录每个节点的父节点编号，最后回溯路径用
        int[] parent = new int[n + 1];
        // lowest[u]：从根走到u这条路径上的最低海拔
        int[] lowest = new int[n + 1];
        // highest[u]：从根走到u这条路径上的最高海拔
        int[] highest = new int[n + 1];
        // cost[u]：从根走到u这条路径累计体力消耗
        long[] cost = new long[n + 1];
        
        // 数组模拟栈，代替Stack对象，速度更快；stack存待遍历节点id
        int[] stack = new int[n];
        int size = 0; // 栈当前有效元素个数，size=0代表栈空
        
        // 根节点是1，先入栈
        stack[size++] = 1;
        // 根节点路径最大、最小海拔就是自身海拔
        lowest[1] = highest[1] = nodes[0][1];
        
        long bestCost = Long.MAX_VALUE; // 记录当前找到的最小消耗，初始无穷大
        int bestLeaf = -1;              // 记录最优路线对应的叶子节点id，-1代表没找到合法叶子
        
        while (size > 0) {
            int u = stack[--size]; // 出栈：size先减，再取元素，栈顶弹出
            
            // 剪枝：这条路径的海拔跨度已经超限，它的所有子节点路径跨度只会更大，直接跳过不往下走
            if (highest[u] - lowest[u] > span) continue;
            
            // 当前节点u的海拔、左右孩子id
            int height = nodes[u - 1][1];
            int left = nodes[u - 1][2], right = nodes[u - 1][3];
            
            // 判断叶子节点：左右孩子都是-1，是一条完整根->叶路径终点
            if (left == -1 && right == -1) {
                // 消耗比之前最优更小，更新最优记录
                if (cost[u] < bestCost) {
                    bestCost = cost[u];
                    bestLeaf = u;
                }
                // 消耗相等时不更新：先访问到的路径字典序更小，保留先找到的
                continue;
            }
            
            // 重点：先压大编号子节点、后压小编号子节点
            // 栈后进先出，保证小编号子节点先被弹出遍历，天然满足字典序优先
            int first = Math.max(left, right);
            int second = Math.min(left, right);
            for (int child : new int[]{first, second}) {
                if (child == -1) continue; // 不存在的子节点跳过
                
                int childHeight = nodes[child - 1][1];
                parent[child] = u; // 记录child的父节点是u
                
                // 继承父路径的最大最小海拔，更新到当前child路径
                lowest[child] = Math.min(lowest[u], childHeight);
                highest[child] = Math.max(highest[u], childHeight);
                
                int delta = childHeight - height;
                if (delta >= 0) {
                    // 上坡：alpha * 升高值
                    cost[child] = cost[u] + (long) alpha * delta;
                } else {
                    // 下坡：beta * 下降值
                    cost[child] = cost[u] + (long) beta * -delta;
                }
                stack[size++] = child; // 子节点入栈
            }
        }
        
        // 回溯构造路径：从最优叶子反向往根走，得到逆序路径
        List<Integer> answer = new ArrayList<>();
        while (bestLeaf != -1 && bestLeaf != 0) {
            answer.add(bestLeaf);
            bestLeaf = parent[bestLeaf];
        }
        // 反转，变成根 -> 叶子的顺序
        Collections.reverse(answer);
        return answer;
    }
    
    public static void main(String[] args) {
        int alpha = 3;
        int beta = 1;
        int s = 800;
        int[][] matrix = {{1, 2000, 2, 3}, {2, 1500, 4, 5}, {3, 2400, 6, 7}, {4, 900, -1, -1}, {5, 2300, -1, -1}, {6, 2100, -1, -1}, {7, 2800, -1, -1}};
        // 预期输出：[1,3,6]
        System.out.println(solve(alpha, beta, s, matrix));
    }
}
