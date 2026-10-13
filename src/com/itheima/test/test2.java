package com.itheima.test;

import java.util.Scanner;

public class test2 {
    static void main() {
//        查找数组中的target
        int arr [] = {1,2,3,4,5,99,1777};

        Scanner sc =new Scanner(System.in);
        System.out.println("请输入一个数：");
        int a = sc.nextInt();

//        for (int i = 0; i < arr.length; i++) {
//            if ( a == arr[i]){
//                System.out.println( i);
//                break;
//            } else{
//                System.out.println("null");
//                break;
//
//            }
//        }
        int left = 0;
        int right = arr.length -1 ;

        while(left<=right){
            int mid = (left+right)/2;
            if(arr[mid] == a) {
                System.out.println(mid);
                break;
            }
            else if(arr[mid]>a){
                right = mid-1;
            }
            else{
                left=mid+1;
            }
        }
        if(left>right) System.out.println("no exist");

    }

}
