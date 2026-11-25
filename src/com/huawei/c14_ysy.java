package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class c14_ysy {
    /*
    【14.异常的打卡记录】--【模拟计算】--80min
    ❌️不可以用map做 题目要求按原顺序输出
    题目描述
    考勤记录是分析和考核职工工作时间利用情况的原始依据，也是计算职工工资的原始依据，为了正确地计算职工工资和监督工资基金使用情况，公司决定对员工的手机打卡记录进行异常排查。
    
    如果出现以下两种情况，则认为打卡异常：
    1.实际设备号与注册设备号不一样;
    2.或者，同一个员工的两个打卡记录的时间小于60分钟并且打卡距离超过5km。
    给定打卡记录的[字符串数组]clockRecords（每个打卡记录组成为：工号;时间（分钟）;打卡距离（km）;实际设备号;注册设备号），返回其中异常的打卡记录（按输入顺序输出）。
    
    输入描述
    第一行输入为N，表示打卡记录数；
    之后的N行为打卡记录，每一行为一条打卡记录。
    
    备注
    clockRecords 长度 ≤ 1000
    clockRecords[i] 格式：{id},{time},{distance},{actualDeviceNumber},{registeredDeviceNumber}
    id 由6位数字组成
    time 由整数组成，范围为0~1000
    distance 由整数组成，范围为0~100
    actualDeviceNumber 与registeredDeviceNumber 由思维大写字母组成
    
    输出描述
    输出异常打卡记录。
*/
    
    /**
     *  辅助函数 - 两条记录是否异常 true为异常
     *  两个打卡记录的时间小于60分钟并且打卡距离超过5km
     */
    static boolean isError(String[] arr1, String[] arr2) {
        int time1 = Integer.parseInt(arr1[1]);
        int time2 = Integer.parseInt(arr2[1]);
        int dis1 = Integer.parseInt(arr1[2]);
        int dis2 = Integer.parseInt(arr2[2]);
        return Math.abs(time1 - time2) < 60 && Math.abs(dis1 - dis2) > 5;
    }
    
    /**
     * 辅助函数 - 数组转字符串
     * */
    static String format(String[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            String s = arr[i];
            sb.append(s);
            if (i != arr.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    /**
     * 辅助函数 - 比较actualDeviceNumber 与registeredDeviceNumber
     * */
    public static boolean compare(String[] record) {
        String actualDeviceNumber = record[3];
        String registeredDeviceNumber = record[4];
        return actualDeviceNumber.equals(registeredDeviceNumber);
    }
    
    
    public static String solve(int n, String[][] clockRecords) {
        // System.out.println(isError(clockRecords[0], clockRecords[1]));
        if (n == 0) {
            return "null";
        }
        if (n == 1) {
            if (compare(clockRecords[0])) {
                return "null";
            } else {
                return format(clockRecords[0]);
            }
        }
        
        // 1.按照工号存每条记录
        HashMap<String, List<String[]>> map = new HashMap<>();
        for (final String[] clockRecord : clockRecords) {
            String id = clockRecord[0];
            
            if (map.containsKey(id)) {
                List<String[]> temp = new ArrayList<>(map.get(id));
                temp.add(clockRecord);
                map.put(id, temp);
            } else {
                List<String[]> temp = new ArrayList<>();
                temp.add(clockRecord);
                map.put(id, temp);
            }
        }
        
        // 打印map
        // map.forEach((k, v) -> {
        //     v.forEach(arr -> {
        //         System.out.println(k);
        //         System.out.println(Arrays.toString(arr));
        //     });
        // });
        
        // 2.根据id遍历每条记录
        List<String[]> abnormal = new ArrayList<>(); // 存所有异常的数据
        
        for (Map.Entry<String, List<String[]>> entry : map.entrySet()) {
            String id = entry.getKey();
            List<String[]> record = entry.getValue();
            
            // 3.每个用户的记录
            int len = record.size();
            boolean[] visited = new boolean[len]; // 异常的记录 标记为true
            for (int i = 0; i < record.size(); i++) {
                // 先看本单条是否异常
                String[] cur = record.get(i);
                if (!compare(cur)) {
                    visited[i] = true;
                }
                if (!visited[i]) {
                    // 如果正常 再比较i之前所有的值是否有异常 如果有 两条记录都标记异常
                    for (int j = 0; j < i; j++) {
                        String[] old = record.get(j);
                        boolean isAbnormal = isError(old, cur);
                        if (isAbnormal) {
                            visited[i] = true;
                            visited[j] = true;
                        }
                    }
                }
                
            }
            
            // 4.收集所有异常数据
            for (int v = 0; v < len; v++) {
                if (!visited[v]) {
                    continue;
                }
                // 异常数据加入abnormal
                abnormal.add(record.get(v));
            }
        }
        
        // 5.abnormal转字符串输出
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < abnormal.size(); i++) {
            String[] tmp = abnormal.get(i);
            sb.append(format(tmp));
            if (i != abnormal.size() - 1) {
                sb.append(";");
            }
        }
        
        // 6.sb空输出null
        if (sb.isEmpty()) {
            sb.append("null");
        }
        return sb.toString();
    }
    
    static void main() {
        int n = 2;
        String[][] clockRecords = {{"100000", "10", "1", "ABCD", "ABCD"}, {"100000", "50", "10", "ABCD", "ABCD"}};
        // 100000,10,1,ABCD,ABCD;100000,50,10,ABCD,ABCD
        System.out.println(solve(n, clockRecords));
        
        int n1 = 2;
        String[][] clockRecords1 = {{"100000", "10", "1", "ABCD", "ABCD"}, {"100000", "80", "10", "ABCE", "ABCE"}};
        // null
        System.out.println(solve(n1, clockRecords1));
        
        int n2 = 2;
        String[][] clockRecords2 = {{"100001", "10", "1", "ABCD", "ABCD"}, {"100002", "10", "1", "ABCD", "ABCD"}, {"100001", "50", "10", "ABCD", "ABCD"}};
        // "100001,10,1,ABCD,ABCD;100001,50,10,ABCD,ABCD"
        System.out.println(solve(n2, clockRecords2));
        
    }
}
