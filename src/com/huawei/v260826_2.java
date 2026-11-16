package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260826_2 {
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
    
    static List<Integer> solve(String s) {
        List<Integer> res = new ArrayList<>();
        if (s == null || s.length() < 2) {
            return res;
        }
        char[] a = s.toCharArray();
        int n = a.length;
        // 从外往里配对，k 是第一处失配的下标，也是已经配对成功的对数
        int l = 0;
        int r = n - 1;
        while (l < r && a[l] == a[r]) {
            l++;
            r--;
        }
        int k = l;
        
        // 删左边下标 i 时，中间段是 s[i+1 .. n-1-i]，这些段共用一个中心
        int minLeft = expand(a, n % 2 == 0 ? n / 2 : (n + 1) / 2, true);
        // 删右边下标 i 时，中间段是 s[n-1-i .. i-1]，中心比上面那个偏左一格
        int minRight = expand(a, n % 2 == 0 ? n / 2 - 1 : (n - 1) / 2, false);
        
        int half = (n - 1) / 2;
        for (int i = 0; i <= half; i++) {
            // 外层至少配到 i，并且删掉 i 后的中间段是回文
            if (k >= i && i + 1 >= minLeft) {
                res.add(i);
            }
        }
        for (int i = half + 1; i < n; i++) {
            int mirror = n - 1 - i;
            if (k >= mirror && mirror >= minRight) {
                res.add(i);
            }
        }
        return res;
    }
    
    /**
     * 从中心向外扩，返回仍是回文的最左下标。
     * fromEnd 为 true 时，右端下标是 n - left；否则是 n - 2 - left。
     */
    static int expand(char[] a, int left, boolean fromEnd) {
        int n = a.length;
        int minLeft = left;
        while (minLeft - 1 >= (fromEnd ? 1 : 0)) {
            int newLeft = minLeft - 1;
            int newRight = (fromEnd ? n - newLeft : n - 2 - newLeft);
            if (newRight < 0 || newRight >= n || a[newLeft] != a[newRight]) {
                break;
            }
            minLeft = newLeft;
        }
        return minLeft;
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
        System.out.println(solve("abcdabcdcba")); // 输出 []
    }
}
