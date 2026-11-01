package com.huawei;

import java.math.BigInteger;

public class test20 {
    /*
    20.【26.8.16-2】信道资源分配与掩码运
    题目描述:
    在进行信道资源分配时，需要通过掩码(Bitmask)来高效管理可用信道的状态。
    每个信道对应掩码中的一个二进制位(位置为1表示信道可用，位置为0表示信道被占用)。
    掩码状态编码以不同进制格式(二进制、十进制、十六进制)存储在配置文件中，由64位无符号整数表示(二进制从低位到高位对应信道0到信道63)，
    掩码的输入格式为带进制标识的字符串:
        二进制字符串:以ob开头(如ob1011);
        十六进制字符串:以ox开头(如ox1A3);
        十进制字符串:无特殊前缀(如255)。
    
    为了优化资源利用率，需计算两组可用信道的集合:交集、并集，以及统计连续可用信道的最大长度(连续1的最长子串）。
    请实现一个函数完成以下操作:
    1.将两个输入的掩码字符串(maskstrA、maskstrB)转为64位无符号整数;
    2.计算两个掩码的可用信道交集(仅保留两个掩码中都为1的位);
    3.计算两个掩码的可用信道并集(保留任意一个掩码中为1的位);
    4.统计两个掩码的连续可用信道最大长度(二进制中连续1的最长长度)。
    函数需返回一个长度为3的数组(十进制整数)，依次包含:交集结果、并集结果、最大连续可用信道长度。
    
    输入描述：
    输入字符串四仅包含合法的进制格式(无需处理非法输入);
    掩码值范围为 0<=mask<=2^64-1，超出范围的输入不存在;
    二进制字符串长度不超过64位，十六进制字符串长度不超过16位;
    连续可用信道长度计算规则:全0掩码返回0，全1掩码返回64。
    输入为一行，两个掩码字符串使用英文逗号分隔:maskStrA,maskStrB

    输出描述
    输出一个长度为3的数组(十进制整数)，依次包含:交集结果、并集结果、最大连续可用信道长度。
    数组格式中不包含空格，例如:[9,15,4]
     */
    
    /**
     * 解析不同进制的掩码字符串
     */
    static BigInteger parseMask(String s) {
        if (s.startsWith("0x") || s.startsWith("0X")) {
            return new BigInteger(s.substring(2), 16);
        }
        if (s.startsWith("0b") || s.startsWith("0B")) {
            return new BigInteger(s.substring(2), 2);
        }
        return new BigInteger(s, 10);
    }
    
    static BigInteger[] solve(String s) {
        BigInteger[] res = new BigInteger[3];
        String maskStrA = s.split(",")[0];
        String maskStrB = s.split(",")[1];
        
        BigInteger maskA = parseMask(maskStrA);
        BigInteger maskB = parseMask(maskStrB);
        
        res[0] = maskA.and(maskB);
        res[1] = maskA.or(maskB);
        
        // 取并集最大连续1
        int maxLen = 0;
        int curLen = 0;
        for (int i = 0; i < 64; i++) {
            if (res[1].testBit(i)) {
                curLen++;
                maxLen = Math.max(maxLen, curLen);
            } else {
                curLen = 0;
            }
        }
        
        res[2] = BigInteger.valueOf(maxLen);
        
        System.out.println(res[0].toString());
        System.out.println(res[1].toString());
        System.out.println(maxLen);
        
        return res;
    }
    
    static void main() {
        // "0b1011,0b1101"
        // [9,15,4]
        String s = "0b1011,0b1101";
        System.out.print(solve(s));
    }
}
