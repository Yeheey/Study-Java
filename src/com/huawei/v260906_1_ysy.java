package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class v260906_1_ysy {
    /*
    【26.9.6-1】仓储货物有序出库--【模拟计算+栈】--52min
    题目描述:
    某仓储中心中有一个货物堆放区，货物编号按乱序堆叠存放，只能从顶部取货(后进先出)，管理员需要将货物按编号从大取出。
    仓库设有1个临时区，可将主库顶部货物依次转移到临时区，临时区也为堆叠存放(后进先出);
    主库或临时区顶部货物可直接出库;
    其他操作(包括主库一临时区和临时区一主库)均为“暂存”操作;
    请计算至少需要多少次暂存操作，能保证有货物按编号从小到大依次出库。
    
    补充说明:
    ·临时区有且只有一个
    ·出库顺序必须是严格升序，每件出库货物的编号必须大于前一件
    ·题目保证所有货物编号互不重复(可能为负数，编号数字不保证连续)
    ·题目保证总能找到一种合法的操作方案完成有序出库
    
    输入描述:
    参数1:一个整数n(1<n<1000)，表示货物的总数量。
    参数2:整数数组[a1,a2....an](-1000<ai<1000)，表示货物的编号，从左到右依次对应货物堆底部到顶部。
    
    输出描述:
    一个整数，表示最少暂存操作次数。
    */
    
    /**
     * n是总数量
     * nums是货物
     */
    public static int stepCounts(int n, int[] nums) {
        List<Integer> order = new ArrayList<>(); // 出库顺序
        for (final int num : nums) {
            order.add(num);
        }
        
        Collections.sort(order, (a, b) -> a - b);
        // System.out.println("出库顺序" + order);
        
        Stack<Integer> stack1 = new Stack<>(); // 主仓库
        Stack<Integer> stack2 = new Stack<>(); // 临时区
        
        for (final int num : nums) {
            stack1.add(num);
        }
        // System.out.println("主仓库堆放" + stack1);
        
        int res = 0;
        int idx = 0; // 出库的货物
        // 出库一个 order删一个 直到空了
        while (!order.isEmpty() && idx < n) {
            // 当前要出库的值 出库了 则idx++
            int target = order.get(idx);
            
            boolean isExist1 = stack1.contains(target); // 目标值在主仓库
            boolean isExist2 = stack2.contains(target); // 目标值在临时区
            
            // 在主仓库
            if (isExist1) {
                while (stack1.peek() != target) {
                    stack2.add(stack1.pop());
                    res++;
                }
                // 是目标值 弹出 出库
                stack1.pop();
                idx++;
            } else if (isExist2) {
                while (stack2.peek() != target) {
                    stack1.add(stack2.pop());
                    res++;
                }
                // 是目标值 弹出 出库
                stack2.pop();
                idx++;
            }
            
            // Integer cur1 = null; // 主仓库的栈顶
            // Integer cur2 = null; // 临时区的栈顶
            //
            // if (!stack1.isEmpty()) {
            //     cur1 = stack1.peek();
            // }
            // if (!stack2.isEmpty()) {
            //     cur2 = stack2.peek();
            // }
            
            // // 在主仓库
            // if (isExist1) {
            //     while (true) {
            //         if (cur1 != target) {
            //             stack2.add(stack1.pop());
            //             if (!stack1.isEmpty()) {
            //                 cur1 = stack1.peek();
            //             }
            //             if (!stack2.isEmpty()) {
            //                 cur2 = stack2.peek();
            //             }
            //             res++;
            //         } else {
            //             // 是目标值 弹出 出库
            //             stack1.pop();
            //             idx++;
            //             break;
            //         }
            //     }
            // }
            //
            // // 在临时区
            // if (isExist2) {
            //     while (true) {
            //         if (cur2 != target) {
            //             stack1.add(stack2.pop());
            //             if (!stack1.isEmpty()) {
            //                 cur1 = stack1.peek();
            //             }
            //             if (!stack2.isEmpty()) {
            //                 cur2 = stack2.peek();
            //             }
            //             res++;
            //         } else {
            //             // 是目标值 弹出 出库
            //             stack2.pop();
            //             idx++;
            //             break;
            //         }
            //     }
            // }
        }
        
        return res;
    }
    
    static void main() {
        int n = 6;
        int[] nums = {6, 5, 2, 4, 3, 1};
        System.out.println(stepCounts(n, nums));// 3
        System.out.println(stepCounts(6, new int[]{6, 5, 2, 4, 3, 1})); // 3
        System.out.println(stepCounts(3, new int[]{1, 2, 3})); // 2
        System.out.println(stepCounts(4, new int[]{2, 3, 4, 5})); // 3
        System.out.println(stepCounts(4, new int[]{4, 3, 2, 1})); // 0
        System.out.println(stepCounts(5, new int[]{5, 4, 3, 2, 1})); // 0
        System.out.println(stepCounts(2, new int[]{2, 1})); // 0
        System.out.println(stepCounts(2, new int[]{1, 2})); // 1
    }
}
