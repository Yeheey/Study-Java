package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class c13 {
    /*
    【13.斗地主之顺子】--【模拟计算+字符串】--70min--【8.最长的顺子】
    题目描述
    在斗地主扑克牌游戏中， 扑克牌由小到大的顺序为：3,4,5,6,7,8,9,10,J,Q,K,A,2，玩家可以出的扑克牌阵型有：单张、对子、顺子、飞机、炸弹等。
    其中顺子的出牌规则为：由至少5张由小到大连续递增的扑克牌组成，且不能包含2。
    例如：{3,4,5,6,7}、{3,4,5,6,7,8,9,10,J,Q,K,A}都是有效的顺子；而{J,Q,K,A,2}、 {2,3,4,5,6}、{3,4,5,6}、{3,4,5,6,8}等都不是顺子。
    给定一个包含13张牌的数组，如果有满足出牌规则的顺子，请输出顺子。
    如果存在多个顺子，请每行输出一个顺子，且需要按顺子的第一张牌的大小（必须从小到大）依次输出。
    如果没有满足出牌规则的顺子，请输出No。
    
    输入描述
    13张任意顺序的扑克牌，每张扑克牌数字用空格隔开，每张扑克牌的数字都是合法的，并且不包括大小王：
    2 9 J 2 3 4 K A 7 9 A 5 6
    不需要考虑输入为异常字符的情况
    
    输出描述
    组成的顺子，每张扑克牌数字用空格隔开：3 4 5 6 7
*/
    // 辅助函数 J-10 Q-11 K-12 A-13
    static String format(String s) {
        switch (s) {
            case "J" -> {
                return "11";
            }
            case "Q" -> {
                return "12";
            }
            case "K" -> {
                return "13";
            }
            case "A" -> {
                return "14";
            }
            default -> {
                return s;
            }
        }
        
    }
    
    static String format1(String s) {
        switch (s) {
            case "11" -> {
                return "J";
            }
            case "12" -> {
                return "Q";
            }
            case "13" -> {
                return "K";
            }
            case "14" -> {
                return "A";
            }
            default -> {
                return s;
            }
        }
        
    }
    
    public static List<String> solve(String inputs) {
        String[] arr = inputs.split(" ");
        List<Integer> list = new ArrayList<>();
        
        for (final String s : arr) {
            list.add(Integer.parseInt(format(s)));
        }
        TreeSet<Integer> set = new TreeSet<>(list);
        set.remove(2);
        list = new ArrayList<>(set);
        
        List<String> res = new ArrayList<>();
        
        StringBuilder tmp = new StringBuilder();
        String t = format1(String.valueOf(set.getFirst()));
        tmp.append(t); // 先存第一个
        
        
        for (int i = 1; i < list.size(); i++) {
            
            int cur = list.get(i);
            int last = list.get(i - 1);
            
            if (cur - last == 1) {
                String cur1 = format1(String.valueOf(cur));
                tmp.append(" ").append(cur1);
                int len = tmp.toString().split(" ").length;
                if (i == list.size() - 1 && len >= 5) {
                    res.add(tmp.toString());
                }
            } else {
                // 不连续了
                // 如果前面的tmp大于等于5 是合法顺子 存到res里
                int len = tmp.toString().split(" ").length;
                
                if (len >= 5) {
                    res.add(tmp.toString());
                }
                tmp = new StringBuilder();
                tmp.append(cur);
            }
        }
        
        if (res.isEmpty()) {
            res.add("No");
        }
        return res;
    }
    
    static void main() {
        String s1 = "2 9 J 2 3 4 K A 7 9 A 5 6";
        // 3 4 5 6 7
        System.out.println(solve(s1));
        
        String s2 = "2 9 J 10 3 4 K A 7 Q A 5 6";
        // 3 4 5 6 7
        // 9 10 J Q K A
        System.out.println(solve(s2));
        
        String s3 = "2 9 9 9 3 4 K A 10 Q A 5 6";
        // No
        System.out.println(solve(s3));
    }
}
