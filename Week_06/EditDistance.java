public class EditDistance {
    //72. 编辑距离
    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) return 0;
        if (word1 == null) return word2.length();
        if (word2 == null) return word1.length();

        int m = word1.length();
        int n = word2.length();
        int ic = 1, dc = 1, rc = 1;
        int[][] dp = new int[m+1][n+1];

        for (int i = 1; i <= m; i++) dp[i][0] = i * dc;
        for (int i = 1; i <= n; i++) dp[0][i] = i * ic;
        for (int i = 1; i <= m; i++) 
            for (int j = 1; j <= n; j++) {
                int cost1 = dp[i-1][j] + dc;
                int cost2 = dp[i][j-1] + ic;
                int cost3 = 0;
                if (word1.charAt(i-1) == word2.charAt(j-1))
                    cost3 = dp[i-1][j-1];
                else
                    cost3 = dp[i-1][j-1] + rc;
                
                dp[i][j] = Math.min(cost1, Math.min(cost2, cost3));
            }
        return dp[m][n];
    }

    public static void main(String args[]) {
        EditDistance ed = new EditDistance();
        System.out.println(ed.minDistance("horse", "ros"));
    }
}
