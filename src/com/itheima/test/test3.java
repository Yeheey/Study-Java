package com.itheima.test;

public class test3 {
    static void main() {
        /*求数组中最大值*/
        int arr[] = new int[]{33,5,22,44,55};
        int max=arr[0];

        for (int i = 0; i < arr.length; i++) {
            if(arr[i]>=max){
                max = arr[i];
            }
        }
        System.out.println("最大值"+max);
    }
}
