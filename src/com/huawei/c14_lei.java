package com.huawei;

import java.util.*;

public class c14_lei {
    /*
    【异常的打卡记录】--
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
    
    
    static Map<String, String[]> mp = new HashMap<>();   // ID :(time,距离，实际设备号，注册设备号)
    
    public static String solve(int n, String[][] clockRecords) {
        List<String[]> tmpRes = new ArrayList<>();
        mp.clear();
        for (int i = 0; i < clockRecords.length; i++) {
            String[] clockRecord = clockRecords[i]; //   ID ,time,距离，实际设备号，注册设备号
            boolean flag1 = !clockRecord[3].equals(clockRecord[4]); // 设备不合法
            String ID = clockRecord[0];
            if (!mp.containsKey(ID)) {
                // 从未打过卡
                boolean flag2 = Integer.parseInt(clockRecord[2]) > 5; // 超过5km
                boolean flag3 = Integer.parseInt(clockRecord[1]) < 60; // 时间小于60
                if (flag1 || (flag2 && flag3)) {
                    // 不合法，把记录存下来
                    tmpRes.add(clockRecord);
                } else {
                    // 符合规则，允许打卡，并更新
                    String[] tmp = new String[4];
                    tmp[0] = clockRecord[1];
                    tmp[1] = clockRecord[2];
                    tmp[2] = clockRecord[3];
                    tmp[3] = clockRecord[4];
                    mp.put(ID, tmp);
                }
            } else {
                // 打过卡 需要判断设备和距离还有时间
                int x = Integer.parseInt(clockRecord[2]);
                int y = Integer.parseInt(mp.get(ID)[1]);
                boolean flag2 = Math.abs(x - y) > 5; // 距离判断
                int time1 = Integer.parseInt(clockRecord[1]);
                int time2 = Integer.parseInt(mp.get(ID)[0]);
                boolean flag3 = Math.abs(time1 - time2) < 60;
                if (flag1 || (flag2 && flag3)) {
                    // 不合法，把记录存下来
                    tmpRes.add(clockRecord);
                } else {
                    // 符合规则，允许打卡，并更新
                    String[] tmp = new String[4];
                    tmp[0] = clockRecord[1];
                    tmp[1] = clockRecord[2];
                    tmp[2] = clockRecord[3];
                    tmp[3] = clockRecord[4];
                    mp.put(ID, tmp);
                }
            }
        }
        for (int k = 0; k < tmpRes.size(); k++) {
            System.out.println(Arrays.toString(tmpRes.get(k)));
        }
        return "";
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
        
    }
}
