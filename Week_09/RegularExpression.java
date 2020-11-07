import java.util.HashMap;
import java.util.Map;

public class RegularExpression {
    //10. 正则表达式匹配
    //递归
    public boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        boolean firstMatch = !s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
        if (p.length() >= 2 && p.charAt(1) == '*') {
            return isMatch(s, p.substring(2)) || (firstMatch && isMatch(s.substring(1), p));
        } else {
            return firstMatch && isMatch(s.substring(1), p.substring(1));
        }
    }
    
    //递归 - 记忆化
    Map<String, Boolean> memo = new HashMap<>();
    public boolean isMatch_2(String s, String p) {
        return isMatch_recur(s, 0, p, 0);
    }

    private boolean isMatch_recur(String s, int i, String p, int j) {
        if (memo.containsKey(i + "-" + j)) return memo.get(i + "-" + j);
        if (j == p.length()) return i == s.length();
        boolean ans = false;
        boolean firstMatch = i < s.length() && (p.charAt(j) == s.charAt(i) || p.charAt(j) == '.');

        if (j + 2 <= p.length() && p.charAt(j + 1) == '*') {
            ans = isMatch_recur(s, i, p, j + 2) || (firstMatch && isMatch_recur(s, i + 1, p, j));
        } else {
            ans = firstMatch && isMatch_recur(s, i + 1, p, j + 1);
        }
        memo.put(i + "-" + j, ans);
        return ans;
    }

    //只有.的情况
    public boolean isMatch_simple(String s, String p) {
        // if (s.isEmpty() && p.isEmpty()) return true;
        // if (s.isEmpty() || p.isEmpty()) return false;
        if (p.isEmpty()) return s.isEmpty();
        boolean firstMatch = !s.isEmpty() && (p.charAt(0) == s.charAt(0) || p.charAt(0) == '.');
        return firstMatch && isMatch_simple(s.substring(1), p.substring(1));
    }

    public static void main(String args[]) {
        RegularExpression re = new RegularExpression();
        // System.out.println(re.isMatch_simple("abc", "a."));
        System.out.println(re.isMatch_2("mississippi", 
                                      "mis*is*p*."));
    }
}
