package com.huawei;

import java.util.*;

public class v261101_1_ysy {
    /*
    【26.11.1-1】整理学生编号--【模拟计算】--60min
    题目描述
    一所小学需要整理初始的学生编号，使其符合规定格式后录入系统。初始编号可能包含数字、字母、空格和特殊符号，只有符合基本要求的编号才可以整理使用，其余编号直接丢弃。
    符合基本要求的编号必须同时满足：
    
    1.仅包含数字 0～9、英文字母 A～Z 或 a～z，以及普通空格。
    2.去掉全部空格后的字符总数不超过 6。
    3.有且仅有一个英文字母，其余字符全部为数字，并且至少有一个数字。
    4.数字部分不能全为0。
    
    对合法编号，按以下规则整理：
   
    1.删除全部空格，把唯一的字母转为大写并放到首位；数字保持原来的先后顺序。
    2.在数字部分左侧补 0 至五位，使整个编号长度恰好为六位。例如 a123 整理为 A00123，00a1 整理为 A00001。
    3.按输入顺序处理重复情况。对同一个初始规范化结果，第 k 次出现时（k 从 0 开始），将其数字部分加上 k×10000，再按五位数字补零输出。
    
    例如三个都规范化为 A00001 的编号，依次变为 A00001、A10001、A20001。
    若累加后数字部分超过 99999，则丢弃该次编号。已经丢弃的出现也计入该组次数，所以同一组开始溢出后，后续出现都会被丢弃。
    重复次数以步骤 1、2 得到的原规范化结果分组统计。不同分组的计数互不影响，调整后的结果不再额外去重或继续递增。
 
    将所有保留的编号按字典序升序排列后输出。
    
    输入描述
    输入只有一行，是一个 JSON 风格的字符串数组，表示待整理的原始学生编号，例如：["a123","1C01","dd123","t000"]
    数组元素使用英文双引号包裹，元素之间用英文逗号分隔。编号内部的空格属于字符串内容，应原样读入，再按题目规则处理。输入不包含需要转义的双引号或反斜杠。
    数组长度为 1 到 80，每个原始编号长度为 0 到 10，长度包含空格。空字符串和全部为空格的编号均不合法。去空格后的长度要求另按题目规则判断。
    
    输出描述
    输出一行字符串数组，保留的编号按字典序升序排列。每个编号用英文双引号包裹，元素之间用英文逗号分隔，逗号后不加空格。
    
    例如：["A00123","C00101"]。如果没有编号被保留，输出空数组 []。
    */
    
    /**
     * 是否为数字
     */
    static boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }
    
    /**
     * 是否为英文字母
     */
    static boolean isEnglish(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
    
    /**
     * 是否是合法输入
     */
    static boolean isValidInput(String s) {
        // 先去除空格
        String s1 = s.replace(" ", "");
        int len = s1.length();
        int numCount = 0;
        int zeroCount = 0;
        int enCount = 0;
        
        for (int i = 0; i < len; i++) {
            char curC = s1.charAt(i);
            if (isDigit(curC)) {
                numCount++;
                if (curC == '0') {
                    zeroCount++;
                }
            } else if (isEnglish(curC)) {
                enCount++;
            } else {
                // 除去空格后 不是数字 也不是英文 不合法
                return false;
            }
        }
        
        return (numCount + enCount <= 6) && (enCount == 1) && (numCount >= 1) && (numCount != zeroCount);
    }
    
    /**
     * 整理合法的编码
     */
    static String formatter(String s) {
        StringBuilder sb1 = new StringBuilder(); // 字母
        StringBuilder sb2 = new StringBuilder(); // 数字
        
        for (int i = 0; i < s.length(); i++) {
            char curC = s.charAt(i);
            if (isEnglish(curC)) {
                sb1.append(String.valueOf(curC).toUpperCase());
            }
            if (isDigit(curC)) {
                sb2.append(curC);
            }
        }
        
        // 补齐前导0
        if (sb2.length() != 5) {
            String temp = sb2.toString();
            int tempLen = temp.length();
            int zeroLen = 5 - tempLen;
            sb2 = new StringBuilder();
            sb2.repeat("0", Math.max(0, zeroLen));
            sb2.append(temp);
        }
        
        return sb1.append(sb2).toString();
    }
    
    /**
     * 重复编码整理 第 count 次出现：数字部分加 count×10000。超过 99999 时返回空串，表示丢弃。
     */
    static String repeat(String s, int count) {
        int num = Integer.parseInt(s.substring(1)) + count * 10000;
        if (num > 99999) {
            return "";
        }
        // ⭐把数字 num 格式化为 5 位的字符串，不够5位前面补0
        return s.charAt(0) + String.format("%05d", num);
    }
    
    static List<String> solve(String[] input) {
        List<String> res = new ArrayList<>();
        
        for (final String s : input) {
            boolean isValid = isValidInput(s);
            // 合法后整理
            if (isValid) {
                String fs = formatter(s);
                res.add(fs);
            }
        }
        
        Collections.sort(res);
        
        HashMap<String, Integer> m = new HashMap<>();
        for (String re : res) {
            m.merge(re, 1, Integer::sum);
        }
        
        List<String> result = new ArrayList<>();
        
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            
            for (int k = 0; k < value; k++) {
                String cur = repeat(key, k);
                if (cur.isEmpty()) {
                    continue;
                }
                result.add(cur);
            }
        }
        
        // 去重+排序
        Collections.sort(result);
        
        return result;
    }
    
    static void main() {
        String[] input = {"a00 1", "a0011", "0001 b", " 00c2", "d00001", "e1234568", "B99999", "b 99999", "b 89999", "b 89999", "00 a1", "00 a1", "00 a1"};
        // ["A00001","A00011","A10001","A20001", "A30001","B00001","B89999","B99999","C00002","D00001"] 升序
        System.out.println(solve(input));
    }
}
