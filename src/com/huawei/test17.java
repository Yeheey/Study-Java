package com.huawei;

import java.math.BigInteger;

public class test17 {
/*
    16.【26.8.19-2】大整数位序反转
    题目描述:
    小华是一名嵌入式开发工程师，在处理芯片寄存器数据时，经常需要对超多位数据进行位翻转操作。
    寄存器中的数据可能是几百位甚至更长。
    给定一个十进制大整数(最多100位)，请将其二进制四表示中的所有位顺序全部翻转(最低位变成最高位，次低位变成次高位，以此类推)，然后输出翻转后的十进制表示。
    输入描述:
    一个十进制大整数(字符串形式，范围:0~10^100 -1，输入不带前导零，除非为0)。
    输出描述:
    翻转后的十进制整数(字符串形式，不能有前导零，除非输出为0)。
    */
    
    static String solve(String n) {
        BigInteger num = new BigInteger(n);
        if (num.compareTo(BigInteger.ZERO) == 0) {
            return "0";
        }
        String binary = num.toString(2); // 转二进制
        String reversed = new StringBuilder(binary).reverse().toString(); // 反转
        String res = new BigInteger(reversed, 2).toString();   // 二进制字符串转十进制
        
        return res;
    }
    
    
    static void main() {
        String n = "4";
        // output 1
        System.out.println(solve(n));
        
    }
}
