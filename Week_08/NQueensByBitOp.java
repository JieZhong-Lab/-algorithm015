public class NQueensByBitOp {
    //52. N皇后 II
    int count = 0;
    public int totalNQueens(int n) {
        if (n < 2) return n;
        dfs(n, 0, 0, 0, 0);
        return count;
    }

    /**
     * 
     * @param n
     * @param row - row number
     * @param cols - 用bit位表示占位： 0 - 没有占该列； 1 - 占据该列
     * @param lds  - 用bit位表示占位： 0 - 没有占该主对角线； 1 - 占据该对主角线
     * @param rds  - 用bit位表示占位： 0 - 没有占该副对角线； 1 - 占据该对副角线
     */
    private void dfs(int n, int row, int cols, int lds, int rds) {
        if (row == n) {
            count++;
            return;
        }
        //((1 << n) - 1) 初始时的占位状态，低n位都是1, 1表示可以占据
        //~(cols | lds | rds) 当前的列，主对角线，副对角线的占据，|之后得到所有被占据的位置，取反之后，1表示未被占据的空位
        //& ((1 << n) - 1) 表示将n前面的所有高位都置为0，就是当前可以占据的空位，1表示空位
        int bits = ~(cols | lds | rds) & ((1 << n) - 1);
        System.out.println("row " + row);
        System.out.println("bits: " +Integer.toBinaryString(bits));
        while (bits != 0) {
            int p = bits & -bits; //得到最低位的1
            bits = bits & (bits - 1); //将最低位清零，表示占据位置p
            System.out.println(Integer.toBinaryString(cols | p) + "-" + Integer.toBinaryString((lds | p) << 1) + "-" + Integer.toBinaryString((rds | p) >> 1));
            dfs(n, row + 1, cols | p, (lds | p) << 1, (rds | p) >> 1);
            //不需要revert current state, 因为cols, lds, rds是int型，基本类型是值传递（参数是拷贝了一份），因此当前层的cols，lds，rds不会被改变
        }
    }

    public static void main(String args[]) {
        NQueensByBitOp nBitOp = new NQueensByBitOp();
        nBitOp.totalNQueens(4);
    }
}
