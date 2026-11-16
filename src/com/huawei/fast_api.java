package com.huawei;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class fast_api {
    
    static void main() {
        System.out.println("====速查API====");
        
        System.out.println("====进制互转====");
        // 1.二进制字符串->十进制
        int num1 = Integer.parseInt("1010", 2);
        System.out.println(num1); // 10
        
        // 二进制字符串->大整数BigInteger
        BigInteger big1 = new BigInteger("1111000011110000111100001111000011110000", 2);
        System.out.println(big1);
        
        // 2.八进制字符串->十进制
        int num2 = Integer.parseInt("12", 8);
        System.out.println(num2); // 10
        
        // 八进制字符串->大整数BigInteger
        BigInteger big2 = new BigInteger("777766665555", 8);
        System.out.println(big2);
        
        // 3.十六进制字符串->十进制
        int num3 = Integer.parseInt("FF", 16);
        System.out.println(num3); // 255
        
        // 十六进制字符串->大整数BigInteger
        BigInteger big3 = new BigInteger("1A2B3C4D5E6F1A2B3C4D5E6F", 16);
        System.out.println(big3); // 255
        
        // 4.十进制转二进制字符串
        String bin1 = Integer.toBinaryString(10);
        System.out.println(bin1); // "1010"
        
        // 5.十进制转八进制字符串
        int n3 = 10;
        String oct = Integer.toOctalString(n3);
        System.out.println(oct); // "12"
        
        // 6.十进制转十六进制字符串
        int n = 163;
        String hex = Integer.toHexString(n);
        // long版本：Long.toHexString(n)
        System.out.println(hex); // "a3"
        // 转大写：hex.toUpperCase()
        
        System.out.println("====数组互转====");
        // 一.Integer[] → List<Integer>
        Integer[] arr = {1, 2, 3};
        List<Integer> list = Arrays.asList(arr);
        
        // 二.List<Integer> → Integer[]
        Integer[] arr1 = new Integer[0];
        Integer[] arr2 = list.toArray(arr1);
        
        // 三.int[] → List<Integer>
        // 1.Stream 写法
        int[] arr3 = {1, 2, 3, 4};
        List<Integer> list1 = Arrays.stream(arr3).boxed().toList();
        
        
        // 2.手写 for 循环（面试怕 stream 忘、兼容老版本，稳妥）
        int[] arr4 = {1, 2, 3, 4};
        List<Integer> list2 = new ArrayList<>();
        for (int num : arr) {
            list2.add(num);
        }
        // 或者直接
        list2.addAll(Arrays.asList(arr));
        
        // 四.List<Integer> → int[]
        List<Integer> list3 = Arrays.asList(1, 2, 3, 4);
        int[] arr5 = list3.stream().mapToInt(Integer::intValue).toArray();
        
    }
}
