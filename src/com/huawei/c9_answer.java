package com.huawei;

import java.util.ArrayList;

public class c9_answer {
    
    public static String solve(long[] inputs) {
        // 保存当前轮次的选手ID
        ArrayList<Integer> players = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            players.add(i);
        }
        
        // 进行循环比赛，直到剩余两个选手
        ArrayList<Integer> losers = new ArrayList<>(); // 存储每轮的失败者
        while (players.size() > 2) {
            ArrayList<Integer> winners = new ArrayList<>();
            losers.clear();
            
            // 每一轮比赛 两两比较
            for (int i = 0; i < players.size() - 1; i += 2) {
                int p1 = players.get(i);
                int p2 = players.get(i + 1);
                
                if (inputs[p1] > inputs[p2] || (inputs[p1] == inputs[p2] && p1 < p2)) {
                    winners.add(p1);
                    losers.add(p2);
                } else {
                    winners.add(p2);
                    losers.add(p1);
                }
            }
            
            // 如果有轮空的选手，直接进入下一轮
            if (players.size() % 2 != 0) {
                winners.add(players.getLast());
            }
            
            players = winners; // 更新为下一轮的选手
        }
        
        // 冠军和亚军
        int first, second;
        if (inputs[players.get(0)] > inputs[players.get(1)] || (inputs[players.get(0)] == inputs[players.get(1)] && players.get(0) < players.get(1))) {
            first = players.get(0);
            second = players.get(1);
        } else {
            first = players.get(1);
            second = players.get(0);
        }
        
        // 从最后一轮的失败者中找到季军
        int third = losers.getFirst();
        for (int i = 1; i < losers.size(); i++) {
            int idx = losers.get(i);
            if (inputs[idx] > inputs[third] || (inputs[idx] == inputs[third] && idx < third)) {
                third = idx;
            }
        }
        
        // 输出冠亚季军ID
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
