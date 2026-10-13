package com.itheima.test;

public class BinarySearchDemo {
    static void main() {
       /* 找数组中是否存在target,有则返回索引*/
        int arr []=new int[]{11,22,33,44,100};

        int target = 99; // 目标数
        int start =0; // 左指针
        int end = arr.length-1; // 右指针

while (start<=end){
    int mid = (end+ start)/2;

    if(arr[mid]>target){
//        目标值在左边,缩小右边范围
end = mid-1;
    }else if(arr[mid]<target){
//        目标值在右边，缩小左边范围
        start = mid+1;
    }else {
//        目标值已被找到,退出遍历循环
        System.out.println(arr[mid]);
        break;
    }
}

if(start>end) System.out.println("没找到");


    }
}
