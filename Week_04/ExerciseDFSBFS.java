import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class ExerciseDFSBFS {
    //433. 最小基因变化
    int minStep = Integer.MAX_VALUE;
    public int minMutation(final String start, final String end, final String[] bank) {
        final Set<String> visited = new HashSet<>();
        dfs(start, end, bank, visited, 0);
        return minStep == Integer.MAX_VALUE ? -1 : minStep;
    }

    private void dfs(final String current, final String end, final String[] bank, final Set<String> visited,
            final int stepCount) {
        if (current.equals(end)) {
            minStep = Math.min(stepCount, minStep);
        }
        for (final String str : bank) {
            int diff = 0;
            // 从基因库中找出相差一位的基因
            for (int i = 0; i < str.length(); i++) {
                if (current.charAt(i) != str.charAt(i))
                    if (++diff > 1)
                        break;
            }
            if (diff == 1 && !visited.contains(str)) {
                visited.add(str);
                dfs(str, end, bank, visited, stepCount + 1);
                visited.remove(str);
            }
        }
    }

    // 515. 在每个树行中找最大值
    //DFS
    public List<Integer> largestValues(final TreeNode root) {
        final List<Integer> ans = new ArrayList<>();
        if (root == null)
            return ans;

        final Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            while (size > 0) {
                final TreeNode node = queue.poll();
                max = Math.max(node.val, max);
                if (node.left != null)
                    queue.offer(node.left);
                if (node.right != null)
                    queue.offer(node.right);
                size--;
            }
            ans.add(max);
        }
        return ans;
    }

    //BFS
    public List<Integer> largestValues_2(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        largestValues_2_recur(root, 0, ans);
        return ans;
    }
    private void largestValues_2_recur(TreeNode root, int level, List<Integer> ans) {
        if (root == null) return;

        if (ans.size() <= level) {
            ans.add(root.val);
        } else {
            int max = Math.max(ans.get(level), root.val);
            ans.set(level, max);
        }
        largestValues_2_recur(root.left, level + 1, ans);
        largestValues_2_recur(root.right, level + 1, ans);
    }

    //200. 岛屿数量
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

    public static void main(final String ars[]) {
        final ExerciseDFSBFS exDFSBFS = new ExerciseDFSBFS();
        final String start = "AACCGGTT";
        final String end = "AAACGGTA";
        final String[] bank = new String[] { "AACCGGTA", "AACCGCTA", "AAACGGTA" };
        System.out.println(exDFSBFS.minMutation(start, end, bank));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(final int x) {
        this.val = x;
    }
}
