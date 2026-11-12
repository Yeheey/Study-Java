package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260809_2_ysy {
    /*
    26.【26.8.9-2】灯带颜色变换--【模拟计算】--8min
    题目描述
    小明设计了一条灯带，该灯带中共有 16 盏灯（编号 0 到 15），每盏灯有两种颜色：红色（用字符 R 表示）和绿色（用字符 G 表示）。
    每过一秒，灯带中的灯都会按照以下规则进行一次颜色变换：
    
    如果上一秒灯 lights[i] 的两个相邻灯 lights[i-1] 和 lights[i+1] 颜色一致，则灯 lights[i] 在当前秒需要设置为绿色。
    其他场景（相邻灯颜色不一致、或灯只有单一邻居），则该灯在当前秒需要设置为红色。
    注意：灯带中编号为 0 的灯和编号为 15 的灯是不相邻的（线性灯带，首尾不相连）。因此编号 0 的灯只有右邻居，编号 15 的灯只有左邻居。
    
    给定一个灯带的初始状态，请你输出 t 秒后灯带中各灯的颜色。
    
    输入描述
    单行输入，格式为："lights",t
    其中 lights 为用英文双引号包围的长度为 16 的字符串，t 为整数。两者用逗号分隔。
    
    输出描述
    输出一个用英文双引号包围的长度为 16 的字符串。
    
    lights 长度固定为 16。
    lights 中每个字符为 R 或 G。
    1 <= t <= 10000000。
    */
    
    // 获取下一秒的灯带颜色
    static String getNextColor(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 || i == s.length() - 1) {
                sb.append("R");
            } else {
                String temp = (s.charAt(i - 1) == s.charAt(i + 1)) ? "G" : "R";
                sb.append(temp);
            }
        }
        return sb.toString();
    }
    
    static String solve(String s, int t) {
        // 有循环 存Set 灯带颜色
        List<String> list = new ArrayList<>();
        while (true) {
            String next = getNextColor(s);
            if (list.contains(next)) {
                break;
            }
            list.add(next);
        }
        int circle = list.size();
        int mod = t % circle;
        return list.get(mod);
    }
    
    static void main() {
        System.out.println(solve("RRRRRRRRRRRRRRRR", 3));  // "RGGGGGGGGGGGGGGR"
    }
}
