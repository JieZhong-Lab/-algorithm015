public class PalindromicSubStrings {
    //647. 回文子串
    //DP
    //状态：dp[i][j]表示字符串s在[i,j]区间的子串是否是一个回文串
    //当s[i] == s[j] && (j-i < 2 || dp[i+1][j-1]) 时， dp[i][j] = true，否则为false
    public int countSubstrings(String s) {
        int ans = 0;
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        // j - 右边界； i - 左边界
        for (int j = 0; j < n; j++) { //先确定右边界，再定左边界（不超过右边界）
            for (int i = 0; i <= j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i+1][j-1])) {
                    dp[i][j] = true;
                    ans++;
                }
            }
        }
        return ans;
    }

    //中心拓展
    public int countSubstrings_2(String s) {
        int ans = 0;
        int n = s.length();
        //奇数回文中心(aba)和偶数(abba)回文中心一共有 2n - 1 个
        for (int i = 0; i < 2 * n - 1; i++) {
            //中心数与左指针和右指针的关系
            int left = i / 2;
            int right = left + i % 2;
            //System.out.println("l " + left + " r " + right);
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                left--;
                right++;
                ans++;
            }
        }
        return ans;
    }

    //5. 最长回文子串
    //中心扩展
    public String longestPalindrome(String s) {
        String ans  = "";
        int n = s.length();
        for (int i = 0; i < 2 * n - 1; i++) {
            int left = i / 2;
            int right = left + i % 2;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                String tmp = s.substring(left, right + 1);
                if (tmp.length() > ans.length()) {
                    ans = tmp;
                }
                left--;
                right++;
            }
        }
        return ans;
    }

    //DP
    public String longestPalindrome_2(String s) {
        String ans = "";
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        // j - 右边界； i - 左边界
        for (int j = 0; j < n; j++) { //先确定右边界，再定左边界（不超过右边界）
            for (int i = 0; i <= j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i+1][j-1])) {
                    dp[i][j] = true;
                    if (j - i + 1 > ans.length())
                        ans = s.substring(i, j + 1);
                }
            }
        }
        return ans;
    }

    public static void main(String args[]) {
        PalindromicSubStrings pal = new PalindromicSubStrings();
        System.out.println(pal.countSubstrings("aba"));
    }
}
