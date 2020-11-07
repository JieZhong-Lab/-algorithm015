import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class W6Review {
    //17. 电话号码的字母组合
    Map<Character, String> dic;
    public List<String> letterCombinations(String digits) {
        dic = new HashMap<>();
        dic.put('2', "abc");
        dic.put('3', "def");
        dic.put('4', "ghi");
        dic.put('5', "jkl");
        dic.put('6', "mno");
        dic.put('7', "pqrs");
        dic.put('8', "tuv");
        dic.put('9', "wxyz");

        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0) return ans;
        
        dfs(digits, 0, "", ans);
        return ans;
    }

    //处理当前index位置的数字
    private void dfs(String digits, int index, String output, List<String> ans) {
        if (index == digits.length()) {
            ans.add(output);
            return;
        }
        
        String s = dic.get(digits.charAt(index));
        for (char ch : s.toCharArray()) {
            dfs(digits, index + 1, output + ch, ans);
        }
    }

    //84. 柱状图中最大的矩形
    //暴力 - 双指针
    public int largestRectangleArea_2(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        int len = heights.length; 
        int maxArea = 0;

        for (int i = 0; i < len; i++) {
            // System.out.println("Ind: " + i + " height: " + heights[i]);
            int curr = heights[i];
            int l = i, r = i;
            while (l > 0 && heights[l - 1] >= curr) l--;
            while (r < len - 1 && heights[r + 1] >= curr) r++;
            // System.out.println("L: " + l + " R: " + r);
            maxArea = Math.max(curr * (r - l + 1), maxArea);
        }
        return maxArea;
    }
    //单调栈
    public int largestRectangleArea_3(int[] heights) {
        if (heights == null || heights.length == 0) return 0;

        int len = heights.length; 
        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        for (int i = 0; i < len; i++) {
            while(stack.peek() != -1 && heights[stack.peek()] >= heights[i]) {
                maxArea = Math.max(maxArea, heights[stack.pop()] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            maxArea = Math.max(maxArea, heights[stack.pop()] * (len - stack.peek() - 1));
        }
        return maxArea;
    }

    //22. 括号生成
    //递归
    public List<String> generateParenthesis(int n) {
        List<String> ans = new ArrayList<>();
        if (n == 0) return ans;
        
        generator(n, 0, 0, ans, "");
        return ans;
    }

    private void generator(int n, int l, int r, List<String> ans, String output) {
        if (l == n && r == n) {
            ans.add(output);
            return;
        }
        if (l < n) generator(n, l + 1, r, ans, output + "(");
        if (r < l) generator(n, l, r + 1, ans, output + ")");
    }
    //DP
    public List<String> generateParenthesis_2(int n) {
        ArrayList<ArrayList<String>> dp = new ArrayList<>(); //dp.get(i) 表示i个括号的可能组合
        //递推公式
        //dp[i]="(" + dp[p] + ")" + dp[q] (其中 p + q = i - 1)
        ArrayList<String> dp_0 = new ArrayList<>();
        dp_0.add("");
        dp.add(dp_0);
        ArrayList<String> dp_1 = new ArrayList<>();
        dp_1.add("()");
        dp.add(dp_1);
        for (int i = 2; i <= n; i++) {
            ArrayList<String> dp_i = new ArrayList<>();
            for (int j = 0; j < i; j++) {// j 模拟 p + q
                List<String> dp_p = dp.get(j);
                List<String> dp_q = dp.get(i - 1 - j);
                for (String s1 : dp_p) {
                    for (String s2 : dp_q) {
                        String output = "(" + s1 + ")" + s2;
                        dp_i.add(output);
                    }
                }
            }
            dp.add(dp_i);
        }
        return dp.get(n);
    }

    //69. x 的平方根
    public int mySqrt(int x) {
        long l = 0, r = x / 2 + 1;
        while (l <= r) {
            long mid = l + (r - l)/2;
            long square = mid * mid;
            if (square > x) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return (int)r;
    }
    public int mySqrt_2(int x) {
        if (x == 0 || x == 1) return x;
        int l = 0, r = x / 2 + 1;
        int ans = 0;
        while (l <= r) {
            int mid = l + (r - l)/2;
            if ((long)mid * mid > x) {
                r = mid - 1;
            } else {
                ans = mid;
                l = mid + 1;
            }
        }
        return ans;
    }

    //367. 有效的完全平方数
    public boolean isPerfectSquare(int num) {
        if (num < 2) {
            return true;
        }
        long l = 2, r = num / 2;
        while (l <= r) {
            long mid = l + (r - l)/2;
            long guess = mid * mid;
            if (guess == num) return true;
            else if (guess > num) r = mid - 1;
            else l = mid + 1;
        }
        return false;
    }

    public static void main(String args[]) {
        W6Review review = new W6Review();
        // int[] heights = new int[] {6,7,5,2,4,5,9,3};
        // System.out.println(review.largestRectangleArea_3(heights));
        List<String> list = review.generateParenthesis_2(3);
        list.forEach(s -> System.out.println(s));
    }
}
