package com.huawei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

public class v260816_3_ysy {
    /*
     【26.8.16-3】智能家居模式调度优化--【DFS】
     题目描述
     在一个智能家居系统四中，用户可以设置多个“自动化联动模式。每个模式 modes[i]=[start_time,end_time,power]描述如下:
     · 模式在左闭右开区间[start_time,end_time)(单位:秒)内处于运行状态
     · 为了简化处理，start_time、end_time已转化为相对于系统启动初始时间点的秒数
     · 运行期间，每秒消耗固定功率 power
     已知家里电网的最高承受功率max_power。若同一时刻有多个模式同时运行，则总功率为各模式功率之和。如果总功率大于max_power，则会导致跳闸。
     系统允许永久删除(关闭)任意若干个模式，使剩余模式在任意时刻同时运行时永不跳闸。
     请你计算:最少需要删除多少个模式?
     输入描述：
     · max_power:整数代表用户电网能承受的最大总电功率，取值1<=max_power<=10^6
     · modes:二维数组，每个子数组modes[i]=[start_time,end_time,power]，表示第i个智能模式的启动时间、结束时间以及运行它所需要的电功率。
     · start_time,end_time: 取值 0 <= start_time < end_time <= 10^5
     · power:取值1<=power<=10^4
     · 模式数量取值1<=n<=22
     输出描述：最少需要删除的模式个数。
     */
    
    static int maxPower;
    static int[] segPower;       // 每个离散时间段当前的累计功率
    static int[] startId, endId; // 每个模式覆盖的段下标区间 [startId, endId)
    static int[] power;
    static int[][] modesArr;
    static int n; // 模式个数
    static int best; // 选择最大合法的模式个数
    
    static List<List<Integer>> selected;
    
    /**
     * 校验本次添加是否合法
     */
    static boolean check(List<List<Integer>> selected) {
        // 收集所有时间点，TreeSet自动去重+排序
        TreeSet<Integer> timeSet = new TreeSet<>();
        for (List<Integer> mode : selected) {
            int s = mode.get(0);
            int e = mode.get(1);
            timeSet.add(s);
            timeSet.add(e);
        }
        List<Integer> timeList = new ArrayList<>(timeSet);
        // 遍历每一段 [t1, t2)
        for (int i = 0; i < timeList.size() - 1; i++) {
            int t1 = timeList.get(i);
            int t2 = timeList.get(i + 1);
            if (t1 == t2) continue;
            int sum = 0;
            for (List<Integer> mode : selected) {
                int s = mode.get(0);
                int e = mode.get(1);
                int p = mode.get(2);
                // 左闭右开：t1落在区间内
                if (s <= t1 && t1 < e) {
                    sum += p;
                }
            }
            if (sum > maxPower) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 深度优先搜索
     */
    public static void dfs(int idx, List<List<Integer>> selected, int count) {
        // 递归出口
        if (idx == n) {
            best = Math.max(best, count);
            return;
        }
        // 剪枝：当前所选个数+剩余个数 还比最优解小 则停止递归
        if (count + n - idx <= best) {
            return;
        }
        
        // 分支1：尝试选当前模式
        List<List<Integer>> newSelected = new ArrayList<>(selected);
        newSelected.add(Arrays.asList(modesArr[idx][0], modesArr[idx][1], modesArr[idx][2]));
        if (check(newSelected)) {
            dfs(idx + 1, newSelected, count + 1);
        }
        
        // 分支2：不选当前模式（独立分支！不管能不能选，都可以走）
        dfs(idx + 1, selected, count);
    }
    
    public static int solve(int max_power, int[][] modes) {
        maxPower = max_power;
        Arrays.sort(modes, (a, b) -> a[0] - b[0]);
        modesArr = modes; // 按时间左端点排序
        n = modesArr.length;
        best = 0; // 初始化最优值
        selected = new ArrayList<>();
        
        dfs(0, selected, 0);
        
        return n - best;
    }
    
    public static void main(String[] args) {
        int maxPower1 = 10;
        int[][] modes1 = {{0, 500, 5}, {400, 600, 7}, {550, 700, 4}};
        // 最优保留2个，删除1个 → 输出1
        System.out.println(solve(maxPower1, modes1));
        
        int maxPower2 = 10;
        int[][] modes2 = {{0, 300, 5}, {400, 600, 7}, {700, 900, 3}};
        // 三个完全不重叠，全部保留，删除0个
        System.out.println(solve(maxPower2, modes2));
    }
}
