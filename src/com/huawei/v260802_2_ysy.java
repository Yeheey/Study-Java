package com.huawei;

public class v260802_2_ysy {
    /*
    【26.8.2-2】语义化版本号对比--【模拟计算】120min--API调用不熟悉
    题目描述:
    当前有一组版本号列表信息，单个版本号格式为:Major.Minor.Patch-可选先行版+可选构建信息，具体字段如下:
    · Major.Minor.Patch:为三个非负整数，禁止前导零;
    · 先行版和构建信息:可选段信息，由字母数字和符号(.和 -)组成;
    请实现语义化版本号比较器，对输入版本号字符串列表按下面规则处理，返回最大的版本号(若存在相等最大版本号，则返回第一个版本号)，若存在不合规格式则返回空字符串。

    优先级比较:
    从左至右比较Major Minor Patch数字大小，大的优先;
    若Major Minor Patch相同，则继续对比先行版信息(第一个-号为分隔符，有信息大于无信息);
    如果先行版信息相同，则对比构建信息(第一个+号为分隔符，有信息大于无信息);依次类推;单个元素对比规则如下:
    先行版和构建信息如果包含符号.，先按.切分然后逐一对比，切分后首个元素大的优先，如果首元素相同则比较第二个，
    ·纯数值:按数值大的优先
    ·字符/数字/符号/混合串:ASCII字典序大的优先(1<A<a)
    ·前缀相同:内容多者优先(a-b<a-b-c)

    输入描述:
    输入版本号列表长度在[1,100]之间，每个版本号字符串长度在[0,1024]之间，其中纯数字标识符的值在[0,2~(31}-1]之间，非纯数字标识符长度在[0,1024]之间。

    先行版和构建信息输入保证:
    任意符号之间内容不为空，信息中可能出现-或.，不出现_ 和.(在特定上下文中)和~  不以符号开头或结尾，纯数字标识符无前导零;
    +最多出现一次;
    输入为一行，多个版本号之间使用英文逗号分隔。

    输出描述:
    返回最大的版本号字符串，若存在不合规格式则返回空字符串。
    */
    
    static String major;
    static String minor;
    static String patch;
    static String preRelease;
    static String buildInfo;
    
    /**
     * 拆解版本号
     * BUG修复1：原patchMix分割逻辑错乱，-是先行版分隔、+是构建分隔；先切+，再切第一个-
     * BUG修复2：原来split直接取数组元素，没有判断分段数量，会数组越界；先按+拆分主体和build
     */
    static String[] decompose(String s) {
        // 重置全局变量
        major = "";
        minor = "";
        patch = "";
        preRelease = "";
        buildInfo = "";
        
        String mainPart;
        // +最多一次，先拆分构建信息
        if (s.contains("+")) {
            String[] plusSplit = s.split("\\+", 2);
            mainPart = plusSplit[0];
            buildInfo = plusSplit[1];
        } else {
            mainPart = s;
            buildInfo = "";
        }
        
        // mainPart 格式：x.y.z 或者 x.y.z-pre
        if (mainPart.contains("-")) {
            String[] dashSplit = mainPart.split("-", 2);
            String core = dashSplit[0];
            preRelease = dashSplit[1];
            String[] coreSeg = core.split("\\.");
            if (coreSeg.length >= 3) {
                major = coreSeg[0];
                minor = coreSeg[1];
                patch = coreSeg[2];
            }
        } else {
            String[] coreSeg = mainPart.split("\\.");
            if (coreSeg.length >= 3) {
                major = coreSeg[0];
                minor = coreSeg[1];
                patch = coreSeg[2];
            }
        }
        
        String[] decomposeVersion = new String[5];
        decomposeVersion[0] = major;
        decomposeVersion[1] = minor;
        decomposeVersion[2] = patch;
        decomposeVersion[3] = preRelease;
        decomposeVersion[4] = buildInfo;
        return decomposeVersion;
    }
    
    /**
     * 判断是纯数字 且没有前导0 范围在[0,2^31-1]之间
     * BUG修复3：原来前导零判断逻辑写反！s.length()>1 && 首字符0 代表非法前导零
     * BUG修复4：原来判断返回值颠倒，合法返回true，非法false
     */
    static Boolean isValidNum(String s) {
        // 空串非法
        if (s == null || s.isEmpty()) {
            return false;
        }
        // 校验全部是数字
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) < '0' || s.charAt(i) > '9') {
                return false;
            }
        }
        // 前导零：长度>1并且以0开头，非法；"0"合法
        boolean hasLeadingZero = s.length() > 1 && s.charAt(0) == '0';
        if (hasLeadingZero) {
            return false;
        }
        final long MAX = Integer.MAX_VALUE; // 2^31-1
        long val;
        try {
            val = Long.parseLong(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return val >= 0 && val <= MAX;
    }
    
    /**
     * 校验版本号是否合规
     * BUG修复5：拆解后major/minor/patch为空直接非法（主体不足3段）
     */
    static Boolean isValidVersion(String s) {
        String[] decomposeVersion = decompose(s);
        String mj = decomposeVersion[0];
        String mi = decomposeVersion[1];
        String pt = decomposeVersion[2];
        if (mj.isEmpty() || mi.isEmpty() || pt.isEmpty()) {
            return false;
        }
        int preReleaseLen = decomposeVersion[3].length();
        int buildInfoLen = decomposeVersion[4].length();
        return isValidNum(mj) && isValidNum(mi) && isValidNum(pt) && preReleaseLen <= 1024 && buildInfoLen <= 1024;
    }
    
    /**
     * 比较数字大小
     * 返回值：空串代表相等；非空=更大的数字串
     */
    static StringBuilder compareNumber(String s1, String s2) {
        long n1 = Long.parseLong(s1);
        long n2 = Long.parseLong(s2);
        if (n1 > n2) {
            return new StringBuilder(s1);
        } else if (n1 < n2) {
            return new StringBuilder(s2);
        } else {
            return new StringBuilder();
        }
    }
    
    /**
     * 比较先行版/构建信息内部段细节（按.分割逐项对比）
     * 返回：空=相等；否则返回更大的那一段原始串
     */
    static StringBuilder compareDetails(String s1, String s2) {
        String[] arr1 = s1.split("\\.");
        String[] arr2 = s2.split("\\.");
        int minLen = Math.min(arr1.length, arr2.length);
        
        for (int i = 0; i < minLen; i++) {
            String seg1 = arr1[i];
            String seg2 = arr2[i];
            boolean isNum1 = isNumeric(seg1);
            boolean isNum2 = isNumeric(seg2);
            if (isNum1 && isNum2) {
                long n1 = Long.parseLong(seg1);
                long n2 = Long.parseLong(seg2);
                if (n1 > n2) return new StringBuilder(s1);
                if (n1 < n2) return new StringBuilder(s2);
            } else {
                // 混合字符串，ASCII字典序
                int cmp = seg1.compareTo(seg2);
                if (cmp > 0) return new StringBuilder(s1);
                if (cmp < 0) return new StringBuilder(s2);
            }
        }
        // 前缀相同，更长的更大
        if (arr1.length > arr2.length) return new StringBuilder(s1);
        if (arr1.length < arr2.length) return new StringBuilder(s2);
        return new StringBuilder();
    }
    
    // 判断是否纯数字
    static boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * 比较先行版信息和构建信息：【题目规则：有信息 > 无信息】
     */
    static StringBuilder comparePreReleaseAndBuild(String s1, String s2) {
        if (s1.isEmpty() && !s2.isEmpty()) {
            return new StringBuilder(s2);
        }
        if (!s1.isEmpty() && s2.isEmpty()) {
            return new StringBuilder(s1);
        }
        // 都不为空，逐段对比
        if (!s1.isEmpty() && !s2.isEmpty()) {
            return compareDetails(s1, s2);
        }
        // 都为空相等
        return new StringBuilder();
    }
    
    /**
     * 比较两个完整版本 v1 和 v2
     * 返回值：
     *  >0 代表v1更大；=0相等；<0代表v2更大
     * BUG修复8：原来compareVersion返回的是bigger分段字符串，无法判断哪个版本胜出；改成int比较信号，不再返回版本串
     */
    static int compareTwoVersion(String v1, String v2) {
        String[] d1 = decompose(v1);
        String[] d2 = decompose(v2);
        
        String mj1 = d1[0], mi1 = d1[1], pt1 = d1[2], pre1 = d1[3], build1 = d1[4];
        String mj2 = d2[0], mi2 = d2[1], pt2 = d2[2], pre2 = d2[3], build2 = d2[4];
        
        // Major
        StringBuilder cmpMj = compareNumber(mj1, mj2);
        if (!cmpMj.isEmpty()) {
            return cmpMj.toString().equals(mj1) ? 1 : -1;
        }
        // Minor
        StringBuilder cmpMi = compareNumber(mi1, mi2);
        if (!cmpMi.isEmpty()) {
            return cmpMi.toString().equals(mi1) ? 1 : -1;
        }
        // Patch
        StringBuilder cmpPt = compareNumber(pt1, pt2);
        if (!cmpPt.isEmpty()) {
            return cmpPt.toString().equals(pt1) ? 1 : -1;
        }
        // 先行版
        StringBuilder cmpPre = comparePreReleaseAndBuild(pre1, pre2);
        if (!cmpPre.isEmpty()) {
            return cmpPre.toString().equals(pre1) ? 1 : -1;
        }
        // 构建信息
        StringBuilder cmpBuild = comparePreReleaseAndBuild(build1, build2);
        if (!cmpBuild.isEmpty()) {
            return cmpBuild.toString().equals(build1) ? 1 : -1;
        }
        // 全部相等
        return 0;
    }
    
    /**
     * solve
     * BUG修复9：原来循环比较时，错误把分段bigger字符串当成完整版本号更新最大值；现在用compareTwoVersion判断，保留第一个最大
     */
    static String solve(String[] input) {
        // 校验所有版本，任意非法返回空串
        for (final String version : input) {
            boolean isValid = isValidVersion(version);
            if (!isValid) {
                return "";
            }
        }
        
        if (input.length == 1) {
            return input[0];
        }
        
        // 维护当前最大版本，相等保留先出现的
        String maxVer = input[0];
        for (int i = 1; i < input.length; i++) {
            String cur = input[i];
            int ret = compareTwoVersion(maxVer, cur);
            // ret <0：当前cur更大，更新maxVer
            if (ret < 0) {
                maxVer = cur;
            }
        }
        return maxVer;
    }
    
    static void main() {
        String[] input = {"1.0.0-alpha", "1.0.0-alpha.beta", "1.0.0-alpha.beta-A", "1.0.0-alpha.beta-A+C"};
        System.out.println("测试1输出：" + solve(input));
        // 预期输出：1.0.0-alpha.beta-A+C
        
        String[] input2 = {"1.4.0-alpha", "1.3.9", "1.4.0", "1.4.0-alpha+build9", "1.4.0-beta", "1.4.0-beta.2", "1.4.0-beta.10", "1.4.0-beta.10+zzz", "1.4.0-beta.10+aaa", "1.4.0-alpha.10", "2.0.0-1", "2.0.0"};
        System.out.println("测试2输出：" + solve(input2));
        // 题目规则预期输出：2.0.0-1
    }
}
