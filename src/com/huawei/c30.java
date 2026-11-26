package com.huawei;

import java.util.Arrays;

public class c30 {
/*
    【30.高矮个子排队】
    题目描述
    现在有一队小朋友，他们高矮不同，我们以正整数数组表示这一队小朋友的身高，如数组{5,3,1,2,3}。
    我们现在希望小朋友排队，以“高”“矮”“高”“矮”顺序排列，每一个“高”位置的小朋友要比相邻的位置高或者相等；每一个“矮”位置的小朋友要比相邻的位置矮或者相等；
    要求小朋友们移动的距离和最小，第一个从“高”位开始排，输出最小移动距离即可。
    例如，在示范小队{5,3,1,2,3}中，{5, 1, 3, 2, 3}是排序结果。
    {5, 2, 3, 1, 3} 虽然也满足“高”“矮”“高”“矮”顺序排列，但小朋友们的移动距离大，所以不是最优结果。
    移动距离的定义如下所示：
    第二位小朋友移到第三位小朋友后面，移动距离为1，若移动到第四位小朋友后面，移动距离为2；
    
    输入描述
    排序前的小朋友，以英文空格的正整数：4 3 5 7 8
    注：小朋友<100个
    
    输出描述
    排序后的小朋友，以英文空格分割的正整数：4 3 7 5 8
    备注：4（高）3（矮）7（高）5（矮）8（高）， 输出结果为最小移动距离，只有5和7交换了位置，移动距离都是1。
    */
    
    public static boolean check(String s) {
        char[] c = s.toCharArray();
        for (final char value : c) {
            if (value < '0' || value > '9') {
                return false;
            }
        }
        return true;
    }
    
    public static int[] solve(String inputs) {
        if (inputs == null || inputs.isEmpty()) {
            return new int[0];
        }
        
        String[] arr = inputs.split(" ");
        
        // 非法输入
        for (final String s : arr) {
            if (!check(s)) {
                return new int[0];
            }
        }
        
        int n = arr.length;
        int[] res = new int[n];
        
        if (arr.length == 1) {
            res[0] = Integer.parseInt(arr[0]);
            return res;
        }
        
        res[0] = Integer.parseInt(arr[0]);
        for (int i = 1; i < arr.length; i++) {
            int pre = res[i - 1];
            int cur = Integer.parseInt(arr[i]);
            if (i % 2 == 0) {
                // i应该比i-1高or相等
                if (cur >= pre) {
                    // 符合情况就加入
                    res[i] = Integer.parseInt(arr[i]);
                    continue;
                }
                int temp = res[i - 1];
                res[i - 1] = Integer.parseInt(arr[i]);
                res[i] = temp;
            } else {
                // i应该比i-1低or相等
                if (cur <= pre) {
                    // 符合情况就加入
                    res[i] = Integer.parseInt(arr[i]);
                    continue;
                }
                int temp = res[i - 1];
                res[i - 1] = Integer.parseInt(arr[i]);
                res[i] = temp;
            }
            
        }
        
        
        return res;
    }
    
    static void main() {
        String inputs1 = "4 1 3 5 2";
        //  4 1 5 2 3
        System.out.println(Arrays.toString(solve(inputs1)));
        
        String inputs2 = "1 1 1 1 1";
        //  1 1 1 1 1
        System.out.println(Arrays.toString(solve(inputs2)));
        
        String inputs3 = "xxx";
        // []
        System.out.println(Arrays.toString(solve(inputs3)));
        
        String inputs4 = "5 3 1 2 3 4 6 8 7 9";
        //  5 1 3 2 4 3 8 6 9 7
        System.out.println(Arrays.toString(solve(inputs4)));
    }
}
