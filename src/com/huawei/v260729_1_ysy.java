package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class v260729_1_ysy {
    /*
    【26.7.29-1】能量对撞--【模拟计算】--【二刷用时40min 注意是栈内球和当前球比较 梳理好逻辑再写-50min】
    题目描述
    在一个充满魔法能量的消除游戏中，一排排不同颜色的能量球（代表不同质量/能量值）静止在网格中。当玩家触发消除机制时，相邻的能量球会发生相互碰撞。
    
    消除规则：
    碰撞机制：当两个相邻的能量球相遇时，系统会根据它们的能量值（质量）进行判断。
    强弱判定：如果两个能量球的能量值不同，能量值较低的那个球会被对手的能量波吞噬，从而消除。
    同归于尽：如果两个能量球的能量值完全相同，它们会引发剧烈的能量湮灭反应，导致两者同时被消除。
    幸存条件：只有能量值足够高且未发生碰撞（或碰撞后存活）的能量球，才能留在场上继续等待下一轮消除。
    题目目标：
    给定一排初始的能量球序列，请模拟上述消除过程，找出最终留在场上的所有能量球及其顺序。
    
    约束：
    能量球的个数不超过 1000 个，能量球的能量值是非负整数，最大不超过 10000。
    
    输入描述
    输入共两行：
    第一行输入整数 n，表示能量球数量。
    第二行输入 n 个整数，使用英文逗号分隔，表示初始能量球序列。
    
    输出描述
    输出最终留在场上的所有能量球序列，以数组形式表示，格式为 [b1,b2,...,bm]。
    */
    
    static List<Integer> solve(int len, int[] energies) {
        List<Integer> res = new ArrayList<>();
        // 判断只有一个的情况
        if (len == 1) {
            res.add(energies[0]);
            return res;
        }
        // 用于存放上场的能量球
        Stack<Integer> stack = new Stack<>();
        
        int i = 0;
        while (i < len) {
            // 当前上场球
            int cur = energies[i];
            // 1.如果栈内为空 存入当前存活球 跳出本次循环
            if (stack.isEmpty()) {
                stack.add(cur);
                i++;
                continue;
            }
            // 2.如果栈内非空 弹出栈顶 和当前球比较
            int stackPeek = stack.pop();
            
            if (stackPeek < energies[i]) {
                stack.add(energies[i]);
            }
            if (stackPeek > energies[i]) {
                stack.add(stackPeek);
            }
            
            i++;
        }
        res = stack;
        
        return res;
    }
    
    static void main() {
        int n = 5;
        int[] nums = {3, 5, 5, 2, 3};
        // [3]
        
        int n1 = 5;
        int[] nums1 = {2, 3, 6, 2, 4};
        // [6]
        System.out.println(solve(n, nums));
        System.out.println(solve(n1, nums1));
        
    }
}



