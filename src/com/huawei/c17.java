package com.huawei;

import java.util.ArrayList;
import java.util.List;

public class c17 {
    /*
        【17.国际移动用户识别码(IMSI)匹配】--【字符串+排序】--写20min+调试20min
        题目描述
        小明是核心网工程师，客户交给小明一个任务:给定一个网络配置列表，每个配置是一个字符串，仅有数字和"*"、"?"符号组成。
        输入用户的IMSI(国际移动用户识别码)，根据以下规则匹配配置列表：
            "*"匹配0个或连续多个任意字符。
            "?"匹配下标为奇数的单个字符，比如123?中的”?"可以匹配123456789012345下标为3的字符’4’，下标从0开始。
        
        输入描述
        输入第一行为网络配置列表，列表中的每个配置是由数字和" * “、” ? “组成的字符串，每个字符串中” * “不会超过一个
        ”?"若干，网络配置列表长度小于200，每个字符串以英文逗号隔开。
        输入第二行为用户的IMSI(国际移动用户识别码)，仅有数字组成，长度等于15。
        备注:保证输入格式正确，无需考虑格式错误.
        
        输出描述
        输出为满足匹配规则的配置字符串列表，列表按字典序升序输出，每个字符以英文逗号隔开。若没有满足条件的配置，则返回字符串"null"
        */
    
    /**
     * 辅助函数-判断该字符串是否是纯数字
     */
    public static boolean isNumber(String s) {
        for (final char c : s.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 辅助函数-判断该字符串是否合法
     */
    public static boolean isValid(String cur, String target) {
        int idx = cur.indexOf("*"); // -1 不存在*
        // 无*无？不全匹配 不合法
        if (idx < 0 && !cur.contains("?") && cur.length() != target.length()) {
            return false;
        }
        // 有* 补齐cur的长度
        if (idx >= 0 && cur.length() != target.length()) {
            while (cur.length() < target.length()) {
                StringBuilder sb = new StringBuilder(cur);
                sb.insert(idx, '*');
                cur = sb.toString();
            }
        }
        
        // System.out.println("cur" + cur);
        // System.out.println("tar" + target);
        
        for (int i = 0; i < target.length(); i++) {
            char c = cur.charAt(i);
            char t = target.charAt(i);
            // 合法 continue
            if (isNumber(String.valueOf(c)) && c == t) {
                // 是数字&&匹配 跳过 下一个循环
                continue;
            }
            // 是*通配符 跳过 下一个循环
            if (c == '*') {
                continue;
            }
            // 不合法 false
            if (isNumber(String.valueOf(c)) && c != t) {
                // 1.是数字 但不匹配
                return false;
            }
            if (c == '?' && i % 2 == 0) {
                // 2. ?且下标是偶数 不合法
                return false;
            }
        }
        
        return true;
    }
    
    public static String solve(String network, String imsi) {
        String[] net = network.split(",");
        // 升序存合法模版
        List<String> tmp = new ArrayList<>();
        for (String cur : net) {
            // 校验本字段是否合法
            boolean isTrue = isValid(cur, imsi);
            // System.out.println(cur + "----是否合法:" + isTrue);
            if (isTrue) {
                tmp.add(cur);
            }
        }
        
        // ✅️字符串升序排列
        tmp.sort(String::compareTo);
        // System.out.println(tmp);
        
        // 整理答案
        StringBuilder sb = new StringBuilder();
        tmp.forEach(item -> {
            sb.append(item).append(",");
        });
        
        if (!sb.isEmpty()) {
            sb.deleteCharAt(sb.length() - 1);
        } else {
            sb.append("null");
        }
        
        return sb.toString();
    }
    
    static void main() {
        String network, imsi;
        
        network = "1234567,1234567*";
        imsi = "123456789012345";
        // 期望 1234567*  · 官方示例1
        System.out.println(solve(network, imsi));
        
        network = "123?????????345,123????*????345";
        imsi = "123456789012345";
        // 期望 null  · 官方示例2，? 覆盖偶数下标
        System.out.println(solve(network, imsi));
        
        network = "1?3456789012345,12?456789012345,123?56789012345,1234?6789012345";
        imsi = "123456789012345";
        // 期望 123?56789012345,1?3456789012345  · ?放下标1/3过、2/4挂
        System.out.println(solve(network, imsi));
        
        network = "1?3?5?7?9?1?3?5,1?3?5?7?9?1?3?6,?23456789012345,1234?6789012345";
        imsi = "123456789012345";
        // 期望 1?3?5?7?9?1?3?5  · ?全在奇数下标
        System.out.println(solve(network, imsi));
        
        network = "123456789012345,1234567890123456,12345678901234,123456789012346";
        imsi = "123456789012345";
        // 期望 123456789012345  · 长度/末位边界
        System.out.println(solve(network, imsi));
        
        network = "123456789012345*,12345678901234*5,*";
        imsi = "123456789012345";
        // 期望 *,12345678901234*5,123456789012345*  · *匹配0字符
        System.out.println(solve(network, imsi));
        
        network = "*345,*3456,*34?,*";
        imsi = "123456789012345";
        // 期望 *,*345  · *在开头，后缀须对齐末尾
        System.out.println(solve(network, imsi));
        
        network = "123*?345";
        imsi = "123456789012345";
        // 期望 123*?345  · *与?组合，?落下标11(奇)
        System.out.println(solve(network, imsi));
        
        network = "123*??45";
        imsi = "123456789012345";
        // 期望 null  · 连续两个?必有一个在偶数下标
        System.out.println(solve(network, imsi));
        
        network = "123*345,123*?45,123*3?5";
        imsi = "123456789012345";
        // 期望 123*345,123*3?5  · ?在下标12(偶)失败/13(奇)通过
        System.out.println(solve(network, imsi));
        
        network = "999999999999999,123,?23456789012345,1234?6789012345";
        imsi = "123456789012345";
        // 期望 null  · 全部不匹配
        System.out.println(solve(network, imsi));
        
        network = "1234567*,*,1?3?5?7?9?1?3?5,123456789012345,123456789012345*,?23456789012345";
        imsi = "123456789012345";
        // 期望 *,1234567*,123456789012345,123456789012345*,1?3?5?7?9?1?3?5  · 乱序考字典序
        System.out.println(solve(network, imsi));
        
        network = "4600*,4600???????????,4?0?0?2?4?6?8?0,*";
        imsi = "460001234567890";
        // 期望 *,4600*,4?0?0?2?4?6?8?0  · 换一个 IMSI
        System.out.println(solve(network, imsi));
    }
}
