package com.huawei;

import java.util.*;

public class v261101_1 {
    /**
     * 1.trim()只去除头尾空格 不去除中间空格 用String s = num.replace(" ", "");
     * 注意：replaceAll("\\s+","") 会删除制表符
     * 2.Map遍历不熟悉
     * 3.字符串和数字互转API
     */
    
    /*
    【26.11.1-1】整理学生编号【模拟计算--100min】
    题目描述
    一所小学需要整理初始的学生编号，使其符合规定格式后录入系统。初始编号可能包含数字、字母、空格和特殊符号，只有符合基本要求的编号才可以整理使用，其余编号直接丢弃。
    符合基本要求的编号必须同时满足：
    
    仅包含数字 0～9、英文字母 A～Z 或 a～z，以及普通空格。
    去掉全部空格后的字符总数不超过 6。
    有且仅有一个英文字母，其余字符全部为数字，并且至少有一个数字。
    数字部分不能全为 0。
    对合法编号，按以下规则整理：
    
    删除全部空格，把唯一的字母转为大写并放到首位；数字保持原来的先后顺序。
    在数字部分左侧补 0 至五位，使整个编号长度恰好为六位。例如 a123 整理为 A00123，00a1 整理为 A00001。
    按输入顺序处理重复情况。对同一个初始规范化结果，第 k 次出现时（k 从 0 开始），将其数字部分加上 k×10000，再按五位数字补零输出。
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
    static boolean isValidNumber(String num) {
        int len = 0;
        int numLen = 0; // 数字长度
        int numZeroLen = 0; // 数字为0的长度
        int enLen = 0; // 字母长度
        
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            if (c == ' ') {
                continue;
            }
            len++; // 空格不算长度
            if (c >= '0' && c <= '9') {
                numLen++;
                if (c == '0') {
                    numZeroLen++;
                }
            } else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                enLen++;
            } else {
                // 有不合法字符
                return false;
            }
        }
        
        // 1.去空格后 长度超过6 false
        // 2.不全是数字字母组成 or 字母不是1个 or 没有数字 or 数字全0 false
        return len <= 6 && enLen == 1 && numLen > 0 && numZeroLen < numLen;
    }
    
    // 提取数字和字母
    static String extract(String s) {
        // 0存字母大写 1存数字
        String[] ans = new String[2];
        StringBuilder sb = new StringBuilder();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                ans[0] = String.valueOf(c).toUpperCase();
            }
            if (c >= '0' && c <= '9') {
                sb.append(c);
            }
        }
        // [⭐优化写法] ans[1]数字部分补零操作
        while (sb.length() < 5) {
            sb.insert(0, '0');
        }
        // if (sb.length() != 5) {
        //     for (int i = 0; i < 6 - sb.length(); i++) {
        //         sb.insert(0, '0');
        //     }
        // }
        ans[1] = sb.toString();
        
        return ans[0] + ans[1];
    }
    
    static List<String> solve(String[] input) {
        List<String> tempRes = new ArrayList<>();
        for (final String num : input) {
            // 1.先校验编号是否符合规范
            boolean isValid = isValidNumber(num);
            // 2.符合规范再进行整理
            if (isValid) {
                // 2.1 删除所有空格
                String s = num.replace(" ", "");
                // 2.2 处理数字和字母
                String result = extract(s);
                // 2.3 先存入结果
                tempRes.add(result);
            }
        }
        // 3.重复学号处理
        List<String> res = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        // ⭐优化写法
        for (String cur : tempRes) {
            map.put(cur, map.getOrDefault(cur, 0) + 1);
        }
        // for (String cur : tempRes) {
        //     if (map.containsKey(cur)) {
        //         // 后续+1
        //         map.put(cur, map.get(cur) + 1);
        //     } else {
        //         // 第一次进来放0
        //         map.put(cur, 0);
        //     }
        // }
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            int val = entry.getValue();
            // ⭐优化写法
            int baseNumber = Integer.parseInt(key.substring(1));
            
            for (int k = 0; k < val; k++) {
                int number = baseNumber + k * 10000;
                if (number > 99999) {
                    break;
                }
                /*⭐
                  %05d 的含义：
                  d：按十进制整数格式化。
                  5：至少占 5 位。
                  0：不足 5 位时，在左侧补 0。
                  Locale.ROOT 表示使用固定的、与地区无关的格式化规则，避免不同系统区域设置影响结果。
                  */
                res.add(key.charAt(0) + String.format(Locale.ROOT, "%05d", number));
            }
            // if (val == 0) {
            //     res.add(key);
            // } else {
            //     // 遍历次数
            //     int i = 0;
            //     // 取数字首位
            //     int row = Integer.parseInt(key.substring(1, 2));
            //     while (row < 9 && i <= val) {
            //         row++;
            //         i++;
            //         String rowStr = Integer.toString(row);
            //         String newKey = new StringBuilder(key).replace(1, 2, rowStr).toString();
            //         res.add(newKey);
            //     }
            // }
        }
        // 4.排序
        Collections.sort(res);
        return res;
    }
    
    static void main() {
        String[] input = {"a00 1", "a0011", "0001 b", " 00c2", "d00001", "e1234568", "B99999", "b 99999", "b 89999", "b 89999", "00 a1", "00 a1", "00 a1"};
        // ["A00001","A00011","A10001","A20001", "A30001","B00001","B89999","B99999","C00002","D00001"] 升序
        System.out.println(solve(input));
    }
}
