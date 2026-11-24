package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c12 {
    /*
    【敏感字段加密】--【字符串】--30min
    题目描述
    给定一个由多个命令字组成的命令字符串：
    1、字符串长度小于等于127字节，只包含大小写字母，数字，下划线和偶数个双引号；
    2、命令字之间以一个或多个下划线_进行分割；
    3、可以通过两个双引号””来标识包含下划线_的命令字或空命令字（仅包含两个双引号的命令字），双引号不会在命令字内部出现；
    请对指定索引的敏感字段进行加密，替换为******（6个*），并删除命令字前后多余的下划线_。
    ✅ 命令字之间固定 一个 `_`  里面连续的 `__`、`___` 都是多余分隔符，分词的时候只当成**1 个分隔**
    如果无法找到指定索引的命令字，输出字符串ERROR。
    
    输入描述
    输入为两行，第一行为命令字索引K（从0开始），第二行为命令字符串S。
    
    输出描述
    输出处理后的命令字符串，如果无法找到指定索引的命令字，输出字符串ERROR
    */
    public static String solve(int k, String s) {
        // 存分割字符
        List<String> list = new ArrayList<>();
        char[] cArr = s.toCharArray();
        
        // 默认下划线是分割线 如果遇到引号 置为false
        boolean isSplit = true;
        // 记住前一个是不是下划线 区分连续下划线的情况
        boolean isLine = false;
        // 计数引号次数
        int count = 0;
        // 暂存的命令字
        StringBuilder sb = new StringBuilder();
        for (char c : cArr) {
            if (c == '"') {
                count++;
                // 当引号是奇数 说明进入_不是分隔符的地方
                isSplit = count % 2 == 0;
                isLine = false;
                sb.append(c);
                continue;
            }
            if (c == '_' && isSplit) {
                // 前一个不是下划线 ✅️注意非空才加
                if (!isLine && !sb.isEmpty()) {
                    list.add(sb.toString());
                }
                
                sb = new StringBuilder();
                isLine = true;
                
                continue;
            }
            isLine = false;
            sb.append(c);
        }
        
        // ✅️注意非空才加
        if (!isLine && !sb.isEmpty()) {
            list.add(sb.toString());
        }
        
        int len = list.size();
        if (k >= len) {
            return "ERROR";
        }
        String star = "******";
        list.set(k, star);
        
        StringBuilder res = new StringBuilder();
        for (String str : list) {
            if (!str.isEmpty()) {
                res.append(str);
            }
            res.append("_");
        }
        
        while (res.toString().endsWith("_")) {
            res.delete(res.length() - 1, res.length());
        }
        
        return res.toString();
    }
    
    static void main() {
        int k0 = 0;
        String s0 = "__password_a12345678_timeout_100";
        // ******_a12345678_timeout_100
        System.out.println(solve(k0, s0));
        
        int k1 = 1;
        String s1 = "password__a12345678_timeout_100";
        // ["password", "a12345678", "timeout", "100"]
        // password_******_timeout_100
        System.out.println(solve(k1, s1));
        
        int k2 = 2;
        String s2 = "aaa_password_\"a12_45678\"_timeout__100_\"\"_";
        // ["aaa", "password", "a12_45678", "timeout", "100", ""]
        // aaa_password_******_timeout_100_""
        System.out.println(solve(k2, s2));
        
        int k3 = 1;
        String s3 = "aaa_password_\"a12_45678\"_timeout__100_\"\"_";
        // ["aaa", "password", "a12_45678", "timeout", "100", ""]
        // aaa_******_"a12_45678"_timeout_100_""
        System.out.println(solve(k3, s3));
    }
}
