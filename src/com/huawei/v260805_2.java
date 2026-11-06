package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class v260805_2 {
    /*
    29.【26.8.5-2】智能广播合并台号
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
    static String solve(int n, String s) {
        // 1.按逗号分割
        String[] parts = s.split(",");
        List<String> list = new ArrayList<>();
        for (String p : parts) {
            list.add(p.trim());
        }
        
        // 2.去重 + 排序（TreeSet，最简单）
        TreeSet<String> treeSet = new TreeSet<>(list);
        
        // 3.转回List方便下标访问
        List<String> resList = new ArrayList<>(treeSet);
        
        StringBuilder sb = new StringBuilder();
        // 情况1：给定只有一个字母
        if (resList.size() == 1) {
            sb.append(resList.getFirst());
            return sb.toString();
        }
        
        // 情况2：两个字母往上
        int len = 1;
        List<Integer> tempLen = new ArrayList<>();
        
        int left = 0;
        int right = 1;
        while (right < resList.size()) {
            if (resList.get(right).charAt(0) - resList.get(right - 1).charAt(0) == 1) {
                right++;
                len = Math.max(len, right - left);
            } else {
                left = right;
                right = left + 1;
                tempLen.add(len);
                len = 1;
            }
        }
        tempLen.add(len);
        
        int idx = 0;
        while (idx < resList.size()) {
            for (int j = 0; j < tempLen.size(); j++) {
                if (tempLen.get(j) >= 3) {
                    sb.append(resList.get(idx) + "-" + resList.get(idx + len - 1));
                } else if (tempLen.get(j) == 2) {
                    sb.append(resList.get(idx) + "," + resList.get(idx + 1));
                } else {
                    sb.append(resList.get(idx));
                }
                if (j != tempLen.size() - 1) {
                    sb.append(",");
                }
                idx += len;
            }
        }
        return sb.toString();
    }
    
    static void main() {
        int n = 6;
        String s = "G,B,A,F,D,C,B,Z,O,P,Q,X,Y";
        String s1 = "A";

        System.out.println(solve(n, s1));
    }
}
