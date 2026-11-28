package com.huawei;

public class c32_ysy {
    /*
    【32.精准核酸检测】--【DFS+邻接表】--二刷40min
    题目描述
    为了达到新冠疫情精准防控的需要，为了避免全员核酸检测带来的浪费，需要精准圈定可能被感染的人群。
    现在根据传染病流调以及大数据分析，得到了每个人之间在时间、空间上是否存在轨迹的交叉。
    现在给定一组确诊人员编号(X1,X2,X3....Xn)，在所有人当中，找出哪些人需要进行核酸检测，输出需要进行核酸检测的人数。
    注意：确诊病例自身不需要再做核酸检测。需要进行核酸检测的人，是病毒传播链条上的所有人员，即有可能通过确诊病例所能传播到的所有人。
    例如：A是确诊病例，A和B有接触、B和C有接触、C和D有接触、D和E有接触。那么B、C、D、E都是需要进行核酸检测的人。
    
    输入描述
    第一行为总人数N
    第二行为确诊病例人员编号（确诊病例人员数量<N），用逗号隔开
    接下来N行，每一行有N个数字，用逗号隔开，其中第i行的第j个数字代表编号i是否与编号j接触过。0表示没有接触，1表示有接触。
    备注：人员编号从0开始0 < N < 100
    
    输出描述
    输出需要做核酸检测的人数
    */
    static boolean[] visited; // 访问过的人
    
    /**
     深度搜索 idx是编号
     */
    static void dfs(int idx, int[][] matrix, int n) {
        int[] edges = matrix[idx]; // 邻接表
        for (int i = 0; i < edges.length; i++) {
            if (!visited[i] && edges[i] == 1) {
                visited[i] = true;
                dfs(i, matrix, n);
            }
        }
    }
    
    /**
     * n-个人 编号为0-n-1
     * sick-已经生病的编号
     * matrix-两两接触的人 1接触过 0 没接触
     */
    static int solve(int n, int[] sick, int[][] matrix) {
        // 特殊情况
        if (n == 0 || matrix == null || sick == null || sick.length == 0) {
            return 0;
        }
        // 初始化
        visited = new boolean[n]; // 索引0 置空 下标为编号
        for (int i : sick) {
            visited[i] = true; // 先把生病的置为true
            // 从生病的人编号出发 深度搜索
            int[] edges = matrix[i]; // 邻接表
            for (int j = 0; j < edges.length; j++) {
                // 如果生病的人 还接触过 访问置true
                if (i != j && edges[j] == 1) {
                    visited[j] = true; // 直接接触
                    // 对于生病接触过的人 进行深度搜索 探索间接接触
                    dfs(j, matrix, n);
                }
            }
            
        }
        
        int sickNum = sick.length;
        int contactNum = 0;
        for (final boolean vis : visited) {
            if (vis) {
                contactNum++;
            }
        }
        
        return contactNum - sickNum;
    }
    
    static void main() {
        int n = 5;
        int[] sick = {1, 2};
        int[][] matrix = {{1, 1, 0, 1, 0}, {1, 1, 0, 0, 0}, {0, 0, 1, 0, 1}, {1, 0, 0, 1, 0}, {0, 0, 1, 0, 1}};
        // 3
        System.out.println("---ans:" + solve(n, sick, matrix));
        
        int n1 = 3;
        int[] sick1 = {0};
        int[][] matrix1 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        // 0
        System.out.println("---ans:" + solve(n1, sick1, matrix1));
        
        int n2 = 3;
        int[] sick2 = {0};
        int[][] matrix2 = {{1, 0, 0}, {0, 1, 1}, {0, 1, 1}};
        // 0
        System.out.println("---ans:" + solve(n2, sick2, matrix2));
    }
}