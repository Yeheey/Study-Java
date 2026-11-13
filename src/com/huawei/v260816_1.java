package com.huawei;

public class v260816_1 {
    /*
    19.【26.8.16-1】电池充电时间计算
    题目描述
    电池充电过程并非恒定电流，而是遵循一定的充电曲线。给定电池容量、当前电量百分比、目标电量百分比和充电器最大
    输出电流，请计算充电至目标电量所需的时间。
    充电曲线规则如下:
    1.当电量<20%时，采用预充模式，电流为最大电流的20%
    2.当电量>=20%且<80%时，采用恒流模式，电流为最大电流的100%
    3.当电量>=80%时，采用恒压模式，电流随电量增加而线性衰减:
    电流计算公式:current(soc) = maxCurrent * (1 - (soc - 80) * 0.045)
    
    其中 soc为电量百分比,该公式保证 current(80) = maxCurrent, current(100) = maxCurrent*0.1
    没充batteryCapacity*1%的电量
    根据当前电量所在阶段确定该步进的充电电流，然后累加该步进所需的时间。
    由于恒压阶段电流线性变化，可使用平均电流法计算充电时间:平均电流=(起始电流+结束电流)/2。
    
    输入描述
    batteryCapacity:电池容量(mAh)，整型，范围1000~10000
    maxCurrent:充电器最大输出电流(mA)，整型，范围500~10000
    initialSOC:当前电量百分比(0~100的整数)，整型
    targetSOC:目标电量百分比(initialSOC~100的整数)，整型
    输入为一行，四个整数使用英文逗号分隔:batteryCapacity,maxCurrent,initialSOC,targetSOC
    
    输出描述
    充电至目标电量所需的时间，浮点数四，保留一位小数(四舍五入)。
     */
    static double solve(int batteryCapacity, int maxCurrent, int initialSOC, int targetSOC) {
        double unit = batteryCapacity / 100.0;   // 1% 对应的 mAh
        double res = 0.0;
        
        // 阶段1：预充 [initial, min(target,20))
        if (initialSOC < 20 && targetSOC > initialSOC) {
            int end = Math.min(targetSOC, 20);
            res += (end - initialSOC) * unit / (maxCurrent * 0.2);
        }
        // 阶段2：恒流 [max(initial,20), min(target,80))
        if (targetSOC > 20 && initialSOC < 80) {
            int start = Math.max(initialSOC, 20);
            int end = Math.min(targetSOC, 80);
            res += (end - start) * unit / maxCurrent;
        }
        // 阶段3：恒压 [max(initial,80), target]，平均电流法
        if (targetSOC > 80 && initialSOC < 100) {
            int start = Math.max(initialSOC, 80);
            double i1 = maxCurrent * (1 - (start - 80) * 0.045);
            double i2 = maxCurrent * (1 - (targetSOC - 80) * 0.045);
            double avg = (i1 + i2) / 2;
            res += (targetSOC - start) * unit / avg;
        }
        return Math.round(res * 10) / 10.0;
    }
    
    static void main() {
        System.out.println(solve(3000, 1500, 0, 100));
        // 3.9
        System.out.println(solve(3000, 1500, 70, 85));
        // 0.3
        System.out.println(solve(3000, 1500, 80, 100));
        // 0.7
    }
}
