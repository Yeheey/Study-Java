package com.huawei;

import java.util.ArrayDeque;
import java.util.Deque;

public class c11_ai_answer {
    /**
     * 通话流量监控：找峰顶三元组 (i,j,k)，使 k-i 最小。
     * 条件：i < j < k，且 nums[j] > nums[i]、nums[j] > nums[k]。
     * 对每个候选峰顶 j，最优的 i / k 分别是左侧、右侧「最近的更小元素」。
     * 用单调栈各扫一遍即可，整体 O(n)。
     */
    static int solve(int[] nums) {
        if (nums == null || nums.length < 3) {
            return -1;
        }
        
        int n = nums.length;
        int[] leftSmaller = nearestSmallerLeft(nums);
        int[] rightSmaller = nearestSmallerRight(nums);
        
        int answer = Integer.MAX_VALUE;
        // 从峰顶出发 遍历
        for (int j = 0; j < n; j++) {
            int i = leftSmaller[j];
            int k = rightSmaller[j];
            // 左右必须都能找到更小的采样点，j 才是合法峰顶
            if (i < 0 || k < 0) {
                continue;
            }
            int width = k - i;
            if (width < answer) {
                answer = width;
            }
        }
        
        if (answer == Integer.MAX_VALUE) {
            return -1;
        }
        return answer;
    }
    
    /** left[j] = 最大的 i < j 且 nums[i] < nums[j]；不存在则为 -1 */
    static int[] nearestSmallerLeft(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        for (int j = 0; j < n; j++) {
            // 栈顶元素 >= 当前值 → 弹出，不可能成为后面元素的“最近更小”
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[j]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                left[j] = -1;
            } else {
                left[j] = stack.peek();
            }
            stack.push(j);
        }
        return left;
    }
    
    /** right[j] = 最小的 k > j 且 nums[k] < nums[j]；不存在则为 -1 */
    static int[] nearestSmallerRight(int[] nums) {
        int n = nums.length;
        int[] right = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();
        
        // 右边倒序遍历
        for (int j = n - 1; j >= 0; j--) {
            while (!stack.isEmpty() && nums[stack.peek()] >= nums[j]) {
                stack.pop();
            }
            if (stack.isEmpty()) {
                right[j] = -1;
            } else {
                right[j] = stack.peek();
            }
            stack.push(j);
        }
        return right;
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
