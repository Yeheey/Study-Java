package com.huawei;

import java.util.HashMap;

public class c26 {
    /*
        【26.货币单位换算】--【模拟计算+map】--50min
        题目描述
        记账本上记录了若干条多国货币金额，需要转换成人民币分（fen），汇总后输出。
        每行记录一条金额，金额带有货币单位，格式为数字+单位，可能是单独元，或者单独分，或者元与分的组合。
        要求将这些货币全部换算成人民币分（fen）后进行汇总，汇总结果仅保留整数，小数部分舍弃。
        元和分的换算关系都是1:100，如下:
            1CNY=100fen（1元=100分）
            1HKD=100cents（1港元=100港分）
            1JPY=100sen（1日元=100仙）
            1EUR=100eurocents（1欧元=100欧分）
            1GBP=100pence（1英镑=100便士）
        汇率表如下：
        CNY	JPY	    HKD	EUR	GBP
        100	1825	123	14	12
        即：100CNY = 10000fen = 1825JPY = 123HKD = 14EUR = 12GBP
        
        输入描述
        第一行输入为N，N表示记录数。0<N<100
        之后N行，每行表示一条货币记录，且该行只会是一种货币。
        
        输出描述
        将每行货币转换成人民币分（fen）后汇总求和，只保留整数部分。
        输出格式只有整数数字，不带小数，不带单位。
        */
    public static long solve(String[] inputs) {
        HashMap<String, Double> map = new HashMap<>();
        map.put("fen", 1.0);
        map.put("CNY", 100.0);
        map.put("HKD", 10000.0 / 123);
        map.put("cents", 100.0 / 123);
        map.put("JPY", 10000.0 / 1825);
        map.put("sen", 100.0 / 1825);
        map.put("EUR", 10000.0 / 14);
        map.put("eurocents", 100.0 / 14);
        map.put("GBP", 10000.0 / 12);
        map.put("pence", 100.0 / 12);
        
        // System.out.println(map);
        double total = 0;
        for (String s : inputs) {
            // s是当前字符串 c是s的char数组
            StringBuilder num1 = new StringBuilder();
            StringBuilder type1 = new StringBuilder();
            StringBuilder num2 = new StringBuilder();
            StringBuilder type2 = new StringBuilder();
            
            char[] c = s.toCharArray();
            for (final char value : c) {
                if (value >= '0' && value <= '9') {
                    if (type1.isEmpty()) {
                        num1.append(value);
                    } else {
                        num2.append(value);
                    }
                }
                if (value >= 'A' && value <= 'Z') {
                    type1.append(value);
                }
                if (value >= 'a' && value <= 'z') {
                    type2.append(value);
                }
            }
            
            // 转换
            long n1 = num1.isEmpty() ? 0 : Integer.parseInt(num1.toString());
            String t1 = type1.toString();
            double d1 = t1.isEmpty() ? 0 : n1 * map.get(t1);
            
            long n2 = 0L;
            String t2 = type2.toString();
            
            if (num2.isEmpty() && !type2.isEmpty()) {
                n2 = Integer.parseInt(num1.toString());
            }
            if (!num2.isEmpty()) {
                n2 = Integer.parseInt(num2.toString());
            }
            
            double d2 = t2.isEmpty() ? 0 : n2 * map.get(t2);
            
            total = total + d1 + d2;
            
        }
        return (long) total;
    }
    
    static void main() {
        String[] s1 = {"20CNY53fen", "53HKD87cents"};
        // 6432
        System.out.println(solve(s1));

        String[] s2 = {"100CNY"};
        // 10000
        System.out.println(solve(s2));

        String[] s3 = {"3000fen"};
        // 3000
        System.out.println(solve(s3));

        String[] s4 = {"123HKD"};
        // 10000
        System.out.println(solve(s4));
        
        
    }
}
