package com.huawei;

import java.util.HashMap;

public class c29_ysy {
/*
    【29.完美走位】--【滑动窗口】--54min--用双重for+剪枝
    题目描述
    在第一人称射击游戏中，玩家通过键盘的A、S、D、W四个按键控制游戏人物分别向左、向后、向右、向前进行移动，从而完成走位。
    假设玩家每按动一次键盘，游戏任务会向某个方向移动一步，如果玩家在操作一定次数的键盘并且各个方向的步数相同时，此时游戏任务必定会回到原点，则称此次走位为完美走位。
    现给定玩家的走位（例如：ASDA），请通过更换其中一段连续走位的方式使得原走位能够变成一个完美走位。其中待更换的连续走位可以是相同长度的任何走位。
    请返回待更换的连续走位的最小可能长度。
    如果原走位本身是一个完美走位，则返回0。
    
    输入描述
    输入为由键盘字母表示的走位s，例如：ASDA
    
    说明：
        1、走位长度 1 ≤ s.length ≤ 100000（也就是长度不一定是偶数）
        2、s.length 是 4 的倍数
        3、s中只含有’A’, ‘S’, ‘D’, ‘W’ 四种字符
        
    输出描述
    输出为待更换的连续走位的最小可能长度。
    */
    
    public static int solve(String input) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("W", 0);
        map.put("S", 0);
        map.put("A", 0);
        map.put("D", 0);
        
        char[] cc = input.toCharArray();
        for (char c : cc) {
            map.merge(String.valueOf(c), 1, Integer::sum);
        }
        int w = map.get("W");
        int s = map.get("S");
        int a = map.get("A");
        int d = map.get("D");
        
        // 每个方向需要走avg步数
        int avg = (w + s + a + d) / 4;
        if (w == avg && a == avg && s == avg && d == avg) {
            // 本身就是完美走位
            return 0;
        }
        
        // 目前需要窗口内包含的字符数
        // 窗口内至少要覆盖的「多余」字符数
        HashMap<Character, Integer> need = new HashMap<>();
        for (char c : new char[]{'W', 'S', 'A', 'D'}) {
            int excess = map.get(String.valueOf(c)) - avg;
            if (excess > 0) {
                need.put(c, excess);
            }
        }
        
        // 用于变量还原
        HashMap<Character, Integer> rowNeed = new HashMap<>(need);
        
        // System.out.println("---map" + map);
        // System.out.println("---need" + need);
        
        int needType = need.size(); // 需要的字符类型
        int res = Integer.MAX_VALUE; // 维护一个最小长度
        
        // 双重for
        for (int i = 0; i < cc.length; i++) {
            int matchType = 0; // 当前匹配的字符类型
            need = new HashMap<>(rowNeed); // 还原need
            
            for (int j = i; j < cc.length; j++) {
                // 如果当前找到的值比之前还大 剪枝j不看了
                if (j - i + 1 >= res) {
                    break;
                }
                char jc = cc[j]; // 结束字符
                if (need.containsKey(jc)) {
                    // 遇到目标字符 need对应值-- 减到0 matchType+1
                    int curCount = need.merge(jc, -1, Integer::sum);
                    if (curCount == 0) {
                        matchType++;
                    }
                    // 如果目标字符已经匹配完毕 j不用再继续找了
                    if (matchType == needType) {
                        int curLen = j - i + 1;
                        res = Math.min(res, curLen);// 维护更新res
                        break;
                    }
                }
                
            }
            
        }
        return res;
    }
    
    public static void main(String[] args) {
        String s8 = "ADDDDDDD";
        // 5（D 多 5，最短盖住 5 个 D）
        System.out.println("ans : " + solve(s8));
        
        String s9 = "WASDAAAA";
        // 3（A 多 3）
        System.out.println("ans : " + solve(s9));
        
        String s2 = "AAAA";
        // 3
        System.out.println("ans : " + solve(s2));
        
        String s5 = "WWWW";
        // 3
        System.out.println("ans : " + solve(s5));
        
        String s1 = "WASDAASD";
        // 1  仅 A 多 1，换一个 A 即可
        System.out.println("ans : " + solve(s1));
        
        String s3 = "ASDW";
        // 0
        System.out.println("ans : " + solve(s3));

        String s4 = "AADDSSWW";
        // 0
        System.out.println("ans : " + solve(s4));
        
        String s6 = "AWAAWSSS";
        // 3（A、S 各多 1，最短同时覆盖两者的窗口长度为 3，不是 2）
        System.out.println("ans : " + solve(s6));

        String s7 = "ASASASAS";
        // 4
        System.out.println("ans : " + solve(s7));
        
        String s8b = "AAAAADDD";
        // 4（A 多 3、D 多 1）
        System.out.println("ans : " + solve(s8b));

        String s10 = "WWWWSSSS";
        // 4
        System.out.println("ans : " + solve(s10));

        String s11 = "AAAASSSSDDDD";
        // 6
        System.out.println("ans : " + solve(s11));
    }
}
