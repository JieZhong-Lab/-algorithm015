public class NumOfIslands {
    //200. 岛屿数量
    //并查集
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        MyUnionFind uf = new MyUnionFind(grid);
        for (int r = 0; r < m; r++) {
            for (int c = 0; c < n; c++) {
                if (grid[r][c] == '1') {
                    grid[r][c] = '0'; //表示已经访问过
                    if (r - 1 >= 0 && grid[r-1][c] == '1') {
                        uf.union(r * n + c, (r - 1) * n + c);
                    }
                    if (r + 1 < m && grid[r+1][c] == '1') {
                        uf.union(r * n + c, (r + 1) * n + c);
                    }
                    if (c - 1 >= 0 && grid[r][c-1] == '1') {
                        uf.union(r * n + c, r * n + c - 1);
                    }
                    if (c + 1 < n && grid[r][c+1] == '1') {
                        uf.union(r * n + c, r * n + c + 1);
                    }
                }
            }
        }
        return uf.getCount();
    }

    public static void main(String args[]) {
        NumOfIslands islands = new NumOfIslands();
        char[][] grid = new char[][] {
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        System.out.println(islands.numIslands(grid));
    }
}

class MyUnionFind {
    private int count = 0;
    private int[] parent;
    private int[] rank;

    public MyUnionFind(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        parent = new int[m * n];
        rank = new int[m * n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    parent[i * n + j] = i * n + j;
                    ++count;
                }
                rank[i * n + j] = 0;
            }
        }
    }

    public int find(int p) {
        int root = p;
        while (root != parent[root]) {
            root = parent[root];
        }
        while (p != parent[p]) {
            int x = p;
            p = parent[p];
            parent[x] = root;
        }
        return root;
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP != rootQ) {
            if (rank[rootP] > rank[rootQ]) {
                parent[rootQ] = rootP;
            } else if (rank[rootP] < rank[rootQ]) {
                parent[rootP] = rootQ; 
            } else {
                parent[rootQ] = rootP;
                rank[rootP]++;
            }
            count--;
        }
    }

    public int getCount() {
        return count;
    }
}
