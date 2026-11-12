package com.huawei;

public class v260802_2_answer {
    static final long LIMIT = 2147483647L;
    
    static boolean isDigits(String s) {
        if (s.isEmpty()) return false;
        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }
    
    static boolean validNumber(String s) {
        if (!isDigits(s)) return false;
        if (s.length() > 1 && s.charAt(0) == '0') return false;
        long value = Long.parseLong(s);
        return value <= LIMIT;
    }
    
    static boolean validExtra(String s) {
        if (s == null) return true;
        if (s.isEmpty() || s.charAt(0) == '.' || s.charAt(s.length() - 1) == '.') return false;
        String[] parts = s.split("\\.", -1);
        for (String part : parts) {
            if (part.isEmpty()) return false;
            for (int i = 0; i < part.length(); i++) {
                char c = part.charAt(i);
                if (!Character.isLetterOrDigit(c) && c != '-') return false;
            }
            if (isDigits(part) && !validNumber(part)) return false;
        }
        return true;
    }
    
    static Version parse(String ver) {
        if (ver.isEmpty()) return new Version(false);
        if (ver.indexOf('+') != ver.lastIndexOf('+')) return new Version(false);
        String left = ver;
        String build = null;
        int plus = ver.indexOf('+');
        if (plus >= 0) {
            left = ver.substring(0, plus);
            build = ver.substring(plus + 1);
            if (!validExtra(build)) return new Version(false);
        }
        String mainText;
        String pre = null;
        int hyphen = left.indexOf('-');
        if (hyphen >= 0) {
            mainText = left.substring(0, hyphen);
            pre = left.substring(hyphen + 1);
        } else {
            String[] dotParts = left.split("\\.", -1);
            if (dotParts.length == 3) {
                mainText = left;
            } else if (dotParts.length >= 5) {
                mainText = dotParts[0] + "." + dotParts[1] + "." + dotParts[2];
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < dotParts.length; i++) {
                    if (i > 3) sb.append('.');
                    sb.append(dotParts[i]);
                }
                pre = sb.toString();
            } else {
                return new Version(false);
            }
        }
        String[] nums = mainText.split("\\.", -1);
        if (nums.length != 3) return new Version(false);
        Version res = new Version(true);
        res.main = new int[3];
        for (int i = 0; i < 3; i++) {
            if (!validNumber(nums[i])) return new Version(false);
            res.main[i] = Integer.parseInt(nums[i]);
        }
        if (!validExtra(pre)) return new Version(false);
        res.pre = pre;
        res.build = build;
        return res;
    }
    
    static int compareSegment(String a, String b) {
        boolean da = isDigits(a), db = isDigits(b);
        if (da && db) {
            long x = Long.parseLong(a), y = Long.parseLong(b);
            return Long.compare(x, y);
        }
        return a.compareTo(b);
    }
    
    static int comparePart(String a, String b) {
        if (a == null && b == null) return 0;
        if (a != null && b == null) return 1;
        if (a == null) return -1;
        String[] pa = a.split("\\.", -1);
        String[] pb = b.split("\\.", -1);
        int len = Math.max(pa.length, pb.length);
        for (int i = 0; i < len; i++) {
            if (i >= pa.length) return -1;
            if (i >= pb.length) return 1;
            int cmp = compareSegment(pa[i], pb[i]);
            if (cmp != 0) return cmp;
        }
        return 0;
    }
    
    static int compareVersion(Version a, Version b) {
        for (int i = 0; i < 3; i++) {
            if (a.main[i] != b.main[i]) return Integer.compare(a.main[i], b.main[i]);
        }
        int preCmp = comparePart(a.pre, b.pre);
        if (preCmp != 0) return preCmp;
        return comparePart(a.build, b.build);
    }
    
    static String solve(String[] versions) {
        // 先统一解析所有版本，只要出现非法格式就返回空字符串
        Version[] parsed = new Version[versions.length];
        for (int i = 0; i < versions.length; i++) {
            parsed[i] = parse(versions[i]);
            if (!parsed[i].valid) return "";
        }
        // 只在后续版本严格更大时更新，保证相等最大值返回第一个
        int best = 0;
        for (int i = 1; i < versions.length; i++) {
            if (compareVersion(parsed[i], parsed[best]) > 0) best = i;
        }
        return versions[best];
    }
    
    static void main(String[] args) {
        String[] input = {"1.0.0-alpha", "1.0.0-alpha.beta", "1.0.0-alpha.beta-A", "1.0.0-alpha.beta-A+C"};
        System.out.println(solve(input));
        // 预期输出：1.0.0-alpha.beta-A+C
        
        String[] input2 = {"1.4.0-alpha", "1.3.9", "1.4.0", "1.4.0-alpha+build9", "1.4.0-beta", "1.4.0-beta.2", "1.4.0-beta.10", "1.4.0-beta.10+zzz", "1.4.0-beta.10+aaa", "1.4.0-alpha.10", "2.0.0-1", "2.0.0"};
        System.out.println(solve(input2));
        // 预期输出：2.0.0-1
    }
    
    static class Version {
        boolean valid;
        int[] main;
        String pre;
        String build;
        
        Version(boolean valid) {
            this.valid = valid;
        }
    }
}
