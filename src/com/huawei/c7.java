package com.huawei;

public class c7 {
    /*
        【7.构成正方形的数量】--【数学】--30min
        题目描述
        输入N个互不相同的二维整数坐标，求这N个坐标可以构成的正方形数量。[内积为零的的两个向量垂直]
        
        输入描述
        第一行输入为N，N代表坐标数量，N为正整数。N <= 100
        
        之后的 K 行输入为坐标x y以空格分隔，x，y为整数，-10<=x, y<=10
        
        输出描述
        输出可以构成的正方形数量。
    */
    
    /**
     * 给定正方形对角线两端点 A(x1,y1) 和 C(x2,y2)，
     * 返回另外两个顶点 B 和 D。
     * 返回格式：double[2][2]，[0] 为 B，[1] 为 D
     */
    // 示例：对角线 (0,0) 到 (2,2)，正方形应为 (0,0)(1,-1)(2,2)(1,3)...
    // 实际验证：A(0,0), C(2,2) → B(2,0), D(0,2)
    public static double[][] getOtherTwoVertices(double x1, double y1, double x2, double y2) {
        // 中点
        double mx = (x1 + x2) / 2.0;
        double my = (y1 + y2) / 2.0;
        
        // 从中点指向 A 的向量
        double vx = x1 - mx;
        double vy = y1 - my;
        
        // 旋转 ±90° 得到 B、D 相对中点的偏移
        // 旋转 +90°: (vx, vy) -> (-vy, vx)
        double bx = mx - vy;
        double by = my + vx;
        
        // 旋转 -90°: (vx, vy) -> (vy, -vx)
        double dx = mx + vy;
        double dy = my - vx;
        
        return new double[][]{{bx, by}, {dx, dy}};
    }
    
    public static int solve(int n, int[][] inputs) {
        if (n < 4) {
            return 0;
        }
        
        int res = 0;
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                
                double x1 = inputs[i][0];
                double y1 = inputs[i][1];
                double x2 = inputs[j][0];
                double y2 = inputs[j][1];
                double[][] r = getOtherTwoVertices(x1, y1, x2, y2);
                
                // 找到剩下两个顶点 看看在不在inputs中
                double[] r1 = r[0];
                double[] r2 = r[1];
                
                boolean b = isInputs(inputs, r1, r2);
                
                if (b) {
                    res += 1;
                }
            }
        }
        
        // 对角线算了2次
        return res / 2;
    }
    
    public static boolean isGirdEquals(double[] a, double[] b) {
        return a[0] == b[0] && a[1] == b[1];
    }
    
    public static boolean isInputs(int[][] inputs, double[] r1, double[] r2) {
        int n = inputs.length;
        double[][] inputsD = new double[n][2];
        boolean b1 = false;
        boolean b2 = false;
        
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                inputsD[i][j] = inputs[i][j];
                
            }
        }
        
        for (int i = 0; i < n; i++) {
            double[] cur = inputsD[i];
            if (isGirdEquals(cur, r1)) {
                b1 = true;
                break;
            }
        }
        
        for (int i = 0; i < n; i++) {
            double[] cur = inputsD[i];
            if (isGirdEquals(cur, r2)) {
                b2 = true;
                break;
            }
        }
        
        return b1 && b2;
    }
    
    static void main() {
        int[][] i1 = {{1, 3}, {2, 4}, {3, 1}};
        // 0
        System.out.println(solve(3, i1));
        
        int[][] i2 = {{0, 0}, {1, 2}, {3, 1}, {2, -1}};
        // 1
        System.out.println(solve(4, i2));
        
        int[][] i3 = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {1, 2}, {2, 1}, {0, 2}, {2, 0}, {-2, 0}, {0, -2}};
        // 3
        System.out.println(solve(10, i3));
    }
}
