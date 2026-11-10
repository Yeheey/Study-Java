package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class v260913_1 {
    /*
    【26.9.13-1】手机桌面整理
    题目描述
    手机系统管理一个App列表，按最近使用App操作排序;
    用户可以对App进行如下操作：
    
    1.OPEN Name: 打开App；
    若App已存在，将其移至列表最前面，打开次数 +1，返回当前打开次数；
    若App不存在，返回-1
    2.INSTALL Name：安装App；
    若APP已存在，将其移至列表最前面，打开次数不变；
    若APP不存在，将其添加至列表最前面，打开次数设置为0；
    若列表已满，先卸载最久未使用APP再安装，INSTALL操作无返回值。
    3.UNINSTALL Name：卸载App；
    若APP已存在，移除该APP并返回其打开次数；
    若APP不存在，返回-1。否则返回该APP的打开次数。
    
    先给定一组用户操作指令，请统计所有OPEN和UNINSTALL操作的返回值。
    
    输入描述
    本题为核心代码模式，你需要实现函数 manageApps(capacity, operations)。
    参数 capacity(0≤capacity≤103) 为一个整数，表示 App 列表的长度上限。
    参数 operations 为一个字符串数组，长度len(1≤len≤103)，每个元素是一条操作指令，格式为操作名与 App 名称之间用一个空格隔开，操作名为OPEN、INSTALL、UNINSTALL之一。
    App 名称由英文字母或数字组成（区分大小写），名称长度 L(1≤L≤20)。
    为了便于在线判题，输入的第一行为容量 capacity，后续每一行是一条操作指令，读取到文件结束为止。
    
    输出描述
    按顺序统计OPEN和UNINSTALL操作的返回值
    */
    
    
    static List<Integer> manageApps(int capacity, String[] operations) {
        
        List<Integer> ans = new ArrayList<>();
        // 用于存key:APP名称和 value:操作次数
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        // 用于存APP的排放顺序
        List<String> list = new ArrayList<>();
        
        for (int i = 0; i < operations.length; i++) {
            String op = operations[i].split(" ")[0];
            String name = operations[i].split(" ")[1];
            
            if (op.equals("OPEN")) {
                // 1.打开
                if (!isExist(list, name)) {
                    ans.add(-1);
                } else {
                    list.remove(name);
                    list.addFirst(name);
                    int count = map.get(name) + 1;
                    map.put(name, count);
                    ans.add(count);
                }
            } else if (op.equals("INSTALL")) {
                // 📌容量为0 不可安装
                if (capacity <= 0) {
                    continue;
                }
                // 2.安装
                if (isExist(list, name)) {
                    // 安装已存在的 头插
                    list.remove(name);
                    list.addFirst(name);
                } else {
                    // 如果不存在 看看容量 还能放就头插
                    if (list.size() < capacity) {
                        list.addFirst(name);
                        map.put(name, 0);
                    } else {
                        // 容量不够 卸载最不常用 在头插
                        map.remove(list.getLast());
                        map.put(name, 0);
                        list.removeLast();
                        list.addFirst(name);
                    }
                }
            } else {
                // 3.卸载
                if (!isExist(list, name)) {
                    ans.add(-1);
                } else {
                    ans.add(map.get(name));
                    list.remove(name);
                    map.remove(name);
                    
                }
            }
        }
        
        System.out.println(list);
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
        return ans;
    }
    
    static Boolean isExist(List<String> list, String name) {
        return list.contains(name);
    }
    
    static void main() {
        int capacity = 3;
        String[] operations = {"INSTALL WeChat", "INSTALL Map", "OPEN Map", "OPEN Map", "INSTALL WeChat", "INSTALL Music", "INSTALL Mail", "OPEN Map", "UNINSTALL Music", "UNINSTALL Map"};
        // [1,2,-1,0,-1]
        System.out.print(manageApps(capacity, operations));
    }
}
