import java.util.Stack;

public class LongestValidParentheses {
    //32. 最长有效括号
    //DP
    public int longestValidParentheses(String s) {
        int ans = 0;
        int[] dp = new int[s.length()]; 
        /** dp[i]是以第i个字符结尾的最长的有效括号 
         * 要组成有效括号最后一个字符必须是')'
         * 判断i前面一个字符：
         * 1.'('  - dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2
         * 2.')'  - 如果和i-1对应的位置是'(', 则 dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2]: 0)
        */
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = (i >= 2 ? dp[i - 2] : 0) + 2;
                } else { //s.charAt(i - 1) == ')'
                    if (i - dp[i - 1] > 0 && s.charAt(i - dp[i - 1] - 1) == '(') {
                        dp[i] = dp[i - 1] + 2 + (i - dp[i - 1] >= 2 ? dp[i - dp[i - 1] - 2]: 0);
                    }
                }
                ans = Math.max(ans, dp[i]);
            }
        }
        return ans;
    }

    //栈（哨兵）
    /**
     * 始终保持栈底为元素为当前已经遍历的元素中“最后一个没有被匹配的右括号的下标”
     * 栈里其他元素维护左括号的下标
     */
    public int longestValidParentheses_2(String s) {
        int ans = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {//s.charAt(i) == ')'
                stack.pop();
                if (stack.isEmpty()) { //始终保持栈底为元素为当前已经遍历的元素中“最后一个没有被匹配的右括号的下标”
                    stack.push(i);
                } else {
                    ans = Math.max(ans, i - stack.peek());
                }
            }
        }
        return ans;
    }

    //贪心
    public int longestValidParentheses_3(String s) {
        int l = 0, r = 0, ans = 0;
        
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') l++;
            else r++;

            if (l == r) {
                ans = Math.max(ans, 2 * r);
            } else if (l < r) {
                l = r = 0;
            }
        }

        l = r = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '(') l++;
            else r++;

            if (l == r) {
                ans = Math.max(ans, 2 * r);
            } else if (l > r) {
                l = r = 0;
            }
        }
        return ans;
    }

    public static void main(String args[]) {
        LongestValidParentheses lParentheses = new LongestValidParentheses();
        System.out.println(lParentheses.longestValidParentheses_2("()))"));
    }
}
