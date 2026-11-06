package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class v260729_1 {
    /*
    27.【26.7.29-1】能量对撞
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
    
    static List<Integer> solve(int n, int[] energies) {
        // 栈：保存当前存活、还没发生碰撞的能量球，栈顶就是当前最右侧的球
        List<Integer> stack = new ArrayList<>();
        
        // 遍历每一个新来的能量球（从左到右依次处理）
        for (int energy : energies) {
            // alive：标记当前这个新来的球，最后能不能活下来、入栈
            boolean alive = true;
            
            // 只要栈不为空，并且当前球还活着，就持续和栈顶碰撞
            while (!stack.isEmpty() && alive) {
                int top = stack.getLast(); // 拿到左边最近存活的球（栈顶）
                
                if (top < energy) {
                    // 左边的栈顶更小 → 栈顶被吞噬、删掉；当前球还活着，继续撞新的栈顶
                    stack.removeLast();
                } else if (top == energy) {
                    // 两者相等 → 同归于尽：栈顶删掉，当前球直接死亡
                    stack.removeLast();
                    alive = false;
                } else {
                    // top > energy：左边更大，当前小球被吃掉，当前球死亡，停止碰撞
                    alive = false;
                }
            }
            
            // 如果循环结束后，当前球还活着，就压入栈
            if (alive) {
                stack.add(energy);
            }
        }
        return stack;
    }
    
    static void main() {
        int n = 5;
        int[] nums = {3, 5, 5, 2, 3};
        System.out.print(solve(n, nums));
    }
}



