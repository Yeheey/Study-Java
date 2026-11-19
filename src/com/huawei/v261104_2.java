package com.huawei;

import java.util.Stack;

public class v261104_2 {
/*
    【河流水系流量评估】--【模拟计算+栈】
    题目描述
    某河流水系由基础溪流和嵌套的支流组组成，可用一个字符串表示。请计算整个水系的总流量。
        ·基础溪流以小写字母表示。流量表按字母顺序给出，a对应第一个元素，b对应第二个元素，依此类推。
        ·n[X]表示n条相同的支流X汇入，贡献为X的总流量乘以n。n为1时可以省略，因而[x]与[1X]等价。
    同一层的溪流和支流组并列时，流量相加。支流组可以继续嵌套，空括号[]的流量为0。
    输入描述
    输入仅一行，先给出方括号包围的流量表，再用英文逗号连接一个双引号包围的表达式四。例如下面的输入表示三种基础溪流和一个嵌套水系。[10, 20, 30],"2[a3[b]c]"
    流量表内部及分隔符旁可以有空白。表达式内只含小写字母、数字和方括号。流量表长度count满足1≤count≤26，每个流量值在1到1000之间。表达式长度为1到64，方括号成对出现。
    数字只出现在左括号前，表示1到99的支流条数。表达式中的字母均可在流量表中按字母下标找到。
    最终流量不超过2^63-1。
    输出描述
    输出一行一个十进制整数，表示河流水系的总流量。*/
    
    /**
     * 字母转下标
     */
    static int enToIndex(char c) {
        return c - 'a';
    }
    
    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    static boolean isEn(char c) {
        return c >= 'a' && c <= 'z';
    }
    
    /**
     * 栈解法
     * stack存：已经算好的上层累加和
     * mulStack存：遇到左括号前的倍数
     */
    static long solve(int[] input, String s) {
        Stack<Long> stack = new Stack<>();
        Stack<Long> mulStack = new Stack<>();
        long curSum = 0L;
        int i = 0;
        int n = s.length();
        while (i < n) {
            char ch = s.charAt(i);
            if (isEn(ch)) {
                // 字母，直接加到当前和
                curSum += input[enToIndex(ch)];
                i++;
            } else if (isDigit(ch)) {
                // 读取连续数字
                long num = 0;
                while (i < n && isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                // 数字后面一定是 [，先把当前和压栈，倍数存mul栈，重置curSum
                stack.push(curSum);
                mulStack.push(num);
                curSum = 0;
                i++; // 跳过'['
            } else if (ch == '[') {
                // 没有数字，省略，倍数=1
                stack.push(curSum);
                mulStack.push(1L);
                curSum = 0;
                i++;
            } else if (ch == ']') {
                // 括号闭合：当前括号内部总和 × 倍数，然后加上栈里上层的和
                long pre = stack.pop();
                long times = mulStack.pop();
                curSum = pre + curSum * times;
                i++;
            } else {
                i++;
            }
        }
        return curSum;
    }
    
    static void main() {
        int[] input = {10, 20, 30};
        String s = "2[a3[b]c]";
        // 200
        System.out.println(solve(input, s));
        
        int[] input1 = {10, 20, 30, 40, 50};
        String s1 = "3[c2[e]]";
        // 390
        System.out.println(solve(input1, s1));
        
        int[] input2 = {10, 20};
        String s2 = "2[ab]";
        // 60
        System.out.println(solve(input2, s2));
    }
}
