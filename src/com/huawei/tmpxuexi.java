package com.huawei;

import java.util.HashMap;

public class tmpxuexi {
    HashMap<Character, Integer> mp = new HashMap<>();
    
    int solve(String x) {
        mp.put('0', 0);
        mp.put('1', 1);
        mp.put('2', 2);
        mp.put('3', 3);
        mp.put('4', 4);
        mp.put('5', 5);
        mp.put('6', 6);
        mp.put('7', 7);
        mp.put('8', 8);
        mp.put('9', 9);
        mp.put('A', 10);
        mp.put('B', 11);
        mp.put('C', 12);
        mp.put('D', 13);
        mp.put('E', 14);
        mp.put('F', 15);
        int res = 0;
        for (int i = x.length() - 1; i >= 0; i--) {
            res = res << 4;    // 左移4位，等价 res *=16
            res += mp.get(x.charAt(i));
        }
        return res;
        
    }
    
    void main() {
        System.out.println(solve("FFA05"));
        System.out.println(1);
    }
}
