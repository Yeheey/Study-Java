package leetcode;

public class leetcode_200_dfs {
    /*
     【200.岛屿数量】
     给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
     岛屿总是被水包围，并且每座岛屿只能由水平方向和/或竖直方向上相邻的陆地连接形成。
     此外，你可以假设该网格的四条边均被水包围。
     */
    static int count = 0;
    static int col;
    static int row;
    static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    
    static void dfs(int i, int j, char[][] grid) {
        if (i < 0 || i >= col || j < 0 || j >= row || grid[i][j] == '0') {
            return;
        }
        
        grid[i][j] = '0';
        
        for (int[] dir : directions) {
            int newI = i + dir[0];
            int newJ = j + dir[1];
            if (newI >= 0 && newI < col && newJ >= 0 && newJ < row && grid[newI][newJ] == '1') {
                dfs(newI, newJ, grid);
            }
        }
    }
    
    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        
        count = 0;
        col = grid.length;
        row = grid[0].length;
        
        for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                // 只有是1的情况 才深度搜索
                if (grid[i][j] == '1') {
                    dfs(i, j, grid);
                    count++;
                }
            }
        }
        return count;
    }
    
    static void main() {
        char[][] grid = {{'1', '1', '1', '1', '0'}, {'1', '1', '0', '1', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '0', '0', '0'}};
        System.out.println(numIslands(grid)); // 1
        
        char[][] grid1 = {{'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}};
        System.out.println(numIslands(grid1)); // 3
    }
}
