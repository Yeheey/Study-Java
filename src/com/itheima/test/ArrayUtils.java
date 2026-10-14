package com.itheima.test;

public class ArrayUtils {
    // 拼接字符串 方法1
    static String arrToString(int arr[]) {
        
        String res = new String("");
        res = res + "[";
        
        for (int i = 0; i < arr.length; i++) {
            res = res + (i + 1);
            if (i != arr.length - 1) {
                res += ",";
            }
        }
        
        res = res + "]";
        return res;
    }
    
    // 拼接字符串  方法2
    static String arrToString1(int arr[]) {
        StringBuilder sb = new StringBuilder("[");
        
        // 遍历数组
        for (int i = 0; i < arr.length; i++) {
            sb.append(i+1);
            
            if (i == arr.length - 1) {
                sb.append("]");
            } else {
                sb.append(",");
            }
        }
        return sb.toString();
        
    }
}
