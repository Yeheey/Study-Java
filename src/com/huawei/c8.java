package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class c8 {
    /*
    【8.最长的顺子】--【模拟计算+滑动窗口】--30min--【13.斗地主之顺子】
    题目描述
    斗地主起源于湖北十堰房县，据说是一位叫吴修全的年轻人根据当地流行的扑克玩法“跑得快”改编的，如今已风靡整个中国，并流行于互联网上。
    牌型：单顺，又称顺子，最少5张牌，最多12张牌(3…A)不能有2，也不能有大小王，不计花色。
    例如： 3-4-5-6-7-8，7-8-9-10-J-Q，3-4-5-6-7-8-9-10-J-Q-K-A
    可用的牌 3<4<5<6<7<8<9<10<J<Q<K<A<2<B(小王)<C(大王)，每种牌除大小王外有四种花色
    (共有13×4+2张牌)
    
    输入：
    手上有的牌
    已经出过的牌(包括对手出的和自己出的牌)
    
    输出：
    对手可能构成的最长的顺子(如果有相同长度的顺子，输出牌面最大的那一个)，
    如果无法构成顺子，则输出 NO-CHAIN。
    
    输入描述
    输入的第一行为当前手中的牌
    输入的第二行为已经出过的牌
  */
    
    public static String solve(String cur, String out) {
        
        int maxLen = 0; // 维护顺子的最长长度
        
        // 先把所有牌面放map里面
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 2; i < 11; i++) {
            map.put(String.valueOf(i), 4);
        }
        // J-11 Q-12 K-13 A-14
        map.put("11", 4);
        map.put("12", 4);
        map.put("13", 4);
        map.put("14", 4);
        map.put("B", 1);
        map.put("C", 1);
        
        // 把所有的手上牌和出过的牌 换成数组展示
        List<String> list = new ArrayList<>();
        String[] c = cur.split("-");
        String[] o = out.split("-");
        list.addAll(List.of(c));
        list.addAll(List.of(o));
        
        // list中改J-11 Q-12 K-13 A-14
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (s.equals("J")) {
                list.set(i, "11");
            }
            if (s.equals("Q")) {
                list.set(i, "12");
            }
            if (s.equals("K")) {
                list.set(i, "13");
            }
            if (s.equals("A")) {
                list.set(i, "14");
            }
        }
        
        System.out.println(list);
        
        // 用map存其他人手上的牌
        for (String key : list) {
            int value = map.get(key);
            map.put(key, value - 1);
        }
        
        int curLen = 0;
        int end = 0;
        for (int i = 3; i < 15; i++) {
            // 3-14
            int curVal = map.get(String.valueOf(i));
            if (curVal > 0) {
                curLen++;
            } else {
                curLen = 0;
            }
            if (curLen >= maxLen) {
                maxLen = curLen;
                end = i;
            }
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = end - maxLen + 1; i <= end; i++) {
            if (i <= 10) {
                sb.append(i);
            } else if (i == 11) {
                sb.append("J");
            } else if (i == 12) {
                sb.append("Q");
            } else if (i == 13) {
                sb.append("K");
            } else {
                sb.append("A");
            }
            if (i != end) {
                sb.append("-");
            }
        }
        
        return sb.isEmpty() ? "NO-CHAIN" : sb.toString();
    }
    
    static void main() {
        String cur = "3-3-3-3-4-4-5-5-6-7-8-9-10-J-Q-K-A";
        String out = "4-5-6-7-8-8-8";
        // 9-10-J-Q-K-A
        System.out.println(solve(cur, out));
    }
}
