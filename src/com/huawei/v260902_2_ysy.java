package com.huawei;

import java.util.*;

public class v260902_2_ysy {
    /*
    【26.9.2-2】螳螂领地争霸--【模拟计算--数组排序】--27min
    题目描述：
    在一根很高的竹子上生活着一群螳螂，每只螳螂都有一段自己的势力范围，不同螳螂在竹子上的领地有可能重叠
    当发现自己的领地内出现其它螳螂时就会战斗，失败一方领地会被胜利一方螳螂占领。
    
    输入描述：
    给定一个螳螂领地信息originalLand[i]
    数组:originalLand[i]=[x，y]表示第i只螳螂的领地在离地高度[x,y]之间的
    主干上(x<y)。螳螂的数量在[1,50]之间，螳螂领地坐标[x,y]中，0≤x<y≤150。
    
    输出描述：
    请返回领地斗争结束后螳螂们的领地信息。返回的领地信息数组中，按照领地坐标升序返回;正确示例:[[3,7],[9,13]]
    错误示例:[[9,13],[3,7]]。
    */
    
    // 有重叠 取并集 最后升序排序
    static List<List<Integer>> compareArray(int[][] originalLand) {
        List<List<Integer>> res = new ArrayList<>();
        
        int n = originalLand.length;
        
        // 左端点升序排序
        Arrays.sort(originalLand, (a, b) -> a[0] - b[0]);
        // ⭐更安全，不会整数溢出
        Arrays.sort(originalLand, (a, b) -> Integer.compare(a[0], b[0]));
        // ⭐或者更优雅
        Arrays.sort(originalLand, Comparator.comparingInt(a -> a[0]));
      
        List<Integer> current = new ArrayList<>();
        current.add(originalLand[0][0]);
        current.add(originalLand[0][1]);
        res.add(current);
        
        if (n == 1) {
            return res;
        }
        
        int lastLeft = res.getLast().get(0); // 前一个左端点
        int lastRight = res.getLast().get(1); // 前一个的右端点
        int curLeft; // 当前左端点
        int curRight; // 当前右端点
        
        for (int i = 1; i < n; i++) {
            List<Integer> temp = new ArrayList<>();
            
            curLeft = originalLand[i][0];
            curRight = originalLand[i][1];
            
            if (curLeft > lastRight) {
                // 无冲突
                temp.add(curLeft);
                temp.add(curRight);
                res.add(temp);
                
                // 更新lastLeft lastRight 为当前端点
                lastLeft = curLeft;
                lastRight = curRight;
            } else {
                // 有冲突 合并数组
                int maxRight = Math.max(lastRight, curRight);
                int minLeft = Math.min(lastLeft, curLeft);
                temp.add(minLeft);
                temp.add(maxRight);
                if (!res.isEmpty()) {
                    res.removeLast();
                }
                
                res.add(temp);
                
                // 更新lastLeft lastRight 为当前端点
                lastLeft = minLeft;
                lastRight = maxRight;
            }
            
        }
        
        
        Collections.sort(res, (a, b) -> a.get(0) - b.get(0));
        
        return res;
    }
    
    static void main() {
        int[][] originalLand = {{12, 13}, {1, 6}, {2, 4}, {5, 7}, {9, 11}};
        // [[1, 7], [9, 11], [12, 13]]
        System.out.println(compareArray(originalLand));
    }
}
