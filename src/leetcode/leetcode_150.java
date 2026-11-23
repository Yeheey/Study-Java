package leetcode;

import java.util.Stack;

public class leetcode_150 {
    // 逆波兰表达式求值
    
    // 辅助函数 判断是运算符
    static boolean isCal(String s) {
        return s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/");
    }
    
    // 辅助函数 运算符计算
    static int calculate(String s, int a, int b) {
        return switch (s) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            default -> a / b;
        };
    }
    
    static int solve(String[] tokens) {
        int res = 0;
        
        Stack<String> stack = new Stack<>();
        
        int s = 0;
        
        while (s < tokens.length) {
            String cur = tokens[s];
            if (!isCal(cur)) {
                // 如果是数字
                stack.add(cur);
            } else {
                // 如果是运算符
                int t1 = Integer.parseInt(stack.pop());
                int t2 = Integer.parseInt(stack.pop());
                res = calculate(cur, t2, t1);
                stack.add(Integer.toString(res));
            }
            
            s++;
        }
        
        return res;
    }
    
    static void main() {
        String[] tokens1 = {"2", "1", "+", "3", "*"};
        // 9
        System.out.println(solve(tokens1));
        
        String[] tokens2 = {"4","13","5","/","+"};
        // 6
        System.out.println(solve(tokens2));
    }
}
