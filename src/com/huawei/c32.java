package com.huawei;

import java.util.Arrays;
import java.util.HashSet;

public class c32 {
    /*
    【精准核酸检测】
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
    
    static boolean[] visited;
    static HashSet<Integer> set;
    
    static void dfs(int index, int n, int[][] matrix) {
        int[] tmp = matrix[index];// 邻接表
        set.add(index); // index 是病人下标编号
        
        for (int i = 0; i < n; i++) {
            if (set.contains(i)) continue;
            if (tmp[i]==0) continue;
            if (tmp[i] == 1 && i == index) continue;
            dfs(i, n, matrix);
        }
        return;
    }
    
    static int solve(int n, int[] sick, int[][] matrix) {
        visited = new boolean[n];
        set = new HashSet<>();
        
        for (final int s : sick) {
            dfs(s, n, matrix);
        }
        
        int sickNum = sick.length;
        int checkNum = set.size();
        
        int check = 0;
        if (sickNum > 0) {
            check = checkNum - sickNum;
        }
        
        return check;
    }
    
    static void main() {
        int n = 5;
        int[] sick = {1, 2};
        int[][] matrix = {{1, 1, 0, 1, 0}, {1, 1, 0, 0, 0}, {0, 0, 1, 0, 1}, {1, 0, 0, 1, 0}, {0, 0, 1, 0, 1}};
        // 3
        System.out.println("=====ans:"+solve(n, sick, matrix));
        
        int n1 = 3;
        int[] sick1 = {0};
        int[][] matrix1 = {{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        // 0
        System.out.println("=====ans:"+solve(n1, sick1, matrix1));

        int n2 = 3;
        int[] sick2 = {0};
        int[][] matrix2 = {{1, 0, 0}, {0, 1, 1}, {0, 1, 1}};
        // 0
        System.out.println("=====ans:"+solve(n2, sick2, matrix2));
    }
}