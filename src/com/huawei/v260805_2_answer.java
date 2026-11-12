package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class v260805_2_answer {
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
    
    static String solve(String[] letters) {
        // 去重并排序，后续只需要扫描连续区间
        TreeSet<Character> set = new TreeSet<>();
        for (String s : letters) {
            if (!s.isEmpty()) {
                set.add(s.charAt(0));
            }
        }
        List<Character> chars = new ArrayList<>(set);
        List<String> result = new ArrayList<>();
        int i = 0;
        while (i < chars.size()) {
            int j = i;
            // 找到一个连续递增区间
            while (j + 1 < chars.size() && chars.get(j + 1) == chars.get(j) + 1) {
                j++;
            }
            if (j - i + 1 >= 3) {
                result.add(chars.get(i) + "-" + chars.get(j));
            } else {
                for (int k = i; k <= j; k++) {
                    result.add(String.valueOf(chars.get(k)));
                }
            }
            i = j + 1;
        }
        return String.join(",", result);
    }
    
    
    
    static void main() {
        String[] s = {"G", "B", "A", "F", "D", "C", "B", "Z", "O", "P", "Q", "X", "Y"};
        System.out.println(solve(s));
    }
}
