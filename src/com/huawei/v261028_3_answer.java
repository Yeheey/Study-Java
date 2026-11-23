package com.huawei;

public class v261028_3_answer {
    /*
        【AI推理任务并行额度最大值】--answer--单调栈--磊哥说不看
        题目描述
        有n个AI推理任务按序排队，第i个任务需占用vram[i]GB显存。调度器可选定一个任务i为基准，向左右连续合并园相邻任务，即选择一个包含任务i的连续子数组。
        合并条件是:被合并任务的显存占用不低于vram[i]
        已知任务的并行执行额度值为:
        并行执行额度值 = vram[i] × 任务个数
        其中，任务个数为满足合并条件的连续子数组长度，包含基准任务本身。对于给定的任务序列 vram，请计算该序列中可达到的并行执行额度最大值。
        
        输入描述
        第一行输入一个整数n，表示任务数量。
        第二行输入n个整数，以空格分隔，依次表示数组vram中每个任务占用的显存。
        数据范围:1≤n≤10^5,0≤vram[i】≤10^4。
        
        输出描述
        输出一个整数，表示任务的并行执行额度最大值。
        */
    
    public static int solve(int[] inputs) {
        int n = inputs.length;
        // 单调栈里存的是下标，对应显存从栈底到栈顶严格递增。
        // 栈里每个下标的「右边第一个更矮（或一样高）的位置」还没出现，所以先挂着。
        int[] stack = new int[n]; // 模拟栈
        // top == -1 → 栈空
        // top == 0 → 栈里只有 stack [0] 一个元素
        // top == 2 → 栈顶是 stack [2]，栈里一共 3 个元素（0,1,2）
        int top = -1; // 指向数组 stack 里当前最后一个有效元素 ->栈顶元素
        int answer = 0;
        
        // right 扫到 n 是故意多走一步：用一个比任何显存都小的哨兵把栈清空。
        // 否则末尾那些还没遇到更矮任务的基准，永远不会被结算。
        for (int right = 0; right <= n; right++) {
            // 虚拟的 -1 只负责结算，保证零显存任务也能出栈。
            int limit = right == n ? -1 : inputs[right];
            // 相等时也弹出，使栈内显存严格递增，完整区间由较右的同高任务接管。
            // 弹出条件成立时，right 就是栈顶这个基准向右能合并到的边界（不含 right）。
            while (top >= 0 && inputs[stack[top]] >= limit) {
                // 出栈
                int base = inputs[stack[top--]];
                int left = top < 0 ? -1 : stack[top];
                // (left, right) 开区间里的每个任务显存都 >= base，所以都能并进这个基准。
                // 长度 = right - left - 1，基准自己也算在里面。
                // 例：vram = [2,1,5,6,2,3]，基准 5 在下标 2。
                // 向右在下标 4 遇到 2 被挡住，向左在下标 1 的 1 被挡住，
                // 区间是下标 2、3，额度 5 * 2 = 10。
                answer = Math.max(answer, base * (right - left - 1));
            }
            // 入栈
            if (right < n) {
                stack[++top] = right;
            }
        }
        return answer;
    }
    
    static void main() {
        int[] inputs = {2, 1, 5, 6, 2, 3};
        // 10
        System.out.println(solve(inputs));
        
        int[] inputs1 = {4, 4, 4, 4};
        // 16
        System.out.println(solve(inputs1));
    }
}
