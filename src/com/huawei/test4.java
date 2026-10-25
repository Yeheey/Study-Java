package com.huawei;

import java.util.ArrayList;

public class test4 {
    /*
        题目描述
        请在一个仅由数字组成的字符串中，找出只由6或8组成的最长的连续子串。
        输入描述
        输入一个仅由数字组成的字符串，字符串长度小于256。
        输出描述
        请输出所有满足要求的最长子串，去重后按照字典序排序输出;当字符串为空或没有符合要求的子串时，输出空字符串。
        */
    static ArrayList<String> luckyNum(String num) {
        ArrayList<String> res = new ArrayList<>();
        int n = num.length();
        int maxLen = 0;
        
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) != '6' && num.charAt(i) != '8') {
                continue;
            }
            int j = i + 1;
            
            StringBuilder sb = new StringBuilder();
            while (j < n && (num.charAt(j) == '6' || num.charAt(j) == '8')) {
                int tempLen = j - i + 1;
                if (tempLen > maxLen) {
                    res.clear();
                    res.add(num.substring(i, j + 1));
                    maxLen = tempLen;
                } else if (tempLen == maxLen) {
                    res.add(num.substring(i, j + 1));
                }
                j++;
            }
        }
        
        return res;
    }
    
    static void main() {
        String a = "88612668";
        System.out.print(luckyNum(a)); // ["668","886"]
    }
}
