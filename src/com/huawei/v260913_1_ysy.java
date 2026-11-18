package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class v260913_1_ysy {
    /*
    【26.9.13-1】手机桌面整理--【模拟计算+Map】--写24min+调试11min = 35min
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
        // key-app name
        // value-打开次数
        HashMap<String, Integer> map = new HashMap<>();
        // app name 排序
        List<String> order = new ArrayList<>();
        
        for (final String item : operations) {
            String op = item.split(" ")[0];
            String name = item.split(" ")[1];
            
            if (op.equals("OPEN")) {
                // 1.打开
                if (!map.containsKey(name)) {
                    ans.add(-1);
                } else {
                    int openNums = map.get(name) + 1;
                    map.put(name, openNums);
                    ans.add(openNums);
                    order.remove(name);
                    order.addFirst(name);
                }
            } else if (op.equals("INSTALL")) {
                // 2.安装
                if (!map.containsKey(name) && order.size() < capacity) {
                    // 2.1 APP无 列表未满
                    map.put(name, 0);
                    order.addFirst(name);
                } else if (!map.containsKey(name) && order.size() >= capacity) {
                    // 2.2 APP无 列表满了 先卸再装
                    String last = order.getLast();
                    map.remove(last);
                    order.removeLast();
                    // 安装
                    map.put(name, 0);
                    order.addFirst(name);
                } else {
                    // 2.3 APP有 移最前
                    order.remove(name);
                    order.addFirst(name);
                }
            } else {
                // 3.卸载
                if (!map.containsKey(name)) {
                    // 3.1 APP无
                    ans.add(-1);
                } else {
                    // 3.2 APP有
                    int openNums = map.get(name);
                    ans.add(openNums);
                    map.remove(name);
                    order.remove(name);
                }
            }
        }
        
        return ans;
    }
    
    static void main() {
        int capacity = 3;
        String[] operations = {"INSTALL WeChat", "INSTALL Map", "OPEN Map", "OPEN Map", "INSTALL WeChat", "INSTALL Music", "INSTALL Mail", "OPEN Map", "UNINSTALL Music", "UNINSTALL Map"};
        // [1,2,-1,0,-1]
        System.out.print(manageApps(capacity, operations));
    }
}
