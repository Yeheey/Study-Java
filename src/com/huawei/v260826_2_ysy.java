package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v260826_2_ysy {
    /*
    【26.8.30-2】字符串回文判断--【字符串+双指针】
    题目描述：
    给定一个仅由小写英文字母组成的字符串s，请判断它是否可以在删除一个字符之后，变成一个回文串。
    回文串的定义是:从左到右读和从右到左读完全相同。
    如果可以，输出一个数组，数组由可删除字符的索引值构成;否则输出一个空数组。

    输入描述
    输入一个字符串 s，2 ≤ s.length ≤ 10^5
    
    输出描述
    输出一个数组，内容为：N 种删除方法的字符索引值。如输入为：abca，则输出为：[1, 2]，代表可以有两种删除方式：[aba, aca]。
       1：删除索引值为 1 的字符，即删除字符 b，则变为：aca，属于回文
       2：删除索引值为 2 的字符，即删除字符 c，则变为：aba，属于回文
    */
    
    // 方法2：双指针--50min--不易想到本身就是回文的情况
    static boolean isPalindrome(String s, int l, int r) {
        boolean isCheck = true;
        while (l < r) {
            if (s.charAt(l) != s.charAt(r)) {
                isCheck = false;
                break;
            }
            l++;
            r--;
        }
        return isCheck;
    }
    
    static List<Integer> centerIndices(String s) {
        int n = s.length();
        int mid = n % 2 == 0 ? n / 2 - 1 : (n - 1) / 2;
        char c = s.charAt(mid);
        
        int count = 0;
        for (int i = mid; i >= 0; i--) {
            if (s.charAt(i) != c) {
                break;
            }
            count++;
        }
        
        // 偶数：[mid - count + 1, mid + count]
        // 奇数：[mid - count + 1, mid + count - 1]
        List<Integer> res = new ArrayList<>();
        int left = mid - count + 1;
        int right = n % 2 == 0 ? mid + count : mid + count - 1;
        for (int i = left; i <= right; i++) {
            res.add(i);
        }
        return res;
    }
    
    static List<Integer> solve(String s) {
        List<Integer> res = new ArrayList<>();
        int l = 0;
        int r = s.length() - 1;
        
        // ✅️重点修复bug
        while (l < r && s.charAt(l) == s.charAt(r)) {
            l++;
            r--;
        }
        
        // 循环一遍发现左右对称 指针相遇 本身就是回文串
        if (l >= r) {
            return centerIndices(s);
        }
        
        int n = s.length();
        if (isPalindrome(s, l + 1, r)) {
            char c = s.charAt(l);
            for (int i = l; i >= 0 && s.charAt(i) == c; i--) {
                res.add(i);
            }
        }
        if (isPalindrome(s, l, r - 1)) {
            char c = s.charAt(r);
            for (int i = r; i < n && s.charAt(i) == c; i++) {
                res.add(i);
            }
        }
        Collections.sort(res);
        
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(solve("abca")); // 输出 [1, 2]
        System.out.println(solve("aba"));  // 输出 [1]
        System.out.println(solve("aaa"));  // 输出 [0, 1, 2]
        System.out.println(solve("abba")); // 输出 [1, 2]
        System.out.println(solve("abbbba"));  // 输出 [1, 2, 3, 4]
        System.out.println(solve("aabbbaa"));  // 输出 [2, 3, 4]
        System.out.println(solve("abc"));      // 输出 []
        System.out.println(solve("ab"));       // 输出 [0, 1]
        System.out.println(solve("abcdabcdcba")); // 输出 []
        // ✅️BUG修复测试用例
        System.out.println(solve("aabb")); // 输出 []
        System.out.println(solve("abab")); // 输出 [0,3]
        System.out.println(solve("aaabaa")); // 输出[0, 1, 2, 3]
    }
}
