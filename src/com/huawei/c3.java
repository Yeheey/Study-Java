package com.huawei;

import java.util.*;

public class c3 {
    /*
    【评委评分】--【模拟计算+数组排序compare的使用】--100min--ai写的sorted函数
    题目描述
    一个有N个选手参加比赛，选手编号为1~N（3<=N<=100），有M（3<=M<=10）个评委对选手进行打分。
    打分规则为每个评委对选手打分，最高分10分，最低分1分。
    请计算得分最多的3位选手的编号。如果得分相同，则得分高分值最多的选手排名靠前
    (10分数量相同，则比较9分的数量，以此类推，用例中不会出现多个选手得分完全相同的情况)。
    
    
    输入描述
    第一行为半角逗号分割的两个正整数，第一个数字表示M（3<=M<=10）个评委，第二个数字表示N（3<=N<=100）个选手。
    第2到M+1行是半角逗号分割的整数序列，表示评委为每个选手的打分，0号下标数字表示1号选手分数，1号下标数字表示2号选手分数，依次类推。
    
    输出描述
    选手前3名的编号。
    **注：**若输入为异常，输出-1，如M、N、打分不在范围内。
*/
    
    /**
     * nums 需要重排的编号
     * scores 下标是编号，值是评委打的分，且已经从高到低排好
     * map key 是选手编号，value 是总分
     */
    static int[] sorted(List<Integer> nums, int[][] scores, HashMap<Integer, Integer> map) {
        int[] res = new int[3];
        if (nums == null || nums.size() < 3) {
            return res;
        }
        // ✅️总分高的在前；总分相同就从高分往低分比，先比出 10 分更多的，再比 9 分
        nums.sort((a, b) -> {
            // 1.先比较总分
            int totalCmp = Integer.compare(map.get(b), map.get(a));
            // 2.总分不同 直接返回该有的排序 【nums已经是降序了】
            if (totalCmp != 0) {
                return totalCmp;
            }
            // 3.总分相同时 比较最高值分的个数
            int[] sa = scores[a];
            int[] sb = scores[b];
            for (int i = 0; i < sa.length; i++) {
                // 3.1 实际只需要按位比较即可 不用统计数量
                int cmp = Integer.compare(sb[i], sa[i]);
                if (cmp != 0) {
                    return cmp;
                }
            }
            return 0;
        });
        for (int i = 0; i < 3; i++) {
            res[i] = nums.get(i);
        }
        return res;
    }
    
    static int[] solve(int n, int m, int[][] matrix) {
        int[] res = new int[3];
        // M、N 或评分表本身不合法，按题目输出 -1
        if (m < 3 || m > 10 || n < 3 || n > 100 || matrix == null || matrix.length != m) {
            return new int[]{-1};
        }
        
        // 下标是选手编号 值是得分
        int[][] scores = new int[n + 1][m];
        for (int i = 0; i < m; i++) {
            // 某一行选手数对不上，也是异常输入
            if (matrix[i] == null || matrix[i].length != n) {
                return new int[]{-1};
            }
            for (int j = 0; j < n; j++) {
                // 打分必须在 1~10
                if (matrix[i][j] < 1 || matrix[i][j] > 10) {
                    return new int[]{-1};
                }
                scores[j + 1][i] = matrix[i][j];
            }
            
        }
        
        // 总分+编号
        HashMap<Integer, Integer> map = new HashMap<>();
        // 总分
        List<Integer> totalScores = new ArrayList<>();
        // 把选手分数从高往低排序
        for (int i = 1; i < scores.length; i++) {
            Arrays.sort(scores[i]);
            // Arrays.sort 是升序，和上面要的从高到低相反；不翻的话会先比最低分
            for (int l = 0, r = scores[i].length - 1; l < r; l++, r--) {
                int tmp = scores[i][l];
                scores[i][l] = scores[i][r];
                scores[i][r] = tmp;
            }
            int total = 0;
            for (int s = 0; s < scores[i].length; s++) {
                total += scores[i][s];
            }
            map.put(i, total); // key选手编号 value总分
            totalScores.add(total);
        }
        totalScores.sort((a, b) -> b - a);
        
        int one = totalScores.get(0);
        int two = totalScores.get(1);
        int three = totalScores.get(2);
        int four = 0;
        if (n > 3) {
            four = totalScores.get(3);
        }
        
        
        if (one != two && two != three && three != four) {
            // 前四名不同分 根据分找map的key add进res里面
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                if (value == one) {
                    res[0] = key;
                }
                if (value == two) {
                    res[1] = key;
                }
                if (value == three) {
                    res[2] = key;
                }
            }
        } else {
            // 前四名存在同分
            List<Integer> nums = new ArrayList<>(); // 所有包括前三名分数的编号
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                int key = entry.getKey();
                int value = entry.getValue();
                if (value == one || value == two || value == three) {
                    nums.add(key);
                }
            }
            
            // 把这个分数对应所有编号 拉出来重排
            res = sorted(nums, scores, map);
        }
        
        return res;
    }
    
    static void main() {
        int n = 6; // 选手数
        int m = 4; // 评委数
        int[][] matrix = {{10, 6, 9, 7, 6, 5}, {9, 10, 6, 7, 5, 5}, {8, 10, 6, 5, 10, 9}, {9, 10, 8, 4, 9, 10}};
        // 2,1,5
        System.out.println("res" + Arrays.toString(solve(n, m, matrix)));
    }
}
