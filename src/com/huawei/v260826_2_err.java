package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v260826_2_err {
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
    // 方法2：双指针
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
    
    static List<Integer> solve(String s) {
        List<Integer> res = new ArrayList<>();
        int l = 0;
        int r = s.length() - 1;
        
        while (l < r) {
            if (s.charAt(l) == s.charAt(r)) {
                l++;
                r--;
            }
            if (s.charAt(l) != s.charAt(r)) {
                // 📌bug2：相同的字符可能删除也合法 防止遗漏
                // 删左：从失配点向左，连续相同的字符都可以删
                boolean isOkLeft = isPalindrome(s, l + 1, r);
                if (isOkLeft) {
                    char c = s.charAt(l);
                    int i = l;
                    while (i >= 0) {
                        if (s.charAt(i) != c) {
                            break;
                        }
                        i--;
                    }
                    for (int k = i + 1; k <= l; k++) {
                        res.add(k);
                    }
                }
                // 删右：从失配点向右，连续相同的字符都可以删
                boolean isOkRight = isPalindrome(s, l, r - 1);
                if (isOkRight) {
                    char c = s.charAt(r);
                    for (int i = r; i < s.length(); i++) {
                        if (s.charAt(i) != c) {
                            break;
                        }
                        res.add(i);
                    }
                }
                // 📌bug1：只能删一次 删完就退出来
                break;
            }
        }
        
        // 如果本身就是回文串 会导致while遍历完都不删减的情况
        int n = s.length();
        boolean isSelfTrue = isPalindrome(s, 0, n - 1);
        if (isSelfTrue) {
            int mid = n % 2 == 0 ? n / 2 - 1 : (n - 1) / 2;
            // 和中间字符相等的 都可以删除
            char c = s.charAt(mid);
            int count = 0;
            for (int i = mid; i >= 0; i--) {
                if (s.charAt(i) == c) {
                    count++;
                } else {
                    break;
                }
            }
            
            if (n % 2 == 0) {
                if (count == 1) {
                    res.add(mid);
                    res.add(mid + 1);
                } else {
                    // mid-count+1~mid+count
                    for (int i = mid - count + 1; i <= mid + count; i++) {
                        res.add(i);
                    }
                }
            } else {
                if (count == 1) {
                    res.add(mid);
                } else {
                    // mid-count+1~mid+count+1
                    for (int i = mid - count + 1; i <= mid + count - 1; i++) {
                        res.add(i);
                    }
                }
            }
        }
        
        Collections.sort(res);
        return res;
    }
    
    
    // 方法1：暴力解法 -- 10min -- 不行 会超时
    static boolean isValid(StringBuilder deleteStr) {
        StringBuilder reverse = new StringBuilder();
        for (int i = deleteStr.length() - 1; i >= 0; i--) {
            reverse.append(deleteStr.charAt(i));
        }
        
        boolean isV = true;
        for (int i = 0; i < deleteStr.length(); i++) {
            if (deleteStr.charAt(i) != reverse.charAt(i)) {
                isV = false;
                break;
            }
        }
        
        return isV;
    }
    
    static List<Integer> solve1(String s) {
        List<Integer> res = new ArrayList<>();
        
        int n = s.length();
        
        for (int i = 0; i < n; i++) {
            StringBuilder deleteStr = new StringBuilder();
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    deleteStr.append(s.charAt(j));
                }
            }
            boolean isTure = isValid(deleteStr);
            if (isTure) {
                res.add(i);
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        System.out.println(solve("abca")); // 输出 [1, 2]
        System.out.println(solve("aba"));      // 输出 [1]
        System.out.println(solve("aaa"));      // 输出 [0, 1, 2]
        System.out.println(solve("abba"));      // 输出 [1, 2]
        System.out.println(solve("abbbba"));      // 输出 [1, 2, 3, 4]
        System.out.println(solve("aabbbaa"));  // 输出 [2, 3, 4]
        System.out.println(solve("abc"));      // 输出 []
        System.out.println(solve("ab"));       // 输出 [0, 1]
        System.out.println(solve("abcdabcdcba")); // 输出 []
        // ✅️BUG修复测试用例
        System.out.println("======BUG=====");
        System.out.println(solve("aabb")); // 输出 []
        System.out.println(solve("abab")); // 输出 [0,3]
        System.out.println(solve("aaabaa")); // 输出[0, 1, 2, 3]
    }
}