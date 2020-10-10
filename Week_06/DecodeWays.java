import java.util.Arrays;

public class DecodeWays {
    //91. 解码方法
    //DP - 从后往前
    public int numDecodings(String s) {
		if (s == null || s.length() == 0) return 0;
        int n = s.length();
        //从后往前推
		int[] dp = new int[n + 1]; //dp[i]表示从第i个数字开始的解码方法总数
		
		dp[n] = 1; //初始状态
		if (s.charAt(n-1) != '0') //初始状态，对最后一个数字解码，对于非0只有一种解码方法
            dp[n-1] = 1;
        
        //当前数字为0，不可解码, dp[i] = 0;
        //dp[i]由解码当前一位数字 + dp[i+1]，以及，解码当前两位数字(<= 26) + dp[i+2]决定
		for (int i = n - 2; i >= 0; i--) {
			if (s.charAt(i) == '0') continue;

			dp[i] += dp[i + 1];
			int a = (s.charAt(i) - '0') * 10;
			a += s.charAt(i + 1) - '0';
			if (a <= 26) {
				dp[i] += dp[i + 2];
			}
		}
		return dp[0];
    }
    
    //DP - 从前往后
    public int numDecodings_3(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        int n = s.length();

        int[] dp = new int[n + 1]; //dp[i]表示[0, i - 1]的解码方法数
        dp[0] = 1; 
        dp[1] = 1; //一个字符的解码方法
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (s.charAt(i-1) == '1' || s.charAt(i-1) == '2')
                    dp[i + 1] = dp[i - 1];
            } else {
                if (s.charAt(i-1) == '1' || (s.charAt(i-1) == '2' && s.charAt(i) <= '6')) {
                    dp[i + 1] = dp[i] + dp[i -1];
                } else { // > 26, 只能单个解码
                    dp[i + 1] = dp[i];
                }
            }
        }
        return dp[n];
    }

    //DP - 从前往后 - 空间优化
    public int numDecodings_4(String s) {
        if (s == null || s.length() == 0) return 0;
        if (s.charAt(0) == '0') return 0;
        int n = s.length();
        int pre = 1, curr = 1; //dp[-1] = 1; dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int tmp = curr;
            if (s.charAt(i) == '0') {
                if (s.charAt(i-1) == '1' || s.charAt(i-1) == '2')
                    curr = pre;
                else
                    return 0;
            } else {
                if (s.charAt(i-1) == '1' || (s.charAt(i-1) == '2' && s.charAt(i) <= '6')) {
                    curr += pre;
                } //else s[i-1, i] > 26, 只能单个解码
            }
            pre = tmp;
        }
        return curr;
    }

	//递归
	public int numDecodings_1(String s) {
		if (s == null || s.length() == 0) return 0;

		return numDecodings_helper(s, 0);
	}
	
	private int numDecodings_helper(String s, int start) {
		if (start == s.length()) return 1;
		int ans = 0;

		if (s.charAt(start) - '0' == 0) {
            return 0;
        } 
		ans += numDecodings_helper(s, start + 1);
		
		if (start < s.length() - 1) {
			int a = (s.charAt(start) - '0') * 10;
			int b = s.charAt(start + 1) - '0';

			if (a + b <= 26) {
				ans += numDecodings_helper(s, start + 2);
			}
		}
		return ans;
	}

	//优化递归
	public int numDecodings_2(String s) {
		if (s == null || s.length() == 0) return 0;

		int[] cnt = new int[s.length() + 1];
		Arrays.fill(cnt, -1);
		return numDecodings_2_helper(s, 0, cnt);
	}
	private int numDecodings_2_helper(String s, int start, int[] cnt) {
		 //terminator
		 if (s.length() == start) return 1;
		 if (s.charAt(start) == '0') return 0; //剪枝1

		 if (cnt[start] != -1) return cnt[start]; //计算过
		
		 int res = 0;
		 //drill down
		 //case 1. 取1位数解码
		 res += numDecodings_2_helper(s, start + 1, cnt);

		 //case 2. 取2位数解码
		 if (start < s.length() - 1) {
			int a = (s.charAt(start) - '0') * 10;
		 	a += s.charAt(start + 1) - '0'; 
			 if (a <= 26) {//剪枝2
				//drill down
				res += numDecodings_2_helper(s, start + 2, cnt);
		 	}
		 }
		 
		 cnt[start] = res; //记忆化
		 return res;
	}
}
