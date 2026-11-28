package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c9 {
    /*
    【9.比赛的冠亚季军】--【模拟计算】--3h--【26.7.29-1】能量对撞】
    题目描述
    有N（3 ≤ N < 10000）个运动员，他们的id为0到N-1,他们的实力由一组整数表示。他们之间进行比赛，需要决出冠亚军。
    比赛的规则是0号和1号比赛，2号和3号比赛，以此类推，每一轮，相邻的运动员进行比赛，获胜的进入下一轮；实力值大的获胜，实力值相等的情况，id小的情况下获胜；轮空的直接进入下一轮。
    
    输入描述
    输入一行N个数字代表N的运动员的实力值(0<=实力值<=10000000000)。
    
    输出描述
    输出冠亚季军的id，用空格隔开。
    */
    
    /**
     * 1 表示 idx1 胜，-1 表示 idx2 胜。实力相等时编号小的获胜。
     */
    public static int compare(int idx1, int idx2, long[] inputs) {
        long val1 = inputs[idx1];
        long val2 = inputs[idx2];
        if (val1 > val2) {
            return 1;
        }
        if (val2 > val1) {
            return -1;
        }
        return idx1 < idx2 ? 1 : -1;
    }
    
    /**
     *前四名决出胜负
     */
    public static void ko(List<Integer> win, List<Integer> lose, long[] inputs, StringBuilder sb) {
        int winNum1 = win.get(0);
        int winNum2 = win.get(1);
        
        int r = compare(winNum1, winNum2, inputs);
        if (r > 0) {
            sb.append(winNum1).append(" ").append(winNum2).append(" ");
        } else if (r < 0) {
            sb.append(winNum2).append(" ").append(winNum1).append(" ");
        } else {
            sb.append(Math.min(winNum1, winNum2)).append(" ").append(Math.max(winNum1, winNum2)).append(" ");
        }
        
        if (lose.size() == 1) {
            sb.append(lose.getFirst());
        } else {
            int loseNum1 = lose.get(0);
            int loseNum2 = lose.get(1);
            
            int r1 = compare(loseNum1, loseNum2, inputs);
            if (r1 > 0) {
                sb.append(loseNum1);
            } else if (r1 < 0) {
                sb.append(loseNum2);
            } else {
                sb.append(Math.min(loseNum1, loseNum2));
            }
        }
    }
    
    public static String solve(long[] inputs) {
        List<Integer> win = new ArrayList<>(); // 胜出的编号
        List<Integer> lose = new ArrayList<>(); // 失败的编号
        
        int i = 0; // 遍历编号
        int n = inputs.length;
        
        boolean isOdd = n % 2 == 0;
        
        // 当胜利里面有三个候选人时,且把所有都遍历过 退出循环 win.size() == 3 && i == n-1
        while (isOdd ? i < n - 1 : i < n) {
            long cur = inputs[i];
            long next;
            
            // 比赛过程
            if (i + 1 < n) {
                // i与i+1比较
                next = inputs[i + 1];
                // 相等时 编号小的胜出
                if (cur >= next) {
                    win.add(i);
                    lose.add(i + 1);
                } else {
                    win.add(i + 1);
                    lose.add(i);
                }
                // 比较完 i+2 进入下一对
                i += 2;
            } else {
                // i已经是最后一个 直接胜出
                win.add(i);
                i++;
            }
        }
        
        
        StringBuilder sb = new StringBuilder();
        // 第一轮结束之后 i == n-1 或者 i+1 == n-1的时候 看win的数量
        if (win.size() < 3) {
            ko(win, lose, inputs, sb);
        } else {
            // win.size() >=3 前三名只在win中 原lose舍弃
            while (win.size() > 2) {
                List<Integer> tempWin = new ArrayList<>();
                List<Integer> tempLose = new ArrayList<>();
                
                for (int k = 0; k < win.size(); k += 2) {
                    if (k + 1 >= win.size()) {
                        tempWin.add(win.get(k));
                        continue;
                    }
                    
                    // 比赛过程
                    int a = win.get(k);
                    int b = win.get(k + 1);
                    if (compare(a, b, inputs) > 0) {
                        tempWin.add(a);
                        tempLose.add(b);
                        continue;
                    }
                    tempWin.add(b);
                    tempLose.add(a);
                }
                
                win = new ArrayList<>(tempWin);
                lose = new ArrayList<>(tempLose);
            }
            ko(win, lose, inputs, sb);
        }
        
        return sb.toString();
    }
    
    static void main() {
        long[] i0 = {2, 4, 6};
        // 2 1 0
        System.out.println(solve(i0));
        
        long[] i = {2, 4, 6, 6, 8, 10, 7};
        // 5 2 6
        System.out.println(solve(i));
        
        long[] i1 = {2, 3, 4, 5};
        // 3 1 2
        System.out.println(solve(i1));
        
        long[] i2 = {5, 5, 5, 5};
        // 0 2 1
        System.out.println(solve(i2));
        
        long[] i3 = {1, 2, 3, 4};
        // 3 1 2
        System.out.println(solve(i3));
        
        long[] i4 = {2, 4, 6, 6, 8, 10, 7, 7};
        // 5 2 6
        System.out.println(solve(i4));
        
        // 6 8 0
        long[] i5 = {9, 1, 8, 2, 7, 3, 100, 0, 6};
        System.out.println(solve(i5));
    }
}
