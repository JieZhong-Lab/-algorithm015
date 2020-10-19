public class SurroundedRegions {
    //130. 被围绕的区域
    //DFS
    int m, n;
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;

        m = board.length;
        n = board[0].length;
        //第一列，最后一列
        for (int i = 0; i < m; i++) {
            dfs(board, i, 0);
            dfs(board, i, n - 1);
        }
        //第一行，最后一行
        for (int i = 1; i < n - 1; i++) {
            dfs(board, 0, i);
            dfs(board, m - 1, i);
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'A') {
                    board[i][j] = 'O';
                } else if (board[i][j] == 'O') {
                    board[i][j] = 'X';
                }
            }
        }
    }
    //标记所有与board[r][c]直接或间接相连的字母O
    private void dfs(char[][] board, int r, int c) {
        if (r < 0 || r >= m || c < 0 || c >= n) return;
        if (board[r][c] != 'O') return;

        board[r][c] = 'A'; //marking
        dfs(board, r - 1, c);
        dfs(board, r + 1, c);
        dfs(board, r, c - 1);
        dfs(board, r, c + 1);
    }

    //并查集
    public void solve_2(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) return;
        int rows = board.length;
        int cols = board[0].length;
        MyUnionFindII uf = new MyUnionFindII(rows * cols + 1); // 多出来的1个为虚拟结点
        int dummy = rows * cols; //虚拟结点

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    if (i == 0 || i == rows - 1 || j == 0 || j == cols -1) {//四条边上的'O'和dummy相连
                        System.out.println(uf.find(i * cols + j) + "-" + uf.find(dummy));
                        uf.union(i * cols + j, dummy);
                        System.out.println(uf.find(i * cols + j) + "-" + uf.find(dummy));
                    }
                    if (i > 0 && board[i - 1][j] == 'O') {
                        uf.union(i * cols + j, (i - 1) * cols + j);
                    }
                    if (i + 1 < rows && board[i + 1][j] == 'O') {
                        uf.union(i * cols + j, (i + 1) * cols + j);
                    }
                    if (j > 0 && board[i][j - 1] == 'O') {
                        uf.union(i * cols + j, i * cols + j - 1);
                    }
                    if (j + 1 < cols && board[i][j + 1] == 'O') {
                        uf.union(i * cols + j, i * cols + j + 1);
                    }
                }
            }
        }

        //遍历board，和dummy结点不连接的'O'结点就是被包围的，标记为X
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 'O') {
                    if (uf.find(i * cols + j) != uf.find(dummy)) {
                        board[i][j] = 'X';
                    }
                }
            }
        }
    }

    public static void main(String args[]) {
        SurroundedRegions sr = new SurroundedRegions();
        char[][] board = new char[][] {
            {'X','X','X','X'},
            {'X','O','O','X'},
            {'X','X','O','X'},
            {'X','O','X','X'}
        };
        sr.solve_2(board);
        for (char[] chs : board) 
            System.out.println(chs);
    }
}

class MyUnionFindII {
    private int[] parent;
    public MyUnionFindII(int n) {
        parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
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
        int proot = find(p);
        int qroot = find(q);
        if (proot == qroot) return;
        parent[proot] = qroot;
    }
}
