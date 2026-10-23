package com.huawei;

import java.util.List;

public class test2 {

/*2.【26.9.6-2】少儿爬山闯关积分游戏(动态规划)
题目描述:
山路一共设有n个关卡，按1~n顺序排列。当前位于1号关卡，必须按照关卡从小到大闯关，且必须到达最后第n号关卡才算寻宝成功。
每个关卡都有对应的宝藏分值(可能为负数)，经过当前关卡即可获得该关卡分值，分数持续累加。

移动规则:
1.从当前关卡，下一次只能在前方1关或者2关处打卡;
2.限制规则:不能连续两次都一次性跳2关，违反该规则的路线无效;
请求出:所有合法路线中，能够收集到的最大累加总分。

输入描述：
参数1:整数n，代表关卡数量  1≤n≤20
参数2:整数数组，依次表示第1~n关的宝藏分值 每关分值:-999≤val≤999

输出描述：
输出一个整数，代表合法路线的最大累加得分。*/
    
    
    static int getMaxScores(int n, List<Integer> nums) {
        int res = nums.getFirst(); // 先存第1关的分数
        if (n == 1) {
            return res;
        } else {
            int minScore = Integer.MIN_VALUE; // 记录一个特别小的分数 用于初始化
            
            int preOneTotal = nums.getFirst(); // 记录前1步总和最大分数
            int preTwoTotal = minScore; // 记录前2步总和最大分数
            int prePreOneTotal = minScore; // 记录前1->1步总和的最大分数
            
            for (int i = 1; i < n; i++) {
                // 如果当前关卡是跳1关过来的 比较1-1 2-1哪个大
                int curOne = Math.max(preOneTotal, preTwoTotal) + nums.get(i);
                // 如果当前关卡是跳2关过来的 只能是1-1-2 则需要判断i！=1
                int curTwo = (i > 1) ? prePreOneTotal + nums.get(i) : minScore;
                // 维护变量
                prePreOneTotal = preOneTotal;
                preOneTotal = curOne;
                preTwoTotal = curTwo;
                
                res = Math.max(curOne, curTwo);
            }
            
            
        }
        return res;
    }
    
    static void main() {
        List<Integer> inputNums = List.of(-10, 60, -5, 92, -2, 100); // output=240
        int n = inputNums.size();
        
        int res = getMaxScores(n, inputNums);
        System.out.print(res);
        
    }
}

/*解题思路
核心思想
每次只能向前跳1关或2关，并且不能连续两次都跳2关。为了判断下一跳是否合法，状态中必须记录“上一次跳了几关"。
定义两个状态，并用滚动变量保存:
onestep:到达当前关卡时，上一次跳1关的最大得分;
twostep:到达当前关卡时，上一次跳2关的最大得分。
计算第i关时，如果最后一步跳1关，则可以从第1-1关的两种状态转移而来;如果最后一步跳2关，则只能从第1-2关且上一跳为1关的状态转移而来。
由于每次只依赖前一关和前两关的少量状态，可以把空间优化到常数级。
*/

/*
算法四步骤
1.起点为第1关，先获得第1关分值。
    2.维护上一关的prevone、prevTwo，分别表示最后跳1关和最后跳2关到达上一关的最大得分。
    3.额外维护prevPrevone，表示最后跳1关到达前两关位置的最大得分。
    4.从第2关开始向后扫描:
curone = max(prevone, prevTwo) + score[i];
curTwo = prevPrevone +score[i],且只在i>=2 时有效。
    5.滚动更新状态，最终返回最后一关两种状态中的较大值。*/
