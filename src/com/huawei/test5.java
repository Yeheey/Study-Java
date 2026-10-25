package com.huawei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class test5 {
    /*
    5.【26.9.2-2】螳螂领地争霸
    题目描述：
    在一根很高的竹子上生活着一群螳螂，每只螳螂都有一段自己的势力范围，不同螳螂在竹子上的领地有可能重叠，当发现自己
    的领地内出现其它螳螂时就会战斗，失败一方领地会被胜利一方螳螂占领。
    
    输入描述：
    给定一个螳螂领地信息originalland[i]数组四:originalLand[i]-[x，y]表示第|只螳螂的领地在离地高度[xy]之间的
    主干上(x<y)。螳螂的数量在[1,50]之间，螳螂领地坐标[x,y]中，0≤x<y≤150。
    
    输出描述：
    请返回领地斗争结束后螳螂们的领地信息。返回的领地信息数组中，按照领地坐标升序返回;正确示例:[[3,7],[9,13]]
    错误示例:[[9,13],[3,7]]。
    */
    static List<List<Integer>> compareArray(List<List<Integer>> matrix) {
        Collections.sort(matrix, (a, b) -> a.get(0) - b.get(0));
        
        int i = 0;
        while (i < matrix.size()) {
            int a = matrix.get(i).get(0);
            int b = matrix.get(i).get(1);
            if (i == matrix.size() - 1) {
                break;
            }
            int c = matrix.get(i + 1).get(0);
            int d = matrix.get(i + 1).get(1);
            
            if (c <= b) {
                matrix.set(i, List.of(a, Math.max(b, d)));
                matrix.remove(i + 1);
            } else {
                i++;
            }
        }
        return matrix;
    }
    
    static void main() {
        List<List<Integer>> matrix = new ArrayList<>();
        matrix.add(List.of(1, 6));
        matrix.add(List.of(2, 4));
        matrix.add(List.of(5, 7));
        matrix.add(List.of(9, 11));
        System.out.println(compareArray(matrix));
    }
}
