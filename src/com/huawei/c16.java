package com.huawei;

import java.util.*;

public class c16 {
    /*
        【16.热点网站统计】--【字符串+排序】--40min
        题目描述
        企业路由器的统计页面，有一个功能需要动态统计公司访问最多的网页URL top N。请设计一个算法，可以高效动态统计Top N的页面。
        
        输入描述
        每一行都是一个URL或一个数字，如果是URL，代表一段时间内的网页访问
        如果是一个数字N，代表本次需要输出的Top N个URL。
        
        输入约束:
            1、总访问网页数量小于5000个，单网页访问次数小于65535次； 2^16-1
            2、网页URL仅由字母，数字和点分隔符组成，且长度小于等于127字节；
            3、数字是正整数，小于等于10且小于当前总访问网页数；
        
        输出描述
        行输入要对应一行输出，输出按访问次数排序的前N个URL，用逗号分隔。
        
        输出要求：
        1、每次输出要统计之前所有输入，不仅是本次输入；
        2、如果有访问次数相等的URL，按URL的字符串字典序升序排列，输出排序靠前的URL；
        */
    
    /**
     * 辅助函数-判断该字符串是否是纯数字
     */
    public static boolean isNumber(String s) {
        char[] sc = s.toCharArray();
        for (final char c : sc) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
    
    public static String solve(String[] inputs) {
        // key-URL value-访问次数
        HashMap<String, Integer> map = new HashMap<>();
        // 用于存放当前所有网址 以及访问次数 ["URL","次数"]
        List<String[]> tmp = new ArrayList<>(2);
        // 打印输出
        StringBuilder sb = new StringBuilder();
        
        for (final String urlOrNum : inputs) {
            if (!isNumber(urlOrNum)) {
                map.merge(urlOrNum, 1, Integer::sum);
            } else {
                tmp.clear();
                // 如果是数字 就要打印当前N个访问最多的
                int n = Integer.parseInt(urlOrNum);
                // ✅️快捷键iter
                for (Map.Entry<String, Integer> entry : map.entrySet()) {
                    String key = entry.getKey();
                    String val = String.valueOf(entry.getValue());
                    tmp.add(new String[]{key, val});
                }
                tmp.sort((a, b) -> {
                    int aVal = Integer.parseInt(a[1]);
                    int bVal = Integer.parseInt(b[1]);
                    String aURL = a[0];
                    String bURL = b[0];
                    return aVal == bVal ? aURL.compareTo(bURL) : Integer.compare(bVal, aVal);
                });
                
                // 打印
                // System.out.println("===打印前" + n + "个");
                // for (String[] strings : tmp) {
                //     System.out.println("===tmp" + Arrays.toString(strings));
                // }
                
                for (int i = 0; i < n; i++) {
                    sb.append(tmp.get(i)[0]);
                    if (i != n - 1) {
                        sb.append(",");
                    }
                }
                sb.append(";");
                // System.out.println("===打印结果" + sb);
            }
        }
        // System.out.println(map);
        
        if(!sb.isEmpty()){
            // 删除最后一个分号
            sb.deleteCharAt(sb.length()-1);
        }
        return sb.toString();
    }
    
    static void main() {
        String[] arr = {"news.qq.com", "news.sina.com.cn", "news.qq.com", "news.qq.com", "game.163.com", "game.163.com", "www.huawei.com", "www.cctv.com", "3", "www.huawei.com", "www.cctv.com", "www.huawei.com", "www.cctv.com", "www.huawei.com", "www.cctv.com", "www.huawei.com", "www.cctv.com", "www.huawei.com", "3"};
        // news.qq.com,game.163.com,news.sina.com.cn
        // www.huawei.com,www.cctv.com,news.qq.com
        System.out.println("ans--" + solve(arr));
        
        String[] arr1 = {"news.qq.com", "www.cctv.com", "1", "www.huawei.com", "www.huawei.com", "2", "3"};
        // news.qq.com
        // www.huawei.com,news.qq.com
        // www.huawei.com,news.qq.com,www.cctv.com
        System.out.println("ans--" + solve(arr1));
    }
}
