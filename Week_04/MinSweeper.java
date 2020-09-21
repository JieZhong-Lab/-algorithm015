import java.util.LinkedList;
import java.util.Queue;

public class MinSweeper {
    //DFS
    int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};
    int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};
    public char[][] updateBoard(char[][] board, int[] click) {
        int x = click[0], y = click[1];
        if (board[x][y] == 'M') 
            board[x][y] = 'X';
        else
            dfs(board, x, y);
        return board;
    }

    private void dfs(char[][] board, int i, int j) {
        int cnt = 0;
        int m = board.length;
        int n = board[0].length;
         //Termniator: 空白方块周围有相邻的地雷，则更新为地雷数量，终止该路径搜索，返回
        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x < 0 || x >= m || y < 0 || y >= n) continue;
            if (board[x][y] == 'M') 
                cnt++;
        }
        if (cnt > 0) {
            board[i][j] = (char)(cnt + '0');
            return;
        }
        //Process current logic
        //空地周围没有雷，则将该位置修改为'B', 继续向周围（8个邻居结点）搜索
        board[i][j] = 'B';
        for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x < 0 || x >= m || y < 0 || y >= n || board[x][y] != 'E') continue;
            //drill down
            dfs(board, x, y);
        }
    }

    //BFS
    public char[][] updateBoard_2(char[][] board, int[] click) {
        int x = click[0];
        int y = click[1];
        if (board[x][y] == 'M') {
            board[x][y] = 'X';
            return board;
        }
        //若起点是未挖出的空方块
        int m = board.length;
        int n = board[0].length;
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{x, y});
        boolean[][] visited = new boolean[m][n];
        visited[x][y] = true;
        while(!queue.isEmpty()) {
            int[] node = queue.poll();
            int i = node[0], j = node[1];
            //判断结点i，j周围是否有雷
            int cnt = 0;
            for (int k = 0; k < 8; k++) {
                int x1 = i + dx[k];
                int y1 = j + dy[k];
                if (x1 < 0 || x1 >= m || y1 < 0 || y1 >= n) continue;
                if (board[x1][y1] == 'M') cnt++;
            }
            if (cnt > 0) {//有雷，标记为数字
                board[i][j] = (char)(cnt + '0');
            } else {//继续探索周围
                board[i][j] = 'B';
                for (int k = 0; k < 8; k++) {
                    int xx = i + dx[k];
                    int yy = j + dy[k];
                    if (xx < 0 || xx >= m || yy < 0 || yy >= n || board[xx][yy] != 'E' || visited[xx][yy])
                        continue;
                    //没有超出边界，并且是没有访问过的空方格
                    visited[xx][yy] = true;
                    queue.offer(new int[] {xx, yy});
                }
            }
        }
        return board;
    }
}
