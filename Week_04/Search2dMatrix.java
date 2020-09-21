public class Search2dMatrix {
    //74. 搜索二维矩阵
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false; 
        int m = matrix.length;
        int n = matrix[0].length;
        int startR = 0, startC= 0;
        int endR = m - 1, endC = n - 1;
        while (startR <= endR) {
            int midR = startR + (endR - startR)/2;
            if (target >= matrix[midR][0] && target <= matrix[midR][n-1]) {
                //找到目标行, 再二分查找
                while(startC <= endC) {
                    int midC = startC + (endC - startC)/2;
                    if (target == matrix[midR][midC]) return true;
                    if (target < matrix[midR][midC]) endC = midC - 1;
                    if (target > matrix[midR][midC]) startC = midC + 1;
                }
                return false;
            }
            if (target < matrix[midR][0]) {
                endR = midR - 1;
            }
            if (target > matrix[midR][n-1]) {
                startR = midR + 1;
            }
        }
        return false;
    }

    //Solution 2. 从右上角开始查找
    public boolean searchMatrix_2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return false;
        int m = matrix.length;
        int n = matrix[0].length;
        int i = 0, j = n - 1;
        while (i < m && j >= 0) {
            //如果matrix[i][j]大于target，向左缩进一列
            if (matrix[i][j] > target) {
                j--;
            }
            //如果matrix[i][j]小于target，向下移动一行
            else if (matrix[i][j] < target) {
                i++;
            }
            else {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        Search2dMatrix bs = new Search2dMatrix();
        int[][] matrix = new int[][] {
            {1, 3, 5, 7},
            {10, 11, 16, 20},
            {23, 30, 34, 50}
        };
        System.out.println(bs.searchMatrix_2(matrix, 11));
    }
}
