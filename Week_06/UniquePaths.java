import java.util.Arrays;

public class UniquePaths {
    //62. 不同路径
    // public int uniquePaths(int m, int n) {
    //     int[][] paths = new int[m][n];

    //     for (int i = 0; i < m; i++) paths[i][n-1] = 1;
    //     for (int i = 0; i < n; i++) paths[0][i] = 1;

    //     for (int i = 1; i < m; i++) {
    //         for (int j = n-2; j >= 0; j--) {
    //             paths[i][j] = paths[i-1][j] + paths[i][j+1];
    //         }
    //     }
    //     return paths[m-1][0];
    // }

    public int uniquePaths_2(int m, int n) {
        int[][] paths = new int[m][n];  //paths[i][j]表示从左上角出发到i，j一共有多少条路径

        for (int i = 0; i < m; i++) paths[i][0] = 1;
        for (int i = 0; i < n; i++) paths[0][i] = 1;

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                paths[i][j] = paths[i-1][j] + paths[i][j-1];
            }
        }
        return paths[m-1][n-1];
    }
    //使用滚动数组
    public int uniquePaths_3(int m, int n) {
        int[] dp = new int[n]; //只需要一维数组
        Arrays.fill(dp, 1);
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[j] += dp[j-1];
            }
        }
        return dp[n-1];
    }

    //63. 不同路径 II
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[][] paths = new int[m][n];
        if (obstacleGrid[0][0] == 0) paths[0][0] = 1;
        for (int i = 1; i < m; i++) {
            if (obstacleGrid[i][0] == 0 && paths[i-1][0] == 1) paths[i][0] = 1;
        }
        for (int i = 1; i < n; i++) {
            if (obstacleGrid[0][i] == 0 && paths[0][i-1] == 1) paths[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (obstacleGrid[i][j] != 1) 
                    paths[i][j] = paths[i-1][j] + paths[i][j-1];
                else paths[i][j] = 0;
            }
        }
        return paths[m-1][n-1];
    }
    //使用滚动数组
    public int uniquePathsWithObstacles_2(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] paths = new int[n];
        if (obstacleGrid[0][0] != 1) paths[0] = 1;
        else return 0;

        for (int i = 1; i < n; i++) {
            if (obstacleGrid[0][i] != 1 && paths[i-1] == 1) paths[i] = 1;
        }

        for (int i = 1; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    paths[j] = 0;
                    continue;
                }
                //if (j == 0) paths[j] = paths[j]; 第一列的格子，为障碍则前面已经设路径数为0，不为障碍则路径数同前
                if (j > 0) paths[j] += paths[j-1];
            }
        return paths[n-1];
    }
    public int uniquePathsWithObstacles_3(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int[] paths = new int[n];
        if (obstacleGrid[0][0] != 1) paths[0] = 1;
        else return 0;

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (obstacleGrid[i][j] == 1) {
                    paths[j] = 0;
                    continue;
                }
                //if (j == 0) paths[j] = paths[j]; 第一列的格子，为障碍则前面已经设路径数为0，不为障碍则路径数同前
                if (j > 0) paths[j] += paths[j-1];
            }
        return paths[n-1];
    }

    public static void main(String args[]) {
        UniquePaths up = new UniquePaths();
        int[][] obstacleGrid = new int[][] {{0}, {1}};
        System.out.println(up.uniquePathsWithObstacles_2(obstacleGrid));
    }
}
