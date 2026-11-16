package com.huawei;

import java.util.ArrayList;
import java.util.TreeSet;

public class v260902_1_ysy {
    /*
    【26.9.2-1】查找幸运数--【字符串+去重排序】--14min
    题目描述
    请在一个仅由数字组成的字符串中，找出只由6或8组成的最长的连续子串。
    
    输入描述
    输入一个仅由数字组成的字符串，字符串长度小于256。
    
    输出描述
    请输出所有满足要求的最长子串，去重后按照字典序排序输出;
    当字符串为空或没有符合要求的子串时，输出空字符串。
    */
    
    static ArrayList<String> luckyNum(String s) {
        ArrayList<String> res = new ArrayList<>();
        
        int maxLen = 0;
        int curLen;
        
        int i = 0;
        
        StringBuilder sb = new StringBuilder();
        while (i < s.length()) {
            if (s.charAt(i) == '6' || s.charAt(i) == '8') {
                sb.append(s.charAt(i));
                curLen = sb.length();
                if (curLen >= maxLen) {
                    if (curLen > maxLen) {
                        res.clear();
                    }
                    res.add(sb.toString());
                    maxLen = curLen;
                }
            } else {
                sb = new StringBuilder();
                curLen = 0;
            }
            
            i++;
        }
        
        TreeSet<String> set = new TreeSet<>(res);
        
        return new ArrayList<>(set);
    }
    
    static void main() {
        System.out.println(luckyNum("88612668")); // ["668","886"]
        System.out.println(luckyNum("123668812388661236688")); // 6688 8866
    }
}
