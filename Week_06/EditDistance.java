public class EditDistance {
    //72. 编辑距离
    /*
         * 字符串之间的距离，编辑距离，将strA编辑成strB所需的最小代价 编辑操作包括插入一个字符、删除一个字符、替换一个字符
         * 分别对应的代价是ic、dc、rc，insert cost、delete cost、replace cost
         * strA[x-1]代表strA的第x个字符，注意下标是从0开始的,strB[y-1]代表strB的第y个字符 定义一个代价矩阵为(N+1)*(M+1)，
         * M, N 表示strA strB的长度 dp[x][y]表示strA的前x个字符串编辑成 strB的前y个字符所花费的代价
         * dp[x][y]是下面几种值的最小值： 1、dp[x][y] = dp[x-1][y] + dc
         * dp[x-1][y]将strA的前x-1个字符编辑成strB的前y个字符的代价已知，
         * 那么将将strA的前x个字符编辑成strB的前y个字符的代价dp[x][y]就是dp[x-1][y] + dc
         * 相当于strA的前x-1个字符编辑成strB的前y个字符，现在变成了strA的前x个字符，增加了一个字符，要加上删除代价 
         * 2、dp[x][y] = dp[x][y-1] + ic dp[x][y-1]将strA的前x个字符编辑成strB的前y-1个字符的代价已知，
         * 现在变为strB的前y个字符，相应的在strA前x个操作代价的基础上插入一个字符 3、dp[x][y] = dp[x-1][y-1]
         * dp[x-1][y-1]将strA的前x-1个字符编辑成strB的前y-1个字符的代价已知，
         * strA的第x个字符和strB的第y个字符相同，strA[x-1] == strB[y-1]，没有引入操作 4、dp[x][y] =
         * dp[x-1][y-1] + rc strA的第x个字符和strB的第y个字符不相同，strA[x-1] ！= strB[y-1]，
         * 在strA的前x-1个字符编辑成strB的前y-1个字符的代价已知的情况下，
         * 计算在strA的前x字符编辑成strB的前y个字符的代价需要加上替换一个字符的代价
         */
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
