package com.itheima.test;

public class StringDemo1 {
    static void main() {
        String s1 = new String("yusiying");
        String s2 = new String("YuSiYing");

        boolean res1 = s1.equals(s2);
        boolean res2 = s1.equalsIgnoreCase(s2);

        System.out.println(res1);
        System.out.println(res2);
    }
}
