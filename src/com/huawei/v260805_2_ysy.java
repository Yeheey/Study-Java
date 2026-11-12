package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class v260805_2_ysy {
    /*
    【26.8.5-2】智能广播合并台号--【滑动窗口】--60min
    【太久了 实际不难】
    题目描述
    某社区有多条广播线路，每条线路用一个大写英文字母编号表示 (A-Z)。
    当发送广播时，为减少操作次数，会将连续相邻编号且满足一定个数的线路合并为一个区间，用“起始编号-结束编号”的简写形式表示；不连续或不满足个数的线路则单独列出。
    合并后的结果要求按字母升序排列，各项之间用一个逗号分隔。
    
    具体合并规则如下：
    
    若连续的线路编号个数 ≥3 个，则合并为一个区间，格式为 起始-结束（如 B-F 表示 B、C、D、E、F 五条线路）
    若连续线路编号个数 =2 个，不合并，仍各自单独列出（如 AB 两个编号写成 A,B）
    若连续线路编号个数 =1 个，单独列出（如 G）
    不连续的线路之间各自处理，互不影响
    
    输入描述
    第一行输入整数 n，表示需要广播的线路条数。
    第二行输入 n 个大写字母，空格分隔，表示发送广播的线路编号。字母可能重复，但合并时每个编号只计一次。
    
    输出描述
    输出一个字符串，表示合并后的广播线路列表，按字母升序排列，项间用逗号分隔。
    约束：1≤n≤26，字母范围为 A-Z（仅大写）。输入字母可能有重复，需去重后排序再合并。
    */
    static String solve(String s) {
        // 1.排序+去重
        List<Character> strArr = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' && !strArr.contains(s.charAt(i))) {
                strArr.add(s.charAt(i));
            }
        }
        Collections.sort(strArr);
        
        // 情况1 长度为1
        int len = strArr.size();
        if (len == 1) {
            return strArr.getFirst().toString();
        }
        
        // 情况2 长度大于1
        StringBuilder sb = new StringBuilder();
        // 滑动窗口
        int start = 0;
        int end = 1;
        // 计算相邻长度
        int count = 1;
        
        StringBuilder temp = new StringBuilder();
        while (start < end && end < len) {
            
            char cs = strArr.get(start);
            char ce = strArr.get(end);
            char cl = strArr.get(end - 1);
            if (ce - cl == 1) {
                // 符合要求 右移
                end += 1;
            } else {
                // 不合要求 计算之前end - start = count
                count = end - start;
                if (count == 1) {
                    temp.append(cs);
                } else if (count == 2) {
                    temp.append(cs).append(",").append(cl);
                } else {
                    temp.append(cs).append("-").append(cl);
                }
                
                // count 重置
                count = 1;
                start = end;
                end += 1; // 右移
            }
            
            // 如果是最后一个符合要求的 要重新添加
            if (end == len - 1) {
                temp.append(cs).append("-").append(strArr.get(len - 1));
            }
            
            if (!temp.isEmpty()) {
                sb.append(temp);
                if (end != len - 1) {
                    sb.append(",");
                }
                // 重新置空
                temp = new StringBuilder();
            }
            
            
        }
        
        return sb.toString();
    }
    
    static void main() {
        String s = "G,B,A,F,D,C,B,Z,O,P,Q,X,Y";
        // "A-D,F,G,O-Q,X-Z"
        System.out.println(solve(s));
        
        String s1 = "A";
        // "A"
        System.out.println(solve(s1));
    }
}
