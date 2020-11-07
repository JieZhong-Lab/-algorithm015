public class PalindromeString {
    //125. 验证回文串
    public boolean isPalindrome(String s) {
        int l = 0, r = s.length() - 1;
        while (l < r) {
            while (l < r && !Character.isLetterOrDigit(s.charAt(l))) l++;
            while (l < r && !Character.isLetterOrDigit(s.charAt(r))) r--;
            if (l < r) {
                if (Character.toLowerCase(s.charAt(l)) != Character.toLowerCase(s.charAt(r))) {
                   return false;
                } 
                l++; r--;
            }
        }
        return true;
    }

    public boolean isPalindrome2(String s) {
        StringBuffer sb = new StringBuffer();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char ch = s.charAt(i);
            if (Character.isLetterOrDigit(ch)) 
                sb.append(Character.toLowerCase(ch));
        }
        StringBuffer revserString = new StringBuffer(sb).reverse();
        System.out.println(sb.toString());
        return revserString.toString().equals(sb.toString());
    }

    //680. 验证回文字符串 Ⅱ
    public boolean validPalindrome2(String s) {
        int len = s.length();
        int l = 0, r = len - 1;
        while (l < r) {
            if (s.charAt(l) == s.charAt(r)) {
                l++; r--;
            } else {
                boolean flag1 = true, flag2 = true;
                for (int i = l, j = r - 1; i < j; i++, j--) {
                    if (s.charAt(i) != s.charAt(j)) {
                        flag1 = false; break;
                    }
                }
                for (int i = l + 1, j = r; i < j; i++, j--) {
                    if (s.charAt(i) != s.charAt(j)) {
                        flag2 = false; break;
                    }
                }
                return flag1 || flag2;
            }
        }
        return true;
    }

    public boolean validPalindrome(String s) {
        int cnt1 = 0, cnt2 = 0;
        int l = 0, r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) == s.charAt(r)) {
                l++; r--;
            } else {
                l++; cnt1++;
            }
        }
        l = 0; r = s.length() - 1;
        while (l < r) {
            if (s.charAt(l) == s.charAt(r)) {
                l++; r--;
            } else {
                r--; cnt2++;
            }
        }

        return cnt1 <= 1 || cnt2 <= 1; 
    }

    //5. 最长回文子串
    public String longestPalindrome(String s) {
        String ans = "";
        int n = s.length();
        boolean[][] dp = new boolean[n][n]; //dp[i][j]表示子串s[i...j]是否是子串
        for (int r = 0; r < n; r++) {   //右边界
            for (int l = 0; l <= r; l++) {  //左边界
                if (s.charAt(l) == s.charAt(r) && (r - l < 2 || dp[l + 1][r - 1])) {
                    dp[l][r] = true;
                    if (r - l + 1 > ans.length()) {
                        ans = s.substring(l, r + 1);
                    }
                }
            }
        }
        return ans;
    }

    public String longestPalindrome_2(String s) {
        int n = s.length();
        int start = 0, end = 0;
        for (int i = 0; i < n; i++) {
            int len1 = expand(s, i, i);
            int len2 = expand(s, i, i + 1);
            int len = len1 > len2 ? len1 : len2;
            if (len > end - start + 1) {
                start = i - (len - 1)/2;
                end = i + len / 2;
            }
        }
        return s.substring(start, end + 1);
    }

    private int expand(String s, int l, int r) {
        while (l >= 0 && r < s.length() && s.charAt(l) == s.charAt(r)) {
            --l;
            ++r;
        }
        return r - l - 1;
    }
}
