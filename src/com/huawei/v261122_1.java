package com.huawei;

public class v261122_1 {
/*
    【智能文本压缩解码】--【模拟计算+字符串】--写24min+调20min
    题目描述
    某通信系统采用了一种特殊的文本压缩算法来传输数据，规则如下：
        1. 连续重复的字符被压缩为"字符+重复次数"的格式。例如，"aaabbc"会被压缩为"a3b2c1"。
        2. 原始字符串中的数字字符（0~9）在压缩时会被用方括号[]括起来，以区别于重复次数。例如，"111222"会被压缩为"[1]3[2]3"。
        3. 非数字字符，直接与重复次数拼接。
    请将压缩后的字符串还原为原始字符串。
    注意：用例保证输入字符串格式正确，字符除字母外也可能是任意可打印ASCII字符（编码32~126）。
    
    输入描述
    输入一个字符串s，表示按上述规则得到的压缩字符串。输入按样例使用双引号包围的单行字符串编码；最外层双引号用于表示字符串，不属于压缩内容。
    每个压缩片段由字符表示和紧随其后的十进制正整数重复次数组成，重复次数可以有多位。原字符为数字时，字符表示为方括号包围的一个数字，如[1]。
    数据范围：
        - 1 ≤ |s| ≤ 10⁴。
        - 每个重复次数至少为1。
        - 解压后的字符串长度不超过10⁵。
        - 输入保证符合题目给定的合法编码格式。
    */
    
    public static String solve(String s) {
        if (s.isEmpty()) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        
        char[] arr = s.toCharArray();
        boolean isFlag = false; // 是循环字符-true 是循环次数-false
        
        String cur = ""; // 要循环的字符 应该就1个
        StringBuilder cntStr = new StringBuilder(); // 要循环的次数 可能有多位数
        
        for (int i = 0; i < arr.length; i++) {
            char c = arr[i];
            if (c == '[' && i + 2 < arr.length && arr[i + 2] == ']') {
                isFlag = true;
                cur =String.valueOf(arr[i+1]); // 要循环的数字
                continue;
            }
            if (c == ']' && isFlag) {
                isFlag = false;
                continue;
            }
            // 【循环的字符cur】-除了[]和数字以外的字符 or 是循环字符的数字 记录下来
            if (c < '0' || c > '9' || (isFlag && c >= '0' && c <= '9')) {
                // 先去做上一次的循环
                if (!cntStr.isEmpty()) {
                    int cnt = Integer.parseInt(cntStr.toString());
                    sb.repeat(cur, cnt);
                    // 遇到新的循环字符 清空之前的次数
                    cntStr = new StringBuilder();
                }
                cur = String.valueOf(c);
            }
            // 【循环次数】
            if (!isFlag && c >= '0' && c <= '9') {
                cntStr.append(c);
            }
            // 如果是最后一次记得加上
            if (i == arr.length - 1 && !cntStr.isEmpty()) {
                int cnt = Integer.parseInt(cntStr.toString());
                sb.repeat(cur, cnt);
            }
        }
        
        return sb.toString();
    }
    
    static void main() {
        System.out.println(solve("a1[1]2")); // "a11"
        System.out.println(solve("[1]3[2]3")); // "111222"
        System.out.println(solve("a12")); // "aaaaaaaaaaaa"
        System.out.println(solve("[3")); // 期望 "[[["，实际 ""
        System.out.println(solve("]2")); // 期望 "]]"，实际 ""
    }
}
