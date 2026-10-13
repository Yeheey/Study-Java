package com.itheima.test;

public class test5 {
    static void main() {
        // 给定两个正序数组arr1和arr2，合并数组，并找出合并之后数组的中位数

        int arr1[] = {1, 3, 5, 7, 9};
        int arr2[] = {2, 4};


        double result = findNum(arr1, arr2);
        System.out.println("结果是" + result);
    }

    public static double findNum(int[] arr1, int[] arr2) {
        double res = 0;

        int len = arr1.length + arr2.length;
        int arr[] = new int[len];

        // 合并数组
        for (int i = 0; i < len; i++) {
            if (i < arr1.length && i >= 0) {
                arr[i] = arr1[i];
            } else {
                arr[i] = arr2[i - arr1.length];
            }
        }

        // 双指针，用于排序
        int index1 = 0;
        int index2 = 0;

        for (int i = 0; i < len; i++) {
            // 如果arr1更短
            if (index1 == arr1.length) {
                // 直接拼接剩余的arr2
                arr[i] = arr2[index2];
                index2++;
                continue;
            }
            // 如果arr2更短
            if (index2 == arr2.length) {
                // 直接拼接剩余的arr1
                arr[i] = arr1[index1];
                index1++;
                continue;
            }
            if (arr1[index1] <= arr2[index2]) {
                arr[i] = arr1[index1];
                index1++;
            } else {
                arr[i] = arr2[index2];
                index2++;
            }
        }

        if (len % 2 == 0) {
            int mid = len / 2;
            res = (arr[mid - 1] + arr[mid]) / 2.0;
        } else {
            int mid = (len - 1) / 2;
            res = arr[mid]/1.0;
        }

        return res;
    }
}
