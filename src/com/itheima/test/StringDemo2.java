package com.itheima.test;


public class StringDemo2 {
    static void main() {
        int arr1[] = {1, 2, 3, 4, 5};
        String s = ArrayUtils.arrToString(arr1);
        System.out.println(s);
        
        String s1 = ArrayUtils.arrToString1(arr1);
        System.out.println(s1);
        
    }
}
