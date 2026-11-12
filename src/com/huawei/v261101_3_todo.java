package com.huawei;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class v261101_3_todo {
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
    
    static List<Integer> resList = new ArrayList<>();
    static int res = 0;
    static Stack<int[]> stack = new Stack<>();
    
    static List<Integer> solve(int n, int alpha, int beta, int s, int[][] matrix) {
        if (matrix.length <= 0) return resList;
        if (matrix.length == 1) {
            resList.add(matrix[0][0]);
            return resList;
        }
        stack.push(matrix[0]); // root进栈
        int cur = 0;
        List<Integer> curList = new ArrayList<>();
        while (!stack.empty()) {
        
        
        }
        
        
        return resList;
    }
    
    public static void main(String[] args) {
        int n = 7;
        int alpha = 3;
        int beta = 1;
        int s = 800;
        int[][] matrix = {{1, 2000, 2, 3}, {2, 1500, 4, 5}, {3, 2400, 6, 7}, {4, 900, -1, -1}, {5, 2300, -1, -1}, {6, 2100, -1, -1}, {7, 2800, -1, -1}};
        // [1,3,6]
        System.out.println(solve(n, alpha, beta, s, matrix));
    }
}
