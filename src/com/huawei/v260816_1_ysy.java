package com.huawei;

public class v260816_1_ysy {
    /*
    【26.8.16-1】电池充电时间计算
    题目描述
    电池充电过程并非恒定电流，而是遵循一定的充电曲线。给定电池容量、当前电量百分比、目标电量百分比和充电器最大输出电流，请计算充电至目标电量所需的时间。
    
    充电曲线规则如下：
    
    当电量 <20% 时，采用预充模式，电流为最大电流的 20%
    当电量 ⩾20% 且 <80% 时，采用恒流模式，电流为最大电流的 100%
    当电量 ⩾80% 时，采用恒压模式，电流随电量增加而线性衰减：
    电流计算公式：current(soc)=maxCurrent×(1−(soc−80)×0.045)
    
    其中 soc 为电量百分比，该公式保证 current(80)=maxCurrent，current(100)=maxCurrent×0.1
    每充入 batteryCapacity×1% 的电量，根据当前电量所在阶段确定该步进的充电电流，然后累加该步进所需的时间。
    由于恒压阶段电流线性变化，可使用平均电流法计算充电时间：平均电流 = (起始电流+结束电流)/2。
    
    输入描述
    batteryCapacity: 电池容量(mAh)，整型，范围 1000∼10000
    maxCurrent: 充电器最大输出电流(mA)，整型，范围 500∼10000
    initialSOC: 当前电量百分比(0∼100 的整数)，整型
    targetSOC: 目标电量百分比(initialSOC∼100 的整数)，整型
    
    输出描述
    充电至目标电量所需的时间，浮点数，保留一位小数（四舍五入）
     */
    static double solve(int batteryCapacity, int maxCurrent, int initialSOC, int targetSOC) {
        double total = 0.0; // 累加计时 = 电池容量 / 电流
        double current; // 当前电流
        final double UNIT = batteryCapacity / 100.0; // 1%对应的电量mAh
        
        // 阶段1 预充模式 电量<20% && 保证target>init 才会累计时间
        if (initialSOC < 20 && targetSOC > initialSOC) {
            int endSOC = Math.min(20, targetSOC); // 如果targetSOC>20 终止选20
            current = maxCurrent * 0.2;
            total += (endSOC - initialSOC) * UNIT / current;
        }
        
        // 阶段2 恒流模式 电量<80% && 保证target>=20 继续累加时间
        if (initialSOC < 80 && targetSOC >= 20) {
            int startSOC = Math.max(20, initialSOC);
            int endSOC = Math.min(80, targetSOC);
            total += (endSOC - startSOC) * UNIT / maxCurrent;
        }
        
        // 阶段3 恒压模式 电量<=100% && 保证targe>=80 继续累加时间
        if (initialSOC <= 100 && targetSOC >= 80) {
            final double k = 0.045;
            int startSOC = Math.max(80, initialSOC);
            double start = maxCurrent * (1 - (startSOC - 80) * k);
            double end = maxCurrent * (1 - (targetSOC - 80) * k);
            current = (start + end) / 2;
            total += (targetSOC - startSOC) * UNIT / current;
        }
        
        return (double) Math.round(total * 10) / 10;
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
