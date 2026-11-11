package com.huawei;

public class v260802_1_ysy {
    /*
    【26.8.2-1】IPv4 计算电动车续航里程--【模拟计算】25min
    题目描述
    给定电动车的电池容量 capacity（kWh）、能耗效率 efficiency（kWh/100km）、驾驶场景（城市 / 高速 / 山路，int 类型，分别是 0/1/2），计算该车的有效续航里程（km）。
    核心公式：理论续航里程 = (电池容量 * 容量能耗修正系数) / 能耗效率 * 100；（结果按四舍五入保留 4 位小数）
    有效续航里程 = 理论续航里程 * 总体损耗系数 ，相关系数计算规则如下：
    
    容量能耗修正数 Kp：不同容量下，能效效率不同，分段进行能效效率修正
    零段：电池容量 capacity>=160kWh 时，kp0 = 1(无损耗)
    一段：电池容量 160kWh>capacity>80kWh 时，kp1 = 0.98
    二段：电池容量 capacity<=80kWh 时，kp2 = 0.95
    
    总体损耗系数 kd：基础损耗系数为 kb (0.956) * 场景修正系数 kc（结果按四舍五入保留 4 位小数）
    
    补充约束
    电池容量范围：20≤capacity≤200 （非整数需向下取整）；
    标准能耗效率范围：5≤efficiency≤30；
    驾驶场景仅支持："city"（城市）0/场景修正系数 kc0=1、"highway"（高速）1/场景修正系数 kc1=0.92、"mountain"（山路）2/场景修正系数 kc2=0.85；
    结果要求：有效续航里程需四舍五入为整数，输出里程 km。
    
    输入描述:
    输入为一行，包含三个值：电池容量（可能含小数）、能耗效率（可能含小数）、驾驶场景（整数 0/1/2），以逗号分隔。
    
    输出描述:
    输出一个整数，表示四舍五入后的有效续航里程（单位：km）。
    */
    static double kp;
    static double kc;
    static double kb = 0.956;
    
    static double roundNum(double d) {
        return (double) Math.round(d * 10000) / 10000;
    }
    
    static double solve(double[] input) {
        double res = 0;
        double capacity = Math.floor(input[0]);
        double efficiency = input[1];
        double scene = input[2];
        
        if (capacity >= 160) {
            kp = 1;
        } else if (capacity > 80) {
            kp = 0.98;
        } else {
            kp = 0.95;
        }
        
        double kiloMilter = roundNum((capacity * kp / efficiency * 100));
        
        if (scene == 0) {
            kc = 1;
        } else if (scene == 1) {
            kc = 0.92;
        } else {
            kc = 0.85;
        }
        double kd = roundNum(kb * kc);
        
        res = Math.round(kd * kiloMilter);
        
        return res;
    }
    
    static void main() {
        double[] input = {75.8, 14.3, 1};
        // output:438
        System.out.print(solve(input));
    }
}
