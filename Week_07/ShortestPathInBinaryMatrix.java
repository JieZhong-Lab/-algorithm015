import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

//1091. 二进制矩阵中的最短路径
public class ShortestPathInBinaryMatrix {
    //BFS
    // int dx[] = new int[] {-1, 1, 0, 0, -1, -1, 1, 1};
    // int dy[] = new int[] {0, 0, -1, 1, 1, -1, -1, 1};
    int dx[] = new int[] {0, 0, 1, -1, 1, -1, 1, -1};
    int dy[] = new int[] {1, -1, 0, 0, 1, 1, -1, -1};

    public int shortestPathBinaryMatrix(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;
        if (grid[0][0] == 1 || grid[rows - 1][cols - 1] == 1) return -1; //无法到达终点
        Queue<int[]> q = new LinkedList<>(); //存放positions
        boolean[][] visited = new boolean[rows][cols];
        visited[0][0] = true;
        int steps = 0; //起点也有长度
        
        q.offer(new int[]{0, 0});
        while(!q.isEmpty()) {
            int size = q.size();
            while (size > 0) {
                int[] currNode = q.poll();
                int x = currNode[0];
                int y = currNode[1];
                if (x == rows -1 && y == cols - 1) {
                    return steps + 1; //走到终点
                }
                
                for (int i = 0; i < 8; i++) {
                    int xx = x + dx[i];
                    int yy = y + dy[i];
                    
                    if (xx < 0 || xx >= rows || yy < 0 || yy >= cols) continue;
                    if (visited[xx][yy]) continue;
                    if (grid[xx][yy] == 1) continue;
                    q.offer(new int[]{xx, yy});
                    visited[xx][yy] = true;
                }
                size--;   
            }
            steps++;
        }
        return -1;
    }

    //A* Search
    int m, n;
    public int shortestPathBinaryMatrix_2(int[][] grid) {
        m = grid.length; 
        n = grid[0].length;
        if (grid[0][0] == 1 || grid[m-1][n-1] == 1) return -1;
        int steps = 0;
        int step[][] = new int[m][n]; //记录当前步数
        PriorityQueue<Node> queue = new PriorityQueue<>(
            (n1, n2) -> (n1.heuristicVal - n2.heuristicVal)
        );
        step[0][0] = 1;
        Node node = new Node(0, 0, step[0][0]);
        queue.offer(node);
        while (!queue.isEmpty()) {
            Node point = queue.poll();
            steps = step[point.x][point.y];
            if (point.x == m -1 && point.y == n - 1) {
                return steps; //走到终点
            }
            for (int i = 0; i < 8; i++) {
                int xx = point.x + dx[i];
                int yy = point.y + dy[i];
                
                if (xx < 0 || xx >= m || yy < 0 || yy >= n || grid[xx][yy] == 1) continue; //超出边界或障碍物
                if (step[xx][yy] != 0 && step[xx][yy] <= steps + 1) continue; //当前节点已经走过 并且当前步数大于之前走过的步数，所以不必再走这个结点
                step[xx][yy] = steps + 1;
                Node next = new Node(xx, yy, step[xx][yy]); 
                queue.offer(next);
            }
        }
        return -1;
    }

    class Node {
        int x;
        int y;
        int heuristicVal;
        Node(int x, int y, int step) {
            this.x = x;
            this.y = y;
            this.heuristicVal = Math.max(m - 1 - x, n - 1 - y) + step;
        }
    }
    
    public static void main(String args[]) {
        ShortestPathInBinaryMatrix sInBinaryMatrix = new ShortestPathInBinaryMatrix();
        int[][] grid = new int[][] {{0}};
        // {
        //     {0,0,1,0,0,0,0},
        //     {0,1,0,0,0,0,1},
        //     {0,0,1,0,1,0,0},
        //     {0,0,0,1,1,1,0},
        //     {1,0,0,1,1,0,0},
        //     {1,1,1,1,1,0,1},
        //     {0,0,1,0,0,0,0}
        // };

        // {
        //     {0,0,0,0,1,1},
        //     {0,1,0,0,1,0},
        //     {1,1,0,1,0,0},
        //     {0,1,0,0,1,1},
        //     {0,1,0,0,0,1},
        //     {0,0,1,0,0,0}
        // };
        System.out.println(sInBinaryMatrix.shortestPathBinaryMatrix_2(grid));
    }
}
