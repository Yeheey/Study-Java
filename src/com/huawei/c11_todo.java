package com.huawei;

import java.util.ArrayDeque;
import java.util.Deque;

public class c11_todo {
    /*
    【11.流量波峰】--80min【太久了】--需要自己在写一遍--【单调栈】
    题目描述
    一线运维人员在对通话流量进行监控，每一段时间内都是出现流量的高峰，流量有高有低形成一个个波峰波谷，运维人员想找到流量变化最快的波峰，你可以帮助他吗？
    给定一个整数数组nums，代表采样点的流量值，请找到满足以下条件的三元组(i,j,k):
    其中i<j<k，nums[j] > nums[i]且nums[j] > nums[k] (即j是峰顶)，
    并找到所有满足条件的三元组中(k-i)的最小值
    
    输入描述
    第一行为n个整数，表示数组中的n个元素，0<= n < =100000
    
    输出描述
    返回所有满足条件的三元组中(k-i)的最小值。若不存在，返回-1。
*/
    
    public static int solve(int[] inputs) {
        int res = Integer.MAX_VALUE; // 找到最小的k-i
        int idx; // 当前循环的下标值
        int k; // 记录单调减函数 末尾
        
        if (inputs.length < 3) {
            return -1;
        }
        if (inputs.length == 3) {
            if (inputs[1] > inputs[0] && inputs[1] > inputs[2]) {
                return 2;
            } else {
                return -1;
            }
        }
        // ★修复2：原来 q.add(队尾) + q.poll(队首) 是「队列轮转」，取到的不是栈顶；
        // 单遍扫描又只能在下降沿算跨度，遇到「平台」(相等值) 会漏掉整段，
        // 而且 i 取 getFirst() 是最远的左点，k-i 必然偏大。
        // 改成两遍单调栈：先算每个 j 左侧最近的严格更小元素 left[j]，
        // 再从右往左算右侧最近的严格更小元素 k，直接取 min(k - left[j])。
        // 变量名 res / idx / k / q 全部沿用，其余结构不动。
        int n = inputs.length;
        // 初始化left 0-n 用-1填满
        int[] left = new int[n];
        for (idx = 0; idx < n; idx++) {
            left[idx] = -1;
        }
        // 单调栈
        Deque<Integer> q = new ArrayDeque<>();
        for (idx = 0; idx < n; idx++) {
            // 栈内非空 且队尾 >=
            while (!q.isEmpty() && inputs[q.peekLast()] >= inputs[idx]) {
                q.pollLast();
            }
            if (!q.isEmpty()) {
                left[idx] = q.peekLast();
            }
            q.addLast(idx);
        }
        q.clear();
        for (idx = n - 1; idx >= 0; idx--) {
            while (!q.isEmpty() && inputs[q.peekLast()] >= inputs[idx]) {
                q.pollLast();
            }
            k = q.isEmpty() ? -1 : q.peekLast();
            if (left[idx] != -1 && k != -1) {
                res = Math.min(res, k - left[idx]);
            }
            q.addLast(idx);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
        
    }
    
    static void main() {
        System.out.println(solve(new int[]{3, 5, 4, 7, 2, 1}));   // 2
        System.out.println(solve(new int[]{4, 3, 2, 1}));         // -1
        System.out.println(solve(new int[]{1, 2, 3, 4, 5}));      // -1
        System.out.println(solve(new int[]{4, 4, 4, 4, 4}));      // -1
        System.out.println(solve(new int[]{1, 3, 2}));            // 2
        System.out.println(solve(new int[]{2, 1}));               // -1
        System.out.println(solve(new int[]{5, 1, 2, 3, 4}));      // -1
        System.out.println(solve(new int[]{1, 2, 2, 1}));         // 3
        System.out.println(solve(new int[]{1, 2, 2, 2, 1}));      // 4
        System.out.println(solve(new int[]{0, 3, 0, 1, 2, 0}));   // 2
    }
}
