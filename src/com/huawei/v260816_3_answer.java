package com.huawei;

import java.util.*;

public class v260816_3_answer {
    /*
    21.【26.8.16-3】智能家居模式调度优化 AI
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
    · modes:二维数组，每个子数组nodes[i]-[start_time,end_time,power]，表示第i个智能模式的启动时间、结束时间以及运行它所需要的电功率。
    · start_time,end_time: 取值 0 <= start_time < end_time <= 10^5
    · power:取值1<=power<=10^4
    · 模式数量取值1<=n<=22
    
    输出描述：
    最少需要删除的模式个数。
    */
    
    public static int solve(int maxPower, int[][] modes) {
        int baseDel = 0;
        List<int[]> valid = new ArrayList<>();
        for (int[] mode : modes) {
            if (mode[2] > maxPower) {
                // 单个模式已经超限，必须删除
                baseDel++;
            } else {
                valid.add(mode);
            }
        }
        if (valid.isEmpty()) {
            return baseDel;
        }
        
        TreeSet<Integer> points = new TreeSet<>();
        for (int[] mode : valid) {
            points.add(mode[0]);
            points.add(mode[1]);
        }
        List<Integer> pointList = new ArrayList<>(points);
        Map<Integer, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < pointList.size(); i++) {
            indexMap.put(pointList.get(i), i);
        }
        
        List<Mode> list = new ArrayList<>();
        for (int[] mode : valid) {
            list.add(new Mode(indexMap.get(mode[0]), indexMap.get(mode[1]), mode[2]));
        }
        
        int segmentCount = pointList.size() - 1;
        int[] load = new int[segmentCount];
        int[] best = new int[]{0};
        dfs(0, 0, list, load, maxPower, best);
        return baseDel + (list.size() - best[0]);
    }
    
    private static void dfs(int idx, int kept, List<Mode> list, int[] load, int maxPower, int[] best) {
        if (kept + (list.size() - idx) <= best[0]) {
            return;
        }
        if (idx == list.size()) {
            best[0] = Math.max(best[0], kept);
            return;
        }
        
        Mode cur = list.get(idx);
        boolean ok = true;
        for (int i = cur.l; i < cur.r; i++) {
            if (load[i] + cur.p > maxPower) {
                ok = false;
                break;
            }
        }
        
        if (ok) {
            for (int i = cur.l; i < cur.r; i++) {
                load[i] += cur.p;
            }
            dfs(idx + 1, kept + 1, list, load, maxPower, best);
            for (int i = cur.l; i < cur.r; i++) {
                load[i] -= cur.p;
            }
        }
        
        dfs(idx + 1, kept, list, load, maxPower, best);
    }
    
    private static List<int[]> parseModes(String s) {
        List<int[]> res = new ArrayList<>();
        int i = 0;
        while (i < s.length() && s.charAt(i) != '[') i++;
        i++;
        while (i < s.length()) {
            while (i < s.length() && (s.charAt(i) == '[' || s.charAt(i) == ',' || Character.isWhitespace(s.charAt(i))))
                i++;
            if (i >= s.length() || s.charAt(i) == ']') break;
            int[] arr = new int[3];
            for (int k = 0; k < 3; k++) {
                int sign = 1;
                if (s.charAt(i) == '-') {
                    sign = -1;
                    i++;
                }
                int num = 0;
                while (i < s.length() && Character.isDigit(s.charAt(i))) {
                    num = num * 10 + (s.charAt(i) - '0');
                    i++;
                }
                arr[k] = sign * num;
                while (i < s.length() && (s.charAt(i) == ' ')) i++;
                if (k < 2) {
                    while (i < s.length() && s.charAt(i) == ',') i++;
                }
            }
            res.add(arr);
            while (i < s.length() && s.charAt(i) != '[') {
                if (s.charAt(i) == ']') {
                    i++;
                    break;
                }
                i++;
            }
        }
        return res;
    }
    
    public static void main(String[] args) {
        int maxPower = 10;
        int[][] modes = {{0, 500, 5}, {400, 600, 7}, {550, 700, 4}};
        // 1
        System.out.println(solve(maxPower, modes));
    }
    
    static class Mode {
        int l;
        int r;
        int p;
        
        Mode(int l, int r, int p) {
            this.l = l;
            this.r = r;
            this.p = p;
        }
    }
    
    
}
