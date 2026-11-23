package com.huawei;

import java.util.HashMap;

public class c18 {
    /*
    【18.恢复数字序列】--【模拟计算+暴力】--68min
    题目描述
    对于一个连续正整数组成的序列，可以将其拼接成一个字符串，再将字符串里的部分字符打乱顺序。
    如序列8 9 10 11 12，拼接成的字符串为89101112，打乱一部分字符后得到90811211，原来的正整数10就被拆成了0和1。
    现给定一个按如上规则得到的打乱字符的字符串，请将其还原成连续正整数序列，并输出序列中最小的数字。
    
    输入描述
    输入一行，为打乱字符的字符串和正整数序列的长度，两者间用空格分隔，字符串长度不超过200，正整数不超过1000；
    保证输入可以还原成唯一序列。
    
    输出描述
    输出一个数字，为序列中最小的数字。
*/
    
    /**
     * 统计字符串每个字符的频次
     */
    static HashMap<Character, Integer> count(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            map.merge(c, 1, Integer::sum);
        }
        return map;
    }
    
    public static int solve(String inputs) {
        // 打乱的数字字符串
        String s = inputs.split(" ")[0];
        // 字符串长度
        int len = s.length();
        // 一共几位数字
        int n = Integer.parseInt(inputs.split(" ")[1]);
        
        // 统计频次
        HashMap<Character, Integer> map = count(s);
        
        int x = len / n; // 起始是个x位数
        
        int res = 0;
        int init;
        if (x == 1) {
            init = 1;
        } else if (x == 2) {
            init = 10;
        } else if (x == 3) {
            init = 100;
        } else {
            init = 1000;
        }
        
        for (int i = init; i <= 1000; i++) {
            StringBuilder temp = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (i + j > 1000) {
                    continue;
                }
                temp.append(i + j);
            }
            HashMap<Character, Integer> tmpMap = count(temp.toString());
            if (tmpMap.equals(map)) {
                res = i;
                break;
            }
        }
        
        return res;
    }
    
    static void main() {
        String s1 = "19801211 5";
        // 8 - 8 9 10 11 12
        System.out.println(solve(s1));
        
        String s2 = "432111111111 4";
        // 111 - 111 112 113 114
        System.out.println(solve(s2));
        
        String s3 = "1000 1";
        // 1000
        System.out.println(solve(s3));
    }
}
