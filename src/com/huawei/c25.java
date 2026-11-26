package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class c25 {
    /*
    【25.打印机队列】--【模拟计算+map+排序】--25min
    题目描述
    有5台打印机打印文件，每台打印机有自己的待打印队列。
    因为打印的文件内容有轻重缓急之分，所以队列中的文件有1~10不同的代先级，其中数字越大优先级越高。
    打印机会从自己的待打印队列中选择_优先级最高_的文件来打印。
    如果存在两个优先级一样的文件，则选择_最早进入队列_的那个文件。
    现在请你来模拟这5台打印机的打印过程。
    
    输入描述
    每个输入包含1个测试用例，
    每个测试用例第一行给出发生事件的数量N（0 < N < 1000）。
    接下来有 N 行，分别表示发生的事件。共有如下两种事件：
    “IN P NUM”，表示有一个拥有优先级 NUM 的文件放到了打印机 P 的待打印队列中。（0< P <= 5, 0 < NUM <= 10)；
    “OUT P”，表示打印机 P 进行了一次文件打印，同时该文件从待打印队列中取出。（0 < P <= 5）。
    
    输出描述
    对于每个测试用例，每次”OUT P”事件，请在一行中输出文件的编号。
    如果此时没有文件可以打印，请输出”NULL“。
    文件的编号定义为”IN P NUM”事件发生第 x 次，此处待打印文件的编号为x。编号从1开始。
    */
    public static String solve(int n, String[] inputs) {
        // key 打印机编号 value [[文件优先级num,文件编号],[...]]
        // 每一次put都要按照优先级 降序 排
        HashMap<Integer, List<int[]>> mp = new HashMap<>();
        int event = 0; // IN的事件编号 每次IN都加1
        
        StringBuilder sb = new StringBuilder();
        
        for (String s : inputs) {
            String oper = s.split(" ")[0];
            int print = Integer.parseInt(s.split(" ")[1]); // 打印机编号
            int num = -1; // 当OUT 不存在优先级
            if (oper.equals("IN")) {
                num = Integer.parseInt(s.split(" ")[2]);
                event++;
                int[] e = new int[2];
                e[0] = num;
                e[1] = event;
                List<int[]> value = new ArrayList<>();
                if (mp.containsKey(print)) {
                    value = mp.get(print);
                }
                value.add(e);
                value.sort((a, b) -> {
                    return b[0] != a[0] ? b[0] - a[0] : a[1] - b[1];
                });
                mp.put(print, value);
            } else {
                // 不存在打印机 或者存在打印机但是里面value值是空
                if (!mp.containsKey(print) || (mp.containsKey(print) && mp.get(print).isEmpty())) {
                    sb.append("NULL");
                } else {
                    // 存在打印机且有值 删除对应value的int[] 拿出编号
                    int[] tmp = mp.get(print).removeFirst();
                    sb.append(tmp[1]);
                }
                sb.append(" ");
            }
            
            // System.out.println(oper);
            // System.out.println(print);
            // System.out.println(num);
        }
        
        // 打印
        // mp.forEach((k, v) -> {
        //     System.out.println("打印机编号" + k);
        //     v.forEach(item -> {
        //         System.out.println(Arrays.toString(item));
        //     });
        // });
        
        String res= "NULL";
        if (!sb.isEmpty()) {
            res = sb.toString().trim();
        }
        
        return res;
    }
    
    static void main() {
        int n1 = 7;
        String[] s1 = {"IN 1 1", "IN 1 2", "IN 1 3", "IN 2 1", "OUT 1", "OUT 2", "OUT 2"};
        // 3 4 NULL
        System.out.println(solve(n1, s1));
        
        int n2 = 5;
        String[] s2 = {"IN 1 1", "IN 1 3", "IN 1 1", "IN 1 3", "OUT 1"};
        // 2
        System.out.println(solve(n2, s2));
    }
}