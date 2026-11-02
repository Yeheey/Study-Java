package com.huawei;

public class test32 {
    /*
    32.【26.8.2-2】语义化版本号对比
    题目描述:
    当前有一组版本号列表信息，单个版本号格式为:Major.Minor.Patch-可选先行版+可选构建信息，具体字段如下:
    · Major.Minor.Patch:为三个非负整数，禁止前导零;
    · 先行版和构建信息:可选段信息，由字母数字和符号(.和 -)组成;
    请实现语义化版本号比较器，对输入版本号字符串列表按下面规则处理，返回最大的版本号(若存在相等最大版本号，则返回第一个版本号)，若存在不合规格式则返回空字符串。
    
    优先级比较:从左至右比较Major Minor Patch数字大小，大的优先;
    若Major Minor Patch相同，则继续对比先行版信息(第一个-号为分隔符四，有信息大于无信息);
    如果先行版信息相同，则对比构建信息(第一个+号为分隔符，有信息大于无信息);依次类推;单个元素对比规则如下:
    先行版和构建信息如果包含符号.，先按.切分然后逐一对比，切分后首个元素大的优先，如果首元素相同则比较第二个，
    ·纯数值:按数值大的优先
    ·字符/数字/符号/混合串:ASCII字典序大的优先(1<A<a)
    ·前缀相同:内容多者优先(a-b<a-b-c)
    
    输入描述:
    输入版本号列表长度在[1,100]之间，每个版本号字符串长度在[0,1024]之间，其中纯数字标识符的值在[0,2~(31}-1]之间，非纯数字标识符长度在[0,1024]之间。
    先行版和构建信息输入保证:任意符号之间内容不为空，信息中可能出现-或.，不出现、.(在特定上下文中)、~，
    不以符号开头或结尾，纯数字标识符无前导零;+最多出现一次;
    输入为一行，多个版本号之间使用英文逗号分隔。
    
    输出描述:
    返回最大的版本号字符串，若存在不合规格式则返回空字符串。
    */
    
    static String solve(String[] input) {
        decompose(input[0]);
        String res = "";
        
        return res;
    }
    
    // 分解每一个部分
    static String[] decompose(String a) {
        int idx1 = a.indexOf('-');
        int idx2 = a.indexOf('+');
        
        String first = ""; // Major.Minor.Patch
        String second = ""; // 可选先行版
        String third = ""; // 可选构建信息
        
        String major;
        String minor;
        String patch;
        
        if (idx1 != -1) {
            first = a.substring(0, idx1);
            if (idx2 == -1) {
                second = a.substring(idx1 + 1);
            } else {
                second = a.substring(idx1 + 1, idx2);
            }
        }
        if (idx2 != -1) {
            third = a.substring(idx2 + 1);
        }
        
        major = first.split("\\.")[0];
        minor = first.split("\\.")[1];
        patch = first.split("\\.")[2];
        
        String[] result = new String[5];
        result[0] = major;
        result[1] = minor;
        result[2] = patch;
        result[3] = second;
        result[4] = third;
        
        return result;
    }
    
    static void main() {
        String[] input = {"1.0.0-alpha", "1.0.0.alpha.alpha", "1.0.0-alpha.beta", "1.0.0-alpha.beta-A+C"}; // output:438
        System.out.print(solve(input));
    }
}
