package com.huawei;

import java.util.Arrays;

public class c2 {
    /*
    【运维日志排序】--11min
    题目描述
    [运维工程师]采集到某产品线网运行一天产生的日志n条，现需根据日志时间先后顺序对日志进行排序，日志时间格式为H:M:S.N。
    
    H表示小时(0~23)
    M表示分钟(0~59)
    S表示秒(0~59)
    N表示毫秒(0~999)
    时间可能并没有补全，也就是说，01:01:01.001也可能表示为1:1:1.1。
    
    输入描述
    第一行输入一个整数n表示日志条数，1<=n<=100000，接下来n行输入n个时间。
    
    输出描述
    按时间升序排序之后的时间，如果有两个时间表示的时间相同，则保持输入顺序。
    */
    
    // 转换毫秒数
    static int timeFormatter(String time) {
        int h = Integer.parseInt(time.split(":")[0]);
        int m = Integer.parseInt(time.split(":")[1]);
        int s = Integer.parseInt(time.split(":")[2].split("\\.")[0]);
        int n = Integer.parseInt(time.split("\\.")[1]);
        
        int total = n + s * 1000 + m * 60 * 1000 + h * 60 * 60 * 1000;
        
        return total;
    }
    
    static String[] solve(String[] timeStr) {
        Arrays.sort(timeStr, (a, b) -> timeFormatter(a) - timeFormatter(b));
        return timeStr;
    }
    
    static void main() {
        String[] timeStr = {"01:41:8.9", "1:1:09.211"};
        // 1:1:09.211
        // 01:41:8.9
        System.out.println(Arrays.toString(solve(timeStr)));
        
        String[] timeStr1 = {"23:41:08.023", "1:1:09.211", "08:01:22.0"};
        // 1:1:09.211
        // 08:01:22.0
        // 23:41:08.023
        System.out.println(Arrays.toString(solve(timeStr1)));
    }
}
