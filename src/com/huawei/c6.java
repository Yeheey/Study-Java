package com.huawei;

public class c6 {
    /*
        【6.整数编码】--【字符串+模拟计算】--40min
        题目描述
        实现一种整数编码方法，使得待编码的数字越小，编码后所占用的字节数越小。
        编码规则如下:
        编码时7位一组，每个字节的低7位用于存储待编码数字的补码
        字节的最高位表示后续是否还有字节，置1表示后面还有更多的字节，置0表示当前字节为最后一个字节。
        采用小端序编码，低位和低字节放在低地址上。
        编码结果按16进制数的字符格式输出，小写字母需转换为大写字母
        
        输入描述
        输入的为一个字符串表示的非负整数 待编码的数字取值范围为[0，1<<64 - 1]
        
        输出描述
        输出一个字符串，表示整数编码的16进制码流
        */
    static String solve(String s) {
        String bin = Integer.toBinaryString(Integer.parseInt(s));
        
        StringBuilder res = new StringBuilder();
        int len = bin.length();
        int circle = 0;
        int times = len % 7 == 0 ? len / 7 : len / 7 + 1;
        
        while (circle < times) {
            int start = Math.max(len - 7 * (circle + 1), 0);
            int end = len - 7 * circle;
            
            String s1 = "0"; // 最高位
            // 每次取7位数
            String s2 = bin.substring(start, end);
            if (start > 0) {
                // 说明前面还有数字 补1
                s1 = "1";
            }
            String s3 = s1 + s2;
            int t3 = Integer.parseInt(s3, 2); // 二进制字符串转十进制
            String h3 = Integer.toHexString(t3); // 十进制转十六进制字符串
            if (h3.length() == 1) {
                res.append("0");
            }
            res.append(h3);
            circle++;
        }
        
        return res.toString().toUpperCase();
    }
    
    static void main() {
        System.out.println(solve("0")); // "00"
        System.out.println(solve("100")); // "64"
        System.out.println(solve("1000")); // "E807"
    }
}
