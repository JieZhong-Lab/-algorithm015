public class NumberOfIslands {
    //200. 岛屿数量
    //DFS
    int m, n;
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) return 0;
        n = grid.length;
        m = grid[0].length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    dfs_marking(grid, i, j);
                    ans++;
                }
            }
        }
        return ans;
    }

    private void dfs_marking(char[][] grid, int r, int c) {
        if (r < 0 || c < 0 || r >= n || c >= m || grid[r][c] != '1') return;

        grid[r][c] = '0';
        dfs_marking(grid, r + 1, c);
        dfs_marking(grid, r - 1, c);
        dfs_marking(grid, r, c + 1);
        dfs_marking(grid, r, c - 1);
    }

    public static void main(String args[]) {
        NumberOfIslands islands = new NumberOfIslands();
        char[][] grid = new char[][] {
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        System.out.println(islands.numIslands(grid));
    }
}
