package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c9_ysy {
    /*
    【9.比赛的冠亚季军】--【模拟计算】--3h
    题目描述
    有N（3 ≤ N < 10000）个运动员，他们的id为0到N-1,他们的实力由一组整数表示。他们之间进行比赛，需要决出冠亚军。
    比赛的规则是0号和1号比赛，2号和3号比赛，以此类推，每一轮，相邻的运动员进行比赛，获胜的进入下一轮；实力值大的获胜，实力值相等的情况，id小的情况下获胜；轮空的直接进入下一轮。
    
    输入描述
    输入一行N个数字代表N的运动员的实力值(0<=实力值<=10000000000)。
    
    输出描述
    输出冠亚季军的id，用空格隔开。
    */
    
    
    public static String solve(long[] inputs) {
        List<Integer> win = new ArrayList<>(); // 胜出的编号
        List<Integer> lose = new ArrayList<>(); // 失败的编号
        List<Integer> players = new ArrayList<>(); // 所有选手的编号
        
        int n = inputs.length;
        for (int k = 0; k < n; k++) {
            players.add(k);
        }
        
        // 一直比到冠军胜出 win里只有一个人 剩下两个在lose后两位选出胜负
        while (players.size() > 2) {
            win = new ArrayList<>();
            lose = new ArrayList<>();
            
            // 两两比较
            for (int i = 0; i < players.size() - 1; i += 2) {
                if (i + 1 >= players.size()) {
                    continue;
                }
                
                int p1 = players.get(i);
                int p2 = players.get(i + 1);
                long val1 = inputs[p1];
                long val2 = inputs[p2];
                
                if (val1 > val2 || (val1 == val2 && p1 < p2)) {
                    win.add(p1);
                    lose.add(p2);
                } else {
                    win.add(p2);
                    lose.add(p1);
                }
            }
            
            // 当游戏玩家为奇数时 会有轮空 最后一位选手直接晋级
            if (players.size() % 2 != 0) {
                win.add(players.getLast());
            }
            
            // 更新新的player为上一次的win
            players = win;
        }
        
        // 整理答案
        String first = "";
        String second = "";
        String third = "";
        
        int w1 = win.get(0);
        int w2 = win.get(1);
        long wv1 = inputs[w1];
        long wv2 = inputs[w2];
        if (wv1 > wv2 || (wv1 == wv2 && w1 < w2)) {
            first = String.valueOf(w1);
            second = String.valueOf(w2);
        } else {
            first = String.valueOf(w2);
            second = String.valueOf(w1);
        }
        
        int l1 = lose.get(0);
        long lv1 = inputs[l1];
        if (lose.size() == 1) {
            third = String.valueOf(l1);
        } else {
            int l2 = lose.get(1);
            long lv2 = inputs[l2];
            
            if (lv1 > lv2 || (lv1 == lv2 && l1 < l2)) {
                third = String.valueOf(l1);
            } else {
                third = String.valueOf(l2);
            }
        }
        
        
        return first + " " + second + " " + third;
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
