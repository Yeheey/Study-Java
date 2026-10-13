package com.itheima.test;

import java.util.Arrays;
import java.util.Random;

public class test4 {
    static void main() {
        //  打乱数组顺序
        int arr [] = new int[]{1,2,3,4,5,6,7,8,9,10};

        Random r = new Random();

        for (int i = 0; i < arr.length; i++) {
            int rIndex = r.nextInt(arr.length);
            int temp = arr [i];  // 暂存一个数
            arr[i] = arr[rIndex];
            arr[rIndex]= temp;
        }

        System.out.println(Arrays.toString(arr));
    }
}
