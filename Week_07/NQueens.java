import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

//51. N 皇后
public class NQueens {
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        if (n == 0) return ans;
        boolean[] cols = new boolean[n]; //记录已经占据的列，一共n列
        boolean[] mainDiags = new boolean[2*n - 1]; //记录已经占据的主对角线，一共2*n-1条主对角线
        boolean[] secDiags = new boolean[2*n - 1]; //记录已经占据的副对角线，一共2*n-1条副对角线
        Deque<Integer> output = new LinkedList<>();
        dfs(n, cols, mainDiags, secDiags, output, 0, ans);
        return ans;
    }
    /**
     * 表示在第0 ～ r - 1行皇后都已经摆放好都情况下，摆第r行及其后面第皇后
     * @param n
     * @param cols
     * @param mainDiags
     * @param secDiags
     * @param output - 队列中的每个元素的下标表示行，元素的的值表示放置的列的位置 (也即队列表示当前棋盘)
     * @param r -   当前行
     * @param ans
     */
    private void dfs(int n, boolean[] cols, boolean[] mainDiags, boolean[] secDiags, Deque<Integer> output, int r, List<List<String>> ans) {
        //terminator
        if (r == n) {
            List<String> currBoard = generateBoard(output, n);
            ans.add(currBoard);
            return;
        }
        //遍历当前行的每一列
        for (int c = 0; c < n; c++) {
            //剪枝
            if (cols[c] || mainDiags[r + c] || secDiags[n - 1 + r - c])  continue;
            //process current logic
            //在位置[r, c]放置Q
            cols[c] = mainDiags[r + c] = secDiags[n - 1 + r - c] = true;
            output.addLast(c);
            //drill down
            dfs(n, cols, mainDiags, secDiags, output, r + 1, ans);

            //restore current status
            //在位置[r, c]不放置Q
            cols[c] = mainDiags[r + c] = secDiags[n - 1 + r - c] = false;
            output.removeLast();    
            
        }
    }

    private List<String> generateBoard(Deque<Integer> path, int n) {
        List<String> board = new ArrayList<>();
        for (int ind : path) {
            StringBuilder row = new StringBuilder();
            row.append(".".repeat(n));
            row.replace(ind, ind + 1, "Q");
            board.add(row.toString());
        }
        return board;
    }

    //52. N皇后 II
    int count = 0;
    public int totalNQueens(int n) {
        if (n == 0) return 0;
        
        boolean[] cols = new boolean[n]; //记录已经占据的列，一共n列
        boolean[] mainDiags = new boolean[2*n - 1]; //记录已经占据的主对角线，一共2*n-1条主对角线
        boolean[] secDiags = new boolean[2*n - 1]; //记录已经占据的副对角线，一共2*n-1条副对角线
        dfs(n, cols, mainDiags, secDiags, 0);

        return count;
    }

    private void dfs(int n, boolean[] cols, boolean[] mainDiags, boolean[] secDiags, int r) {
        //terminator
        if (r == n) {
            count++;
            return;
        }
        //遍历当前行的每一列
        for (int c = 0; c < n; c++) {
            if (!cols[c] && !mainDiags[r + c] && !secDiags[n - 1 + r - c]) {
                //process current logic
                //在位置[r, c]放置Q
                cols[c] = mainDiags[r + c] = secDiags[n - 1 + r - c] = true;
                
                //drill down
                dfs(n, cols, mainDiags, secDiags, r + 1);

                //restore current status
                cols[c] = mainDiags[r + c] = secDiags[n - 1 + r - c] = false;
            }
        }
    }
    public static void main(String args[]) {
        NQueens nq = new NQueens();
        //List<List<String>> ans = nq.solveNQueens(8);
        System.out.println(nq.totalNQueens(8));
        // for (List<String> board : ans) {
        //     board.forEach(row -> System.out.println(row));
        //     System.out.println("------");
        // }
    }
}
