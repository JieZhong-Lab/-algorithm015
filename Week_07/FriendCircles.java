import java.util.LinkedList;
import java.util.Queue;

public class FriendCircles {
    //547. 朋友圈
    //DFS
    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return 0;
        boolean[] visited = new boolean[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }
    
    public void dfs(int[][] M, boolean[] visisted, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && !visisted[j]){
                visisted[j] = true;
                dfs(M, visisted, j);
            }
        }
    }

    //BFS
    public int findCircleNum_2(int[][] M) {
        boolean[] visited = new boolean[M.length];
        int count = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < M.length; i++) {
            if (!visited[i]) {
                queue.offer(i);
                while (!queue.isEmpty()) {
                    int curr = queue.poll();
                    visited[curr] = true;
                    for (int j = 0; j < M.length; j++) {
                        if (M[curr][j] == 1 && !visited[j]) {
                            queue.offer(j);
                        }
                    }
                }
                count++;
            }
        }
        return count;
    }

    //并查集
    public int findCircleNum_3(int[][] M) {
        if (M == null || M.length == 0 || M[0].length == 0) return 0;
        int n = M.length;
        DisjointSet uf = new DisjointSet(n);
        for (int i = 0; i < n -1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) uf.union(i, j);
            }
        }
        return uf.getCount();
    }

    public static void main(String args[]) {
        FriendCircles ex = new FriendCircles();
        int[][] M = new int[][]{
            {1,1,0,0,0,0},
            {1,1,0,0,0,0},
            {0,0,1,1,1,0},
            {0,0,1,1,0,0},
            {0,0,1,0,1,0},
            {0,0,0,0,0,1}};
        System.out.println(ex.findCircleNum_2(M));
    }
}

class DisjointSet {
    private int count = 0;
    private int[] parent;

    public DisjointSet(int n) {
        count = n;
        parent = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;
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
        if (rootP == rootQ) return;
        parent[rootP] = rootQ;
        count--;
    }

    public int getCount() {
        return count;
    }
}
