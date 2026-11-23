package com.huawei;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class v261028_1_answer {
    /*
        【日志规则逆波兰转换】-AI
        题目描述
        系统中的日志采集模块支持用户配置日志过滤规则。为了避免每次匹配日志时重新分析规则，需要将中缀逻辑表达式园转换为后缀表达式，也称逆波兰表达式(RPN)。
        给定一条合法的日志过滤表达式，请输出其对应的后缀表达式。后缀表达式将运算符放在对应的操作数之后，不再需要括号。
        表达式的操作数仅包含以下五种日志关键字，必须完全匹配，且区分大小写:ERROR NETWORK TIMEOUT DISK CPU
      
        支持的运算符如下：
        NOT：前缀一元运算符，逻辑非，优先级3，右结合
        AND：二元运算符，逻辑与，优先级2，左结合
        OR：二元运算符，逻辑或，优先级1，左结合
        
        优先级数值越大，运算优先级越高。同级的AND或OR按从左到右的顺序结合;NOT为右结合，例如NOT NOT ERROR 表示NOT(NOT ERROR)。
        表达式可以使用英文圆括号改变运算顺序。括号保证成对出现，嵌套正确。空格可以连续出现，括号可以紧贴关键字;相邻的单词之间至少有一个空格。
        
        输入描述
        输入一行字符串，表示一条合法的日志过滤表达式。输入不包含变量赋值前缀和字符串两侧的引号。
        数据范围:表达式总长度为1到1000，括号嵌套深度不超过10。
        所有操作数和运算符均使用上述大写单词。表达式至少包含一个操作数，不需要处理非法表达式。
        
        输出描述
        输出一行后缀表达式，按顺序输出其中的操作数与运算符，相邻元素之间使用一个英文空格分隔。
        输出不包含括号、引号、逗号或数组方括号，行首和行尾不添加空格。
        */
    
    // 辅助函数-判断是否是操作数
    static boolean isOper(String s) {
        return s.equals("ERROR") || s.equals("NETWORK") || s.equals("TIMEOUT") || s.equals("DISK") || s.equals("CPU");
    }
    
    // 辅助函数-判断是否是运算符
    static boolean isCac(String s) {
        return s.equals("NOT") || s.equals("AND") || s.equals("OR");
    }
    
    static int priority(String s) {
        return switch (s) {
            case "NOT" -> 3;
            case "AND" -> 2;
            case "OR" -> 1;
            default -> 0;
        };
    }
    
    /**
     * 左结合时，栈顶优先级大于等于当前运算符就先弹出。
     * NOT 右结合，只有栈顶优先级严格更高时才弹出。
     */
    static boolean popFirst(String top, String cur) {
        if (cur.equals("NOT")) {
            return priority(top) > priority(cur);
        }
        return priority(top) >= priority(cur);
    }
    
    static void append(StringBuilder res, String s) {
        if (!res.isEmpty()) {
            res.append(' ');
        }
        res.append(s);
    }
    
    /**
     * 括号可以紧贴关键字，连续空格要丢掉。
     */
    static List<String> tokenize(String inputs) {
        List<String> tokens = new ArrayList<>();
        StringBuilder word = new StringBuilder();
        for (int i = 0; i < inputs.length(); i++) {
            char c = inputs.charAt(i);
            if (c == ' ') {
                if (word.isEmpty()) {
                    continue;
                }
                tokens.add(word.toString());
                word.setLength(0);
                continue;
            }
            if (c != '(' && c != ')') {
                word.append(c);
                continue;
            }
            if (!word.isEmpty()) {
                tokens.add(word.toString());
                word.setLength(0);
            }
            tokens.add(String.valueOf(c));
        }
        if (!word.isEmpty()) {
            tokens.add(word.toString());
        }
        return tokens;
    }
    
    static String solve(String inputs) {
        StringBuilder res = new StringBuilder();
        Stack<String> s2 = new Stack<>();
        
        for (final String s : tokenize(inputs)) {
            if (isOper(s)) {
                append(res, s);
                continue;
            }
            if (s.equals("(")) {
                s2.push(s);
                continue;
            }
            if (s.equals(")")) {
                while (!s2.peek().equals("(")) {
                    append(res, s2.pop());
                }
                s2.pop();
                continue;
            }
            if (!isCac(s)) {
                continue;
            }
            while (!s2.isEmpty() && isCac(s2.peek()) && popFirst(s2.peek(), s)) {
                append(res, s2.pop());
            }
            s2.push(s);
        }
        
        while (!s2.isEmpty()) {
            append(res, s2.pop());
        }
        return res.toString();
    }
    
    static void main() {
        String inputs = "ERROR AND NETWORK";
        // ERROR NETWORK AND
        System.out.println(solve(inputs));
    }
}