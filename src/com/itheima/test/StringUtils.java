package com.itheima.test;

public class StringUtils {
    static String encryption(String name) {
        // 只保留用户名第一个字符,剩下用“***”代替
        String initName = name;
        
        // 方法1：char
        char firstName = initName.charAt(0);
        String res1 = firstName + "***";
        //   System.out.print(res1);
        
        // 方法2：substring(0,1)
        String firstName2 = initName.substring(0, 1);
        String res2 = firstName2 + "***";
        // System.out.println(res2);
        
        return res2;
    }
}
