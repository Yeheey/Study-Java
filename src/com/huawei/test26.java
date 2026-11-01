package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test26 {
    /*
    26.【26.8.9-2】灯带颜色变换
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
    
    // 方法一：暴力解法 会超时
    static String solve(String s, int t) {
        StringBuilder lastString = new StringBuilder(s);
        StringBuilder curString = new StringBuilder(s);
        
        // 遍历秒数
        for (int second = 0; second < t; second++) {
            curString.setCharAt(0, 'R');
            curString.setCharAt(15, 'R');
            
            // 遍历字符串
            for (int c = 1; c < 15; c++) {
                if (lastString.charAt(c - 1) == lastString.charAt(c + 1)) {
                    curString.setCharAt(c, 'G');
                } else {
                    curString.setCharAt(c, 'R');
                }
            }
            
            lastString = new StringBuilder(curString.toString());
        }
        
        return curString.toString();
    }
    
    // 解法2: 用空间换时间 把所有状态存入map里面
    static String solve1(String s, int t) {
        Map<String, Integer> stateMap = new HashMap<>();
        List<String> stateList = new ArrayList<>();
        String curr = s;
        
        // 当map中没有当前字符串的key值时
        while (!stateMap.containsKey(curr)) {
            // key是字符串 value是状态数组的长度
            stateMap.put(curr, stateList.size());
            // 状态数组存入每一秒变化的字符串
            stateList.add(curr);
            // 当前字符串更新下一秒的字符串
            curr = getNext(curr);
        }
        
        // while循环结束时 curr又回到传的值
        // 计算循环长度
        int cycleStart = stateMap.get(curr);
        int cycleLen = stateList.size() - cycleStart;
        
        if (t < cycleStart) {
            return stateList.get(t);
        } else {
            int off = (t - cycleStart) % cycleLen;
            return stateList.get(cycleStart + off);
        }
    }
    
    // 获取下一个状态
    static String getNext(String s) {
        StringBuilder last = new StringBuilder(s);
        StringBuilder cur = new StringBuilder(s);
        cur.setCharAt(0, 'R');
        cur.setCharAt(15, 'R');
        for (int c = 1; c <= 14; c++) {
            if (last.charAt(c - 1) == last.charAt(c + 1)) {
                cur.setCharAt(c, 'G');
            } else {
                cur.setCharAt(c, 'R');
            }
        }
        return cur.toString();
    }
    
    static void main() {
        System.out.println(solve("RRRRRRRRRRRRRRRR", 3));  // "RGGGGGGGGGGGGGGR"
        System.out.println(solve1("RRRRRRRRRRRRRRRR", 3));
    }
}
