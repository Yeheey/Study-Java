package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test1 {

/*1.【26.9.6-1】仓储货物有序出库（栈）
题目描述:
某仓储中心中有一个货物堆放区，货物编号按乱序堆叠存放，只能从顶部取货(后进先出)，管理员需要将货物按编号从大取出。
仓库设有1个临时区，可将主库顶部货物依次转移到临时区，临时区也为堆叠存放(后进先出);主库或临时区顶部货物
可直接出库;其他操作(包括主库一临时区和临时区一主库)均为“暂存”操作;请计算至少需要多少次暂存操作，能保证
有货物按编号从小到大依次出库。

补充说明:
，临时区有且只有一个
·出库顺序必须是严格升序，每件出库货物的编号必须大于前一件
·题目保证所有货物编号互不重复(可能为负数，编号数字不保证连续)
·题目保证总能找到一种合法的操作方案完成有序出库

输入描述:
参数1:一个整数n(1<n<1000)，表示货物的总数量。
参数2:整数数组[a1,a2....an](-1000<ai<1000)，表示货物的编号，从左到右依次对应货物堆底部到顶部。

输出描述:
一个整数，表示最少暂存操作次数。*/

    /*
     * n是总数量
     * nums是货物
     */
    public static int stepCounts(int n, List<Integer> nums) {
        int res = 0;
        List<Integer> mainStack = new ArrayList<>(nums);  // 主仓库
        List<Integer> tempStack = new ArrayList<>(); // 临时仓库
        List<Integer> order = new ArrayList<Integer>(nums); // 要出库的顺序
        
        Collections.sort(order);
        for (int i = 0; i < n; i++) {
            int target = order.get(i);
            if (mainStack.contains(target)) {
                // 在主仓库 挪目标值上面所有箱子到临时区
                while (mainStack.getLast() != target) {
                    // 当最顶上的值 不是目标值 存到暂存区 res++
                    int mainLast = mainStack.removeLast();
                    tempStack.add(mainLast);
                    res++;
                }
                // 如果是目标值 弹出库
                mainStack.removeLast();
                
            } else {
                // 在临时区 挪目标值上面所有箱子到主仓库
                while (tempStack.getLast() != target) {
                    // 当最顶上的值 不是目标值 存到主仓库区 res++
                    int tempLast = tempStack.removeLast();
                    mainStack.add(tempLast);
                    res++;
                }
                // 如果是目标值 弹出库
                tempStack.removeLast();
            }
        }
        
        return res;
        
    }
    
    static void main() {
        int n = 6;
        List<Integer> nums = List.of(6, 5, 2, 4, 3, 1);
        
        System.out.print(stepCounts(n, nums));
    }
    
    
}

/*
* 解题思路
核心思想
主库和临时区都可以看成栈，栈顶是当前唯一能直接操作的位置。目标出库顺序是所有货物编号从小到大排序后的结果。
对于当前要出库的货物target:
，如果它在主库中，就必须先把它上方的货物一个个移动到临时区，直到它到达主库栈顶;
·如果它在临时区中，就必须先把它上方的货物一个个移动回主库，直到它到达临时区栈顶;
·当目标货物到达某个栈顶时，可以直接出库，不计入暂存次数。
每次为了取出当前最小目标货物而移动的阻挡货物都是无法避免的，因此该模拟得到最少暂存次数。
* */
