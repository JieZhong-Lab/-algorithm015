public class ExerciseDisjointSet {
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

    //并查集
}
