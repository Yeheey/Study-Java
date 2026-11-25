package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class c21 {
    /*
        【21.猜数字】--想不出来--瞄了一眼答案说暴力枚举--开写
        题目描述
        一个人设定一组四位的数字作为谜底，另一方猜。
        每猜一个数，出数者就要根据这个数字给出提示，提示以XAYB形式呈现，直到猜中位置。
        其中X表示位置正确的数的个数（数字正确且位置正确），而Y表示数字正确而位置不对的数的个数。
        例如，当谜底为8123，而猜谜者猜1052时，出题者必须提示0A2B。
        例如，当谜底为5637，而猜谜者才4931时，出题者必须提示1A0B。
        当前已知N组猜谜者猜的数字与提示，如果答案确定，请输出答案，不确定则输出NA。
        
        输入描述
        第一行输入一个正整数，0＜N ＜ 100。
        
        接下来N行，每一行包含一个猜测的数字与提示结果。
        
        输出描述
        输出最后的答案，答案不确定则输出NA。
        */
    
    /**
     * 检查两个数字 位置对 数字对的个数
     * cur 0000-9999 当做谜底
     * guess 猜测的数字
     * 返回[位置对数字对 个数，数字对位置错 个数]
     */
    public static int[] checkNum(int cur, int guess) {
        // 第一个放-位置对数字对 第二个放-数字对位置错
        int[] res = new int[2];
        int count1 = 0;
        int count2 = 0;
        char[] c = String.valueOf(cur).toCharArray();
        char[] g = String.valueOf(guess).toCharArray();
        
        StringBuilder curSb = new StringBuilder(cur); // 谜底母串
        StringBuilder temp = new StringBuilder(); // 存位置和数字都对的上的 子串
        
        for (int i = 0; i < 4; i++) {
            char cc = c[i]; // cur第i个字符
            char gc = g[i]; // guess第i个字符
            // 当前位置和数字都对的上
            if (cc == gc) {
                temp.append(cc);
                count1++;
            }
            // System.out.println(temp);
            if(!temp.isEmpty()){
                int pos = curSb.indexOf(String.valueOf(temp));
                if(pos != -1){
                    // curSb 现在是减去同位置同数字剩下的子串
                    curSb.delete(pos, pos + temp.length());
                }
            }
            // 当前位置上不对  但是cur里包含了当前guess字符
            String gcStr = String.valueOf(gc);
            if (cc != gc && curSb.toString().contains(gcStr)) {
                count2++;
            }
        }
        res[0] = count1;
        res[1] = count2;
        return res;
    }
    
    /**
     * 检查枚举的数和猜测的数 对不对得上
     * cur 0000-9999
     */
    public static boolean check(String cur, List<int[]> sorted) {
        // int cr = 0;// 位置对数字对
        // int ce = 0;// 数字对位置错
        
        // 要求cr == gr ce==ge 且每一个都对 才算猜中了 符合所有的 存入temp中
        for (int[] guess : sorted) {
            int[] res = checkNum(Integer.parseInt(cur), guess[0]);
            int num = guess[0]; // 猜测数字
            int gr = guess[1]; // 位置对数字对
            int ge = guess[2]; // 数字对位置错
            
            // 只要有一个对不上 false跳出循环
            if(res[0] != gr && res[1] != ge){
                return false;
            }
        }
        
        return true;
    }
    
    public static String solve(String[] inputs) {
        // 先按X降序 相同按Y降序 排列
        List<int[]> sorted = new ArrayList<>();
        for (final String input : inputs) {
            // 当前猜的数字
            int curNum = Integer.parseInt(input.split(" ")[0]);
            // 数字对 位置对 的个数
            int x = Integer.parseInt(String.valueOf(input.split(" ")[1].charAt(0)));
            // 数字对 位置错 的个数
            int y = Integer.parseInt(String.valueOf(input.split(" ")[1].charAt(2)));
            int[] temp = new int[]{curNum, x, y};
            sorted.add(temp);
            
            // System.out.println("curNum" + curNum);
            // System.out.println("x" + x);
            // System.out.println("y" + y);
        }
        sorted.sort((a, b) -> {
            return a[1] != b[1] ? Integer.compare(b[1], a[1]) : Integer.compare(b[2], a[2]);
        });
        
        // print
        sorted.forEach(item -> {
            System.out.println(Arrays.toString(item));
        });
        
        String res = "NA"; // 结果
        // 存符合的结果 如果为空或者多个答案 输出NA
        List<String> temp = new ArrayList<>();
        // 暴力枚举
        for (int i = 0; i <= 9999; i++) {
            // 0000-9999
            String cur = String.format("%04d", i);
            // 符合所有猜想 存入结果数组中
            if (check(cur, sorted)) {
                temp.add(cur);
            }
            // System.out.println(cur);
        }
        return res;
    }
    
    static void main() {
        String[] s1 = {"4815 1A1B", "5716 0A1B", "7842 0A1B", "4901 0A0B", "8585 3A0B", "8555 2A1B"};
        // 3585
        System.out.println(solve(s1));
        // 谜底：3 5 8 5
        // 猜测：8 5 8 5
        // 位置 1、2、3 全都对上；只有第一位猜错，数字 8 在剩下的谜底里没有，所以没有 B。
    }
}
