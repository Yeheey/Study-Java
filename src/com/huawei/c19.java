package com.huawei;

import java.util.Arrays;

public class c19 {
    /*
        【19.流水线】--【模拟计算+排序】--12min
        题目描述
        一个工厂有m条流水线，来并行完成n个独立的作业，该工厂设置了一个调度系统，在安排作业时，总是优先执行处理时间最短的作业。
        现给定流水线个数m，需要完成的作业数n, 每个作业的处理时间分别为t1,t2…tn。请你编程计算处理完所有作业的耗时为多少？
        当n>m时，首先处理时间短的m个作业进入流水线，其他的等待，当某个作业完成时，依次从剩余作业中取处理时间最短的进入处理。
        
        输入描述
        第一行为2个整数（采用空格分隔），分别表示流水线个数m和作业数n；
        
        第二行输入n个整数（采用空格分隔），表示每个作业的处理时长t1,t2…tn。
            0< m,n<100，0<t1,t2…tn<100。
        注：保证输入都是合法的。
        
        输出描述
        输出处理完所有作业的总时长。
        */
    
    /**
     * m个流水线
     * n个作业
     * times是每个作业处理时长 优先时间短的
     */
    
    public static int solve(int m, int n, int[] times) {
        // 处理作业 按时间 升序排
        Arrays.sort(times);
        
        int[] total = new int[m]; // 每一个流水线总时长
        // 遍历每一个作业 塞入当前total最小和里面
        // 如果和相等 那就塞下标最小的里面 其实会一直放第一个
        // 放完重新排序total
        for (int i = 0; i < n; i++) {
            Arrays.sort(total);
            total[0] += times[i];
        }
        Arrays.sort(total);
        return total[m-1];
    }
    
    static void main() {
        int m1 = 3, n1 = 5;
        int[] times1 = {8, 4, 3, 2, 10};
        System.out.println(solve(m1, n1, times1)); // 13  · 你的示例
        
        int m2 = 5, n2 = 3;
        int[] times2 = {8, 4, 3};
        System.out.println(solve(m2, n2, times2)); // 8   · n<m，只启动3条线
        
        int m3 = 3, n3 = 3;
        int[] times3 = {8, 4, 3};
        System.out.println(solve(m3, n3, times3)); // 8   · n==m
        
        int m4 = 1, n4 = 5;
        int[] times4 = {8, 4, 3, 2, 10};
        System.out.println(solve(m4, n4, times4)); // 27  · m=1串行=求和
        
        int m5 = 3, n5 = 1;
        int[] times5 = {7};
        System.out.println(solve(m5, n5, times5)); // 7   · n=1
        
        int m6 = 1, n6 = 1;
        int[] times6 = {9};
        System.out.println(solve(m6, n6, times6)); // 9   · 最小规模
        
        int m7 = 2, n7 = 6;
        int[] times7 = {5, 5, 5, 5, 5, 5};
        System.out.println(solve(m7, n7, times7)); // 15  · 全等时长
        
        int m8 = 2, n8 = 4;
        int[] times8 = {1, 1, 1, 99};
        System.out.println(solve(m8, n8, times8)); // 100 · 尾部超长作业
        
        int m9 = 2, n9 = 4;
        int[] times9 = {99, 1, 2, 3};
        System.out.println(solve(m9, n9, times9)); // 101 · 漏排序会得99
        
        int m10 = 2, n10 = 8;
        int[] times10 = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(solve(m10, n10, times10)); // 20  · 多轮调度
        
        int m11 = 3, n11 = 5;
        int[] times11 = {10, 2, 8, 3, 4};
        System.out.println(solve(m11, n11, times11)); // 13  · 顺序无关
        
        int m12 = 99, n12 = 2;
        int[] times12 = {50, 60};
        System.out.println(solve(m12, n12, times12)); // 60  · m远大于n
        
        int m13 = 2, n13 = 10;
        int[] times13 = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
        System.out.println(solve(m13, n13, times13)); // 5
        
        int m14 = 50, n14 = 99;
        int[] times14 = new int[99];
        Arrays.fill(times14, 99);
        System.out.println(solve(m14, n14, times14)); // 198 · 接近上限
        
        int m15 = 3, n15 = 7;
        int[] times15 = {1, 1, 1, 1, 1, 1, 100};
        System.out.println(solve(m15, n15, times15)); // 102
        
        int m16 = 4, n16 = 6;
        int[] times16 = {5, 3, 8, 1, 9, 2};
        System.out.println(solve(m16, n16, times16)); // 11
        
        int m17 = 3, n17 = 7;
        int[] times17 = {4, 4, 4, 1, 1, 1, 7};
        System.out.println(solve(m17, n17, times17)); // 12  · 重复时长
        
        int m18 = 4, n18 = 9;
        int[] times18 = {1, 1, 1, 1, 1, 1, 1, 1, 100};
        System.out.println(solve(m18, n18, times18)); // 102
    }
}
