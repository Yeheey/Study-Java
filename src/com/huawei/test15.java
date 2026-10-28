package com.huawei;

public class test15 {
    /*
        15.【26.8.23-3】抗洪救灾
        题目描述：
        某地区发生特大洪水，水位高达X，该地区被划分成了mxn个区块，各区块海拔高度不同。
        被洪水淹没区域的群众已经自发转移到附近的高地，消防官兵需要快速获取未被淹没的高地数量，并派出救援队将群众转移。
        请你快速帮助消防官兵统计出未被淹没的高地数量，方便消防官兵组织救援。
        未淹没的连续区块被识别为同一区域，连续方式包含水平、垂直、斜线方向(8连通)。
        
        输入描述：
        参数1:整数X，表示洪水高度(0≤X≤10)。
        参数2:整数m，表示区块的行数(3≤m,n≤100)。
        参数3:整数n，表示区块的列数(3≤m,n≤100)。
        参数4:二维数组(mxn)，表示每个区块的海拔高度。
        输入为一行，四个参数用英文逗号分隔，例如:5,3,3,[[0,0,0],[0,9,0],[0,0,0]]
        
        输出描述：
        一个整数，表示未被淹没的高地区域数量。
        */
    private static int num;
    private static int height = 0;
    private static int row = 0;
    private static int col = 0;
    private static boolean[][] visited;
    private static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    private static int[][] matrix;
    
    static int solve(int x, int m, int n, int[][] mat) {
        num = 0;
        height = x;
        row = m;
        col = n;
        matrix = mat;
        visited = new boolean[row][col];
        
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (matrix[r][c] > height && !visited[r][c]) {
                    num++;
                    dfs(r, c, visited, matrix);
                }
                
            }
        }
        
        return num;
    }
    
    static void dfs(int r, int c, boolean[][] visited, int[][] matrix) {
        // 1.边界考虑
        if (r >= row || r < 0 || c >= col || c < 0 || visited[r][c] || matrix[r][c] <= height) {
            return;
        }
        
        // 2.访问过的置为true
        visited[r][c] = true;
        
        // 3.8连通遍历
        for (int[] direction : directions) {
            int newR = r + direction[0];
            int newC = c + direction[1];
            dfs(newR, newC, visited, matrix);
        }
        
    }
    
    static void main() {
        int x = 5;
        int m = 3;
        int n = 3;
        
        int[][] matrix = {{0, 0, 0}, {0, 9, 0}, {0, 0, 0}};
        System.out.print(solve(x, m, n, matrix));
    }
}
