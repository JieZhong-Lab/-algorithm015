public class MaxSquare {
    //221. 最大正方形
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) return 0;
        int maxSide = 0;
    
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] dp = new int[m][n]; //dp[i][j]表示以 (i, j)(i,j) 为右下角，且只包含1的正方形的边长最大值
        
        for (int i = 0; i < m; i++) 
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == '1') {
                    if (i == 0 || j == 0) {
                        dp[i][j] = 1;
                    }
                    else { 
                        dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i-1][j-1]), dp[i][j-1]) + 1;
                    }
                    maxSide = Math.max(maxSide, dp[i][j]);
                }
            }
        return maxSide * maxSide;
    }
}
