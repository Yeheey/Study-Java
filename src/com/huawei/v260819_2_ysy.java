package com.huawei;

import java.math.BigInteger;

public class v260819_2_ysy {
    /*
        【26.8.19-2】大整数位序反转-【BigInteger+字符串+前导0】--24min 不熟悉方法
        题目描述:
        小华是一名嵌入式开发工程师，在处理芯片寄存器数据时，经常需要对超多位数据进行位翻转操作。
        寄存器中的数据可能是几百位甚至更长。
        给定一个十进制大整数(最多100位)，请将其二进制表示中的所有位顺序全部翻转(最低位变成最高位，次低位变成次高位，以此类推)，然后输出翻转后的十进制表示。
        
        输入描述:
        一个十进制大整数(字符串形式，范围:0~10^100 -1，输入不带前导零，除非为0)。
        
        输出描述:
        翻转后的十进制整数(字符串形式，不能有前导零，除非输出为0)。
    */
    
    static String solve(String s) {
        // 1.十进制字符串->十进制大整数
        BigInteger bigInteger = new BigInteger(s, 10);
        
        // 2.十进制大整数->二进制字符串
        String binStr = bigInteger.toString(2);
        
        // 3.反转二进制字符串
        StringBuilder sb = new StringBuilder();
        int len = binStr.length();
        for (int i = len - 1; i >= 0; i--) {
            sb.append(binStr.charAt(i));
        }
        
        // 4.前导零检查
        if (sb.length() == 1 && sb.charAt(0) == '0') {
            return "0";
        }
        int i = 0;
        while (i < sb.length() && sb.charAt(i) == '0') {
            i++;
        }
        
        return sb.substring(i);
    }
    
    
    static void main() {
        String n = "4";
        // output 1
        System.out.println(solve(n));
    }
}
