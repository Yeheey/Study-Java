package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class test11 {
    /*
     11.【26.8.30-2】字符串回文判断[AI]
     题目描述：
     给定一个仅由小写英文字母组成的字符串s，请判断它是否可以在删除一个字符之后，变成一个回文串。
     回文串的定义是:从左到右读和从右到左读完全相同。
     如果可以，输出一个数组，数组由可删除字符的索引值构成;否则输出一个空数组。
     */
    
    // 辅助函数：判断字符串 s 中 [l, r] 区间的子串是否为回文
    static boolean isPalindrome(String s, int l, int r) {
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                return false;
            }
            l++;
            r--;
        }
        return true;
    }
    
    static List<Integer> solve(String s) {
        List<Integer> res = new ArrayList<>();
        int n = s.length();
        int left = 0;
        int right = n - 1;
        
        // 双指针从两端向中间遍历，找到第一个字符不相等的位置
        while (left < right && s.charAt(left) == s.charAt(right)) {
            left++;
            right--;
        }
        
        // 情况1：原字符串本身就是回文
        if (left >= right) {
            // 找到中间连续相同字符的区间：删除区间内任意一个字符后，字符串仍为回文
            char midChar = s.charAt(n / 2);
            int l = n / 2;
            int r = n / 2;
            // 向左扩展连续相同字符
            while (l >= 0 && s.charAt(l) == midChar) {
                l--;
            }
            // 向右扩展连续相同字符
            while (r < n && s.charAt(r) == midChar) {
                r++;
            }
            // 将区间内所有索引加入结果
            for (int i = l + 1; i <= r - 1; i++) {
                res.add(i);
            }
            return res;
        }
        
        // 情况2：遇到不相等字符，分别尝试删除左/右指针字符，检查剩余子串是否为回文
        if (isPalindrome(s, left + 1, right)) {
            res.add(left);
        }
        if (isPalindrome(s, left, right - 1)) {
            res.add(right);
        }
        
        return res;
    }
    
    public static void main(String[] args) {
        // 题目示例
        String s = "abca";
        System.out.println(solve(s)); // 输出 [1, 2]
        
        // 额外测试用例
        System.out.println(solve("aba"));      // 输出 [1]
        System.out.println(solve("aaa"));      // 输出 [0, 1, 2]
        System.out.println(solve("aabbbaa"));  // 输出 [2, 3, 4]
        System.out.println(solve("abc"));      // 输出 []
        System.out.println(solve("ab"));       // 输出 [0, 1]
        System.out.println(solve("abcdabcdcba"));       // 输出 [0, 1]
    }
}
