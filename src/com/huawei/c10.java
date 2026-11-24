package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c10 {
    /*
    【手机App防沉迷系统】--【模拟计算】--80min
    题目描述
    智能手机方便了我们生活的同时，也侵占了我们不少的时间。“手机App防沉迷系统”能够让我们每天合理地规划手机App使用时间，在正确的时间做正确的事。
    
    它的大概原理是这样的：
    在一天24小时内，可以注册每个App的允许使用时段
    一个时间段只能使用一个App
    App有优先级，数值越高，优先级越高。
    注册使用时段时，如果高优先级的App时间和低优先级的时段有冲突，则系统会自动注销低优先级的时段
    如果App的优先级相同，则后添加的App不能注册。
    请编程实现，根据输入数据注册App，并根据输入的时间点，返回时间点使用的App名称，如果该时间点没有注册任何App，请返回字符串“NA”。
    
    输入描述
    第一行表示注册的App数量 N（N ≤ 100）
    第二部分包括 N 行，每行表示一条App注册数据
    最后一行输入一个时间点，程序即返回该时间点使用的App
    
    数据说明如下：
    N行注册数据以空格分隔，四个数依次表示：App名称、优先级、起始时间、结束时间
    优先级1~5，数字越大，优先级越高
    时间格式 HH:MM，小时和分钟都是两位，不足两位前面补0
    起始时间需小于结束时间，否则注册不上
    注册信息中的时间段包含起始时间点，不包含结束时间点
    
    输出描述
    输出一个字符串，表示App名称，或NA表示空闲时间
    */
    
    /**
     * 辅助函数-时间大小比较 1 time1先于time2  -1 time2先于time1  0 time1 == time2
     */
    public static int compareTime(String time1, String time2) {
        int h1 = Integer.parseInt(time1.split(":")[0]);
        int h2 = Integer.parseInt(time2.split(":")[0]);
        if (h1 < h2) {
            return 1;
        } else if (h2 < h1) {
            return -1;
        } else {
            int m1 = Integer.parseInt(time1.split(":")[1]);
            int m2 = Integer.parseInt(time2.split(":")[1]);
            return Integer.compare(m2, m1);
        }
    }
    
    /**
     * 辅助函数：看targetStart or targetEnd 任意一个 是否落在startTime—endTime之间
     */
    public static boolean isTrue(String targetStart, String targetEnd, String startTime, String endTime) {
        int res1 = compareTime(startTime, targetStart); // 1 0
        int res2 = compareTime(targetStart, endTime); // 1 0
        
        int res3 = compareTime(startTime, targetEnd); // 1 0
        int res4 = compareTime(targetEnd, endTime); // 1 0
        
        return (res1 >= 0 && res2 >= 0) || (res3 >= 0 && res4 >= 0);
    }
    
    /**
     * 辅助函数-APP优先级比较 app1 在时间点上要先注册
     */
    public static String comparePriority(String[] app1, String[] app2) {
        String name1 = app1[0];
        int p1 = Integer.parseInt(app1[1]);
        String name2 = app2[0];
        int p2 = Integer.parseInt(app2[1]);
        
        return p1 >= p2 ? name1 : name2;
    }
    
    public static String solve(int n, String[][] inputs, String targetTime) {
        if (n == 0) {
            return "NA";
        }
        if (n == 1) {
            return isTrue("00:00", targetTime, inputs[0][2], inputs[0][3]) ? inputs[0][0] : "NA";
        }
        
        // temp 存重新使用的实际时间
        List<String[]> temp = new ArrayList<>();
        temp.add(inputs[0]); // 先存第一个
        
        for (int i = 1; i < n; i++) {
            String[] cur = inputs[i];
            String curS = cur[2]; // 起始时间
            String curE = cur[3]; // 结束时间
            
            String[] last = temp.getLast();
            String lastN = last[0];
            String lastP = last[1];
            String lastS = last[2];
            String lastE = last[3];
            
            // temp.removeLast();
            
            // 检查是否与前一个有重叠
            if (isTrue(curS, curE, lastS, lastE)) {
                // 有重叠 返回保留的APP名称
                String name = comparePriority(last, cur);
                if (name.equals(cur[0])) {
                    // 如果保留的是当前的APP 那么last 的结束时间更新为curStart-1min
                    lastE = curS;
                    temp.removeLast();
                    String[] t = new String[4];
                    t[0] = lastN;
                    t[1] = lastP;
                    t[2] = lastS;
                    t[3] = lastE;
                    temp.add(t);
                    temp.add(cur);
                }
                // 如果保留的是前一个APP 那么 cur 的时间段都舍弃
            } else {
                // 没有重叠 cur直接加入temp
                temp.add(cur);
            }
        }
        // 填充数据略
        // for (String[] arr : temp) {
        //     System.out.println(Arrays.toString(arr));
        // }
        
        
        StringBuilder res = new StringBuilder();
        for (String[] tmp : temp) {
            String tmpN = tmp[0];
            String tmpS = tmp[2];
            String tmpE = tmp[3];
            if (isTrue("00:00", targetTime, tmpS, tmpE)) {
                if (!res.isEmpty()) {
                    res.delete(0, res.length());
                }
                res.append(tmpN);
            }
        }
        
        if (res.isEmpty()) {
            res.append("NA");
        }
        // System.out.println(isTrue("09:20","08:10","09:10"));
        // System.out.println(compareTime("09:20", "09:20"));
        // System.out.println(comparePriority(inputs[0], inputs[1]));
        return res.toString();
    }
    
    static void main() {
        int n1 = 4;
        String[][] inputs1 = {{"App1", "1", "09:00", "10:00"}, {"App2", "2", "09:10", "09:30"}, {"App3", "1", "19:00", "20:00"}, {"App4", "2", "12:10", "12:30"}};
        String targetTime1 = "09:20";
        // APP 2
        System.out.println(solve(n1, inputs1, targetTime1));
        
        int n2 = 2;
        String[][] inputs2 = {{"App1", "1", "09:00", "10:00"}, {"App2", "2", "09:10", "09:30"}};
        String targetTime2 = "09:50";
        // NA
        System.out.println(solve(n2, inputs2, targetTime2));
        
        String targetTime = "09:10";
        // APP2
        System.out.println(solve(n2, inputs2, targetTime));
        
        // 同时撞上更高和更低的优先级，新 App 注册失败，已有的都不注销
        int n16 = 3;
        String[][] inputs16 = {{"App1", "1", "09:00", "10:00"}, {"App2", "3", "11:00", "12:00"}, {"App3", "2", "09:30", "11:30"}};
        String targetTime16 = "09:40";
        // App1
        System.out.println(solve(n16, inputs16, targetTime16));
    }
}
