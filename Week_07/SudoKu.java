import java.util.Arrays;

public class SudoKu {
    //36. 有效的数独
    public boolean isValidSudoku(char[][] board) {
        //对于位置[i, j], 行 - i, 列 - j
        //box -  (i / 3) * 3 + j / 3
        boolean[][] rows = new boolean[9][10]; //每一行中，每一个数字(1-9)是否出现
        boolean[][] cols = new boolean[9][10]; //每一列中，每一个数字(1-9)是否出现
        boolean[][] boxes = new boolean[9][10]; //每一个box，每一个数字(1-9)是否出现

        for(int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') continue;
                int currNum = board[i][j] - '0';
                if (rows[i][currNum]) return false;
                if (cols[j][currNum]) return false;
                if (boxes[(i/3)*3 + j/3][currNum]) return false;

                rows[i][currNum] = true;
                cols[j][currNum] = true;
                boxes[(i/3)*3 + j/3][currNum] = true;
            }
        }
        return true;
    }

    //37. 解数独
    public void solveSudoku(char[][] board) {
        dfs(board, 0);
    }

    private boolean dfs(char[][] board, int depth) {
        if (depth == 81) return true; //found solution;
        int i = depth / 9, j = depth % 9;
        if (board[i][j] != '.') return dfs(board, depth + 1);

        boolean[] flag = new boolean[10];
        validate(board, i, j, flag);
        for (int k = 1; k <= 9; k++) {
            if (flag[k]) {
                board[i][j] = (char)('0' + k);
                if (dfs(board, depth + 1)) return true;
            }
        }
        board[i][j] = '.'; //if cannot solve, in the wrong path, change back to '.' and out
        return false;
    }

    private void validate(char[][] board, int i, int j, boolean[] flag) {
        Arrays.fill(flag, true);
        for (int k = 0; k < 9; k++) {
            if (board[i][k] != '.') flag[board[i][k] - '0'] = false;
            if (board[k][j] != '.') flag[board[k][j] - '0'] = false;
            int r = i / 3 * 3 + k / 3;
            int c = j / 3 * 3 + k % 3;
            if (board[r][c] != '.') flag[board[r][c] - '0'] = false;
        }
    }
}
