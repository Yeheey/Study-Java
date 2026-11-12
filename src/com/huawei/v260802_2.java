package com.huawei;

public class v260802_2 {
    /*
     * 32.【26.8.2-2】语义化版本号对比
     * 题目描述:
     * 当前有一组版本号列表信息，单个版本号格式为:Major.Minor.Patch-可选先行版+可选构建信息，具体字段如下:
     * · Major.Minor.Patch:为三个非负整数，禁止前导零;
     * · 先行版和构建信息:可选段信息，由字母数字和符号(.和 -)组成;
     * 请实现语义化版本号比较器，对输入版本号字符串列表按下面规则处理，返回最大的版本号(若存在相等最大版本号，则返回第一个版本号)，若存在不合规格式则返回空字符串。
     *
     * 优先级比较:从左至右比较Major Minor Patch数字大小，大的优先;
     * 若Major Minor Patch相同，则继续对比先行版信息(第一个-号为分隔符，有信息大于无信息);
     * 如果先行版信息相同，则对比构建信息(第一个+号为分隔符，有信息大于无信息);依次类推;单个元素对比规则如下:
     * 先行版和构建信息如果包含符号.，先按.切分然后逐一对比，切分后首个元素大的优先，如果首元素相同则比较第二个，
     * ·纯数值:按数值大的优先
     * ·字符/数字/符号/混合串:ASCII字典序大的优先(1<A<a)
     * ·前缀相同:内容多者优先(a-b<a-b-c)
     *
     * 输入描述:
     * 输入版本号列表长度在[1,100]之间，每个版本号字符串长度在[0,1024]之间，其中纯数字标识符的值在[0,2~(31}-1]之间，非纯数字标识符长度在[0,1024]之间。
     * 先行版和构建信息输入保证:任意符号之间内容不为空，信息中可能出现-或.，不出现、.(在特定上下文中)、~，
     * 不以符号开头或结尾，纯数字标识符无前导零;+最多出现一次;
     * 输入为一行，多个版本号之间使用英文逗号分隔。
     *
     * 输出描述:
     * 返回最大的版本号字符串，若存在不合规格式则返回空字符串。
     */
    
    static String solve(String[] input) {
        // ==========新增bug注释：原逻辑错误：遍历只拿i-1和i比较，丢失全局最大值；应该保存当前最大值，依次和后面比较==========
        // ==========新增bug注释：原题要求：只要任意一个版本非法，直接返回""，原代码完全没有校验逻辑==========
        if (input.length == 0) {
            return "";
        }
        // 校验全部版本，任意一个非法直接返回空串
        for (String ver : input) {
            if (!isValidVersion(ver)) {
                return "";
            }
        }
        String maxVer = input[0];
        for (int i = 1; i < input.length; i++) {
            maxVer = compare(maxVer, input[i]);
        }
        return maxVer;
    }
    
    // 比较版本号大小 返回较大的版本号字符串
    static String compare(String a, String b) {
        String[] res1 = decompose(a);
        String[] res2 = decompose(b);
        
        // ==========新增bug注释：原for循环无意义，删掉，顺序判断即可==========
        // 1.比较Major
        long ma = Long.parseLong(res1[0]);
        long mb = Long.parseLong(res2[0]);
        if (ma > mb) {
            return a;
        } else if (ma < mb) {
            return b;
        } else {
            // 2.比较Minor
            long miA = Long.parseLong(res1[1]);
            long miB = Long.parseLong(res2[1]);
            if (miA > miB) {
                return a;
            } else if (miA < miB) {
                return b;
            } else {
                // 3.比较Patch
                long pa = Long.parseLong(res1[2]);
                long pb = Long.parseLong(res2[2]);
                if (pa > pb) {
                    return a;
                } else if (pa < pb) {
                    return b;
                } else {
                    // 4.比较可选先行版
                    String resInfo1 = compareInfo(res1[3], res2[3]);
                    if (!resInfo1.isEmpty()) {
                        return resInfo1.equals(res1[3]) ? a : b;
                    } else {
                        // 5.比较构建信息
                        String resInfo2 = compareInfo(res1[4], res2[4]);
                        if (!resInfo2.isEmpty()) {
                            return resInfo2.equals(res1[4]) ? a : b;
                        } else {
                            // 完全相等，返回前面的a，保证取第一个
                            return a;
                        }
                    }
                }
            }
        }
    }
    
    // 比较先行版本号+构建信息
    // ==========新增注释：题目规则：先行版、构建信息统一规则：有信息 > 无信息==========
    static String compareInfo(String a, String b) {
        if (a.isEmpty() && b.isEmpty()) {
            return ""; // 相等返回空标记
        } else if (a.isEmpty() || b.isEmpty()) {
            // 有信息>无信息
            return a.isEmpty() ? b : a;
        } else {
            // 都有信息
            int resV = compareVersion(a, b);
            if (resV > 0) {
                return a;
            } else if (resV < 0) {
                return b;
            } else {
                return ""; // 相等
            }
        }
    }
    
    /**
     * 比较两个版本字符串
     *
     * @param v1 版本1
     * @param v2 版本2
     * @return >0:v1更大；=0相等；<0:v2更大
     */
    public static int compareVersion(String v1, String v2) {
        // 按 . 切分
        String[] arr1 = v1.split("\\.");
        String[] arr2 = v2.split("\\.");
        
        int minLen = Math.min(arr1.length, arr2.length);
        for (int i = 0; i < minLen; i++) {
            String s1 = arr1[i];
            String s2 = arr2[i];
            
            int cmp;
            if (isAllDigit(s1) && isAllDigit(s2)) {
                // 两段都是纯数字：按数值比较
                long n1 = Long.parseLong(s1);
                long n2 = Long.parseLong(s2);
                cmp = Long.compare(n1, n2);
            } else {
                // 混合串：ASCII字典序（原生compareTo正好是Unicode，英文符号数字等价ASCII）
                cmp = s1.compareTo(s2);
            }
            if (cmp != 0) {
                return cmp;
            }
            // 当前段相等，继续下一段
        }
        // 前面全部相同：内容多（数组更长）的优先
        return Integer.compare(arr1.length, arr2.length);
    }
    
    /**
     * 判断字符串是否全部由数字组成（纯数值段）
     */
    public static boolean isAllDigit(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }
        for (char ch : s.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
    
    // ==========新增：合法性校验函数，原题要求存在不合规直接返回空字符串==========
    static boolean isValidVersion(String ver) {
        if (ver.isEmpty()) return false;
        int idx1 = ver.indexOf('-');
        int idx2 = ver.indexOf('+');
        String mainStr;
        if (idx1 != -1) {
            mainStr = ver.substring(0, idx1);
        } else if (idx2 != -1) {
            mainStr = ver.substring(0, idx2);
        } else {
            mainStr = ver;
        }
        String[] parts = mainStr.split("\\.");
        if (parts.length != 3) return false;
        for (String seg : parts) {
            if (!isAllDigit(seg)) return false;
            // 禁止前导零，0合法
            if (seg.length() > 1 && seg.charAt(0) == '0') {
                return false;
            }
        }
        // +最多出现一次
        int plusCnt = 0;
        for (char c : ver.toCharArray()) {
            if (c == '+') plusCnt++;
        }
        return plusCnt <= 1;
    }
    
    // 分解每一个部分
    static String[] decompose(String a) {
        int idx1 = a.indexOf('-');
        int idx2 = a.indexOf('+');
        
        String first = ""; // Major.Minor.Patch
        String second = ""; // 可选先行版
        String third = ""; // 可选构建信息
        
        // ==========新增bug注释：原代码：没有'-'的时候first不会赋值，first为空，split会报错==========
        if (idx1 == -1 && idx2 == -1) {
            first = a;
        } else if (idx1 == -1) {
            // 只有+，没有-
            first = a.substring(0, idx2);
            third = a.substring(idx2 + 1);
        } else if (idx2 == -1) {
            // 只有-，没有+
            first = a.substring(0, idx1);
            second = a.substring(idx1 + 1);
        } else {
            // 既有-又有+
            first = a.substring(0, idx1);
            second = a.substring(idx1 + 1, idx2);
            third = a.substring(idx2 + 1);
        }
        
        String major = first.split("\\.")[0];
        String minor = first.split("\\.")[1];
        String patch = first.split("\\.")[2];
        
        String[] result = new String[5];
        result[0] = major;
        result[1] = minor;
        result[2] = patch;
        result[3] = second;
        result[4] = third;
        return result;
    }
    
    static void main(String[] args) {
        String[] input = {"1.0.0-alpha", "1.0.0-alpha.beta", "1.0.0-alpha.beta-A", "1.0.0-alpha.beta-A+C"};
        System.out.println(solve(input));
        // 预期输出：1.0.0-alpha.beta-A+C
        
        String [] input2 = {"1.4.0-alpha","1.3.9","1.4.0","1.4.0-alpha+build9","1.4.0-beta","1.4.0-beta.2","1.4.0-beta.10","1.4.0-beta.10+zzz","1.4.0-beta.10+aaa","1.4.0-alpha.10","2.0.0-1","2.0.0"};
        System.out.println(solve(input2));
        // 预期输出：2.0.0-1
    }
}
