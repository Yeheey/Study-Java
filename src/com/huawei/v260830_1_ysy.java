package com.huawei;

public class v260830_1_ysy {
/*
    【26.8.30-1】小花获胜的奶茶--【滑动窗口】--7min
    题目描述：
    小菊和小花是好朋友，他们经常一起玩游戏。这天他们玩一个数字游戏，获胜可以获得对方1杯奶茶。
    游戏规则:
    小菊在纸上写了一排数组，小花需要从中选择连续k个数字，使得这k个数字的和最大。
    小花正确找到最大的值就是获胜，小菊则提供1杯奶茶;小花想获胜喝奶茶，请你帮助她。
    
    输入描述：
    参数1:给出的一排数字
    参数2:k
    
    约束:
    ·-2147483648<=单个数字<=21474836472
    ·0<一排数字长度<1063
    ，需小花选择连续的数字个数k，1<=k<=小菊给出的一排数字的长度
    
    输出描述：
    返回连续k个数字的最大和
*/
    
    
    static int solve1(int[] nums, int k) {
        int winSum = 0;
        // 初始化winSum 并赋值给res
        for (int i = 0; i < k; i++) {
            winSum += nums[i];
        }
        int res = winSum;
        
        // 从第2个数开始滑动窗口 WinSum - nums[i] + nums [i+k]
        int i = 0;
        int n = nums.length;
        while (i + k < n) {
            winSum = winSum - nums[i] + nums[i + k];
            if(winSum>=res){
                res = winSum;
            }
            i++;
        }
        
        return res;
    }
    
    static void main() {
        int[] nums = {1, 3, 8, 6, 4, 10};
        int k = 3;
        // output 20
        System.out.println(solve1(nums, k));
        
        int[] nums1 = {-1, -2, -3, -4, -5};
        int k1 = 3;
        // output -6
        System.out.println(solve1(nums1, k1));
    }
}
