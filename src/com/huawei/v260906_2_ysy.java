package com.huawei;

public class v260906_2_ysy {
    /*
    【26.9.6-2】少儿爬山闯关积分游戏--【动态规划】类似背包问题--看了答案做的
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
    输出一个整数，代表合法路线的最大累加得分。
    */
    
    static int getMaxScores(int[] nums) {
        int res = nums[0]; // 先存第1关的分数
        int n = nums.length;
        
        if (n == 1) {
            return res;
        } else {
            // 初始化一个很小的分数
            int minScore = Integer.MIN_VALUE;
            int preOne = nums[0]; // 走一步的最大总和
            int preTwo = minScore;// 走两步的最大总和
            int prePreOne = minScore;// 走一步再走一步的最大总和
            
            // 遍历整个过程
            for (int i = 1; i < n; i++) {
                int curOne = Math.max(preOne, preTwo) + nums[i];
                int curTwo = (i == 1) ? minScore : prePreOne + nums[i];
                
                res = Math.max(curOne, curTwo);
                
                // 维护变量
                prePreOne = preOne;
                preOne = curOne;
                preTwo = curTwo;
            }
            
        }
        return res;
    }
    
    static void main() {
        int[] c1 = {-10, 60, -5, 92, -2, 100}; // 240
        int[] c2 = {10, -100, 200}; // 210
        int[] c3 = {-5, -1, -3};
        int[] c4 = {1, 2, 3, 4, 5};
        int[] c5 = {10, 20}; // 只能走一步：30
        System.out.println(getMaxScores(c1)); // 240
        System.out.println(getMaxScores(c2)); // 210
        System.out.println(getMaxScores(c3)); // -8
        System.out.println(getMaxScores(c4)); // 15
        System.out.println(getMaxScores(c5)); // 30
    }
}
