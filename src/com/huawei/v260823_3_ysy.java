package com.huawei;

public class v260823_3_ysy {
    /*
        【26.8.23-3】抗洪救灾-【二维DFS】--三刷--30min
        题目描述：
        某地区发生特大洪水，水位高达X，该地区被划分成了m * n个区块，各区块海拔高度不同。
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
    
    static int[][] directions = {{-1, 0}, {-1, -1}, {-1, 1}, {1, 0}, {1, -1}, {1, 1}, {0, -1}, {0, 1}};
    static boolean[][] visited;
    static int count;
    
    static void dfs(int r, int c, int[][] matrix, int m, int n, int x) {
        if (r < 0 || r >= m || c < 0 || c >= n || visited[r][c] || matrix[r][c] <= x) {
            return;
        }
        
        visited[r][c] = true;
        
        for (final int[] direction : directions) {
            int newI = r + direction[0];
            int newJ = c + direction[1];
            dfs(newI, newJ, matrix, m, n, x);
        }
    }
    
    /**
     * x-洪水高度
     * m-行
     * n-列
     * matrix-每个区块海拔高度
     */
    static int solve(int x, int m, int n, int[][] matrix) {
        if (matrix == null || m == 0 || n == 0) {
            return 0;
        }
        
        // 初始化
        visited = new boolean[m][n];
        count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 只有大于水位 且没有访问过 才进行深度搜索
                if (matrix[i][j] > x && !visited[i][j]) {
                    // 外层DFS
                    dfs(i, j, matrix, m, n, x);
                    count++;
                }
            }
        }
        // System.out.println(Arrays.deepToString(visited));
        return count;
    }
    
    
    static void main() {
        // 海拔 > X 才算没被淹没；上下左右和四个斜向挨着的算同一块
        // case1：题目示例，只有中心 9
        int x = 5;
        int m = 3;
        int n = 3;
        int[][] matrix = {{0, 0, 0}, {0, 9, 0}, {0, 0, 0}};
        // 1
        System.out.println(solve(x, m, n, matrix));
        
        // case2：全部 <= 水位
        // 0
        System.out.println(solve(5, 3, 3, new int[][]{{1, 2, 3}, {4, 5, 0}, {1, 1, 1}}));
        
        // case3：整图都高于水位，连成一块
        // 1
        System.out.println(solve(1, 3, 3, new int[][]{{2, 3, 4}, {5, 6, 7}, {8, 9, 2}}));
        
        // case4：两块高地只斜着挨着，8连通应算 1
        // 1
        System.out.println(solve(3, 3, 3, new int[][]{{5, 0, 0}, {0, 5, 0}, {0, 0, 0}}));
        
        // case5：斜向隔了一格，挨不着
        // 2
        System.out.println(solve(3, 3, 3, new int[][]{{9, 0, 0}, {0, 0, 0}, {0, 0, 9}}));
    }
}
