package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c10 {
    /*
    【手机App防沉迷系统】--【模拟计算】--80min--AI帮助修复+题意理解有偏差
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
     * 辅助函数:时间转化为分钟
     */
    public static int formatTime(String time) {
        int hour = Integer.parseInt(time.split(":")[0]);
        int min = Integer.parseInt(time.split(":")[1]);
        return hour * 60 + min;
    }
    
    /**
     * 辅助函数：某个时间点是否落在 [startTime, endTime) 内
     * 包含起始时间，不包含结束时间
     */
    public static boolean overlap(String target, String startTime, String endTime) {
        int start = formatTime(startTime);
        int end = formatTime(endTime);
        int ta = formatTime(target);
        // ✅️ 结束时间是开区间，10:00-11:00 与 11:00-12:00 在 11:00 处不算重叠
        return ta >= start && ta < end;
    }
    
    /**
     * 两个时段是否重叠。只看端点会漏掉「新时段把旧时段整个包住」，
     * 也会把仅仅相接的结束点当成冲突。
     */
    public static boolean rangeOverlap(String start1, String end1, String start2, String end2) {
        return formatTime(start1) < formatTime(end2) && formatTime(start2) < formatTime(end1);
    }
    
    public static String solve(int n, String[][] inputs, String targetTime) {
        if (n == 0) {
            return "NA";
        }
        if (n == 1) {
            return overlap(targetTime, inputs[0][2], inputs[0][3]) ? inputs[0][0] : "NA";
        }
        
        // 用于存放合法的注册APP
        List<String[]> temp = new ArrayList<>();
        // 起始时间不小于结束时间，这条注册不上
        if (formatTime(inputs[0][2]) < formatTime(inputs[0][3])) {
            temp.add(inputs[0]);
        }
        for (int i = 1; i < n; i++) {
            String[] curArr = inputs[i];
            String curP = curArr[1]; // app优先级
            String curS = curArr[2]; // 起始时间
            String curE = curArr[3]; // 结束时间
            
            if (formatTime(curS) >= formatTime(curE)) {
                continue;
            }
            
            // 必须和全部已注册时段比较，不能只看最后一条
            List<String[]> hits = new ArrayList<>();
            int maxPriority = -1;
            for (String[] reg : temp) {
                if (!rangeOverlap(curS, curE, reg[2], reg[3])) {
                    continue;
                }
                hits.add(reg);
                maxPriority = Math.max(maxPriority, Integer.parseInt(reg[1]));
            }
            
            // 不高于冲突中的最高优先级：同优先级或更低，整条失败，已有时段也不注销
            if (Integer.parseInt(curP) <= maxPriority) {
                continue;
            }
            
            // 高于每一个冲突时段，这些低优先级整段注销，再注册当前 App
            temp.removeAll(hits);
            temp.add(curArr);
        }
        
        StringBuilder sb = new StringBuilder();
        temp.forEach(item -> {
            if (overlap(targetTime, item[2], item[3])) {
                sb.append(item[0]);
            }
        });
        if (sb.isEmpty()) {
            sb.append("NA");
        }
        return sb.toString();
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
        
        int n11 = 3;
        String[][] inputs11 = {{"App1", "3", "09:00", "10:00"}, {"App2", "1", "11:00", "12:00"}, {"App3", "2", "09:30", "11:30"}};
        String targetTime11 = "11:10";
        // App2
        System.out.println(solve(n11, inputs11, targetTime11));
        
        // 一条高优先级要同时注销所有冲突的低优先级
        int n12 = 3;
        String[][] inputs12 = {{"App1", "1", "09:00", "10:00"}, {"App2", "1", "10:30", "11:30"}, {"App3", "2", "09:30", "11:00"}};
        String targetTime12 = "09:10";
        // NA
        System.out.println(solve(n12, inputs12, targetTime12));
        
        int n13 = 3;
        String[][] inputs13 = {{"App1", "1", "09:00", "10:00"}, {"App2", "1", "10:30", "11:30"}, {"App3", "2", "09:30", "11:00"}};
        String targetTime13 = "09:40";
        // App3
        System.out.println(solve(n13, inputs13, targetTime13));
        
        // 非法时段不能占住时间，后面的合法注册要能成功
        int n14 = 2;
        String[][] inputs14 = {{"Bad", "1", "09:00", "09:00"}, {"App2", "1", "09:00", "10:00"}};
        String targetTime14 = "09:30";
        // App2
        System.out.println(solve(n14, inputs14, targetTime14));
    }
}
