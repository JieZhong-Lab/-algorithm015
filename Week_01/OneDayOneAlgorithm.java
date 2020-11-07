import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class OneDayOneAlgorithm {
	//1021. 删除最外层的括号
	public String removeOuterParentheses(String S) {
		Stack<Character> stack = new Stack<>();
		StringBuilder ans = new StringBuilder();

		int start = 0;
		for (int i = 0; i < S.length(); i++) {
			if (S.charAt(i) == '(') {
				stack.push('(');
			} else {
				stack.pop();
				if (stack.isEmpty()) {
					ans.append(S.substring(start + 1, i));
					start = i + 1;
				}
			}
		}
		return ans.toString();
	}

	//简洁代码
	public String removeOuterParentheses2(String S) {
		StringBuilder ans = new StringBuilder();
		int level = 0;
		for (char c : S.toCharArray()) {
			if (c == ')') level--;
			if (level >= 1) ans.append(c);
			if (c == '(') level++;
		}
		return ans.toString();
	}
	
	// 299. 猜数字游戏
	// https://leetcode-cn.com/problems/bulls-and-cows/
	//one bucket TODO ..
	//two bucket
	public String getHint2(String secret, String guess) {
		int len = secret.length();
		int[] secArr = new int[10];
		int[] gusArr = new int[10];
		int bulls = 0, cows = 0;
		for (int i = 0; i < len; i++) {
			if (secret.charAt(i) == guess.charAt(i)) {
				bulls++;
			} else {
				++secArr[secret.charAt(i) - '0'];
				++gusArr[guess.charAt(i) - '0'];
			}
		}
		
		for (int i = 0; i < 10; i++) 
			cows += Math.min(secArr[i], gusArr[i]);
		
		return bulls+"A"+cows+"B";
	}
	public String getHint(String secret, String guess) {
		int A = 0, B = 0;
		int len = secret.length();
		Map<Character, Integer> map = new HashMap<> ();
		for (int i = 0; i < len; i++) {
			if (secret.charAt(i) == guess.charAt(i)) {
				A++;
				continue;
			} 
			map.put(secret.charAt(i), map.getOrDefault(secret.charAt(i), 0) + 1);
		}
		
		for (int i = 0; i < len; i++) {
			if (secret.charAt(i) != guess.charAt(i) && map.containsKey(guess.charAt(i))) {
				B++;
				int t = map.get(guess.charAt(i));
				if (--t > 0) {
					map.put(guess.charAt(i), t);
				} else 
					map.remove(guess.charAt(i));
			}
		}
		return A+"A"+B+"B";
	}

	// 290. 单词规律
	// https://leetcode.com/problems/word-pattern/
	public boolean wordPattern3(String pattern, String str) {
		if (pattern == null || str == null) return false;

		String[] strArr = str.split(" ");
		if (pattern.length() != strArr.length) return false;

		Map<Character, String> map = new HashMap<>();
		map.put(pattern.charAt(0), strArr[0]);
		for (int i = 1; i < pattern.length(); i++) {
			if (map.containsKey(pattern.charAt(i))) {
				if (!map.get(pattern.charAt(i)).equals(strArr[i]))
					return false;
			} else {
				for (char c : map.keySet()) {
					if (map.get(c).equals(strArr[i])) 
						return false;
				}
				map.put(pattern.charAt(i), strArr[i]);
			}
		}
		return true;
	}

	public boolean wordPattern(String pattern, String str) {
		if (pattern == null || str == null)
			return pattern == str;

		String[] words = str.split(" ");
		if (pattern.length() != words.length)
			return false;

		Map<Character, String> map = new HashMap<>();
		for (int i = 0; i < pattern.length(); i++) {
			char ch = pattern.charAt(i);
			if (map.containsKey(ch)) {
				if (!map.get(ch).equals(words[i]))
					return false;
			} else {
				if (i >= 1) {
					for (char c : map.keySet()) {
						if (map.get(c).equals(words[i]))
							return false;
					}
				}
				map.put(ch, words[i]);
			}
		}
		return true;
	}

	public boolean wordPattern2(String pattern, String str) {
		Map map = new HashMap();
		String[] words = str.split("//s");

		if (words.length != pattern.length())
			return false;

		// track of the first occurrences of each character in pattern and each word in
		// str
		for (int i = 0; i < words.length; i++) {
			char c = pattern.charAt(i);
			String w = words[i];
			if (!map.containsKey(c)) {
				map.put(c, i);
			}

			if (!map.containsKey(w)) {
				map.put(w, i);
			}
			if (map.get(c) != map.get(w)) {
				return false;
			}
		}
		return true;
	}


	// 70.Climbing Stairs
	// https://leetcode.com/problems/climbing-stairs/submissions/
	public int climbStairs(int n) {
		int w[] = new int[n + 1];
		w[0] = 1;
		w[1] = 1;

		for (int i = 2; i <= n; i++) {
			w[i] = w[i - 2] + w[i - 1];
		}
		return w[n];
	}

	// 66.Plus one
	public int[] plusOne(int[] digits) {
		int len = digits.length;
		for (int i = len - 1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i] += 1;
				return digits;
			} else {
				digits[i] = 0;
			}
		}

		int arr[] = new int[len + 1];
		arr[0] = 1;
		return arr;
	}

	// 1528. 重新排列字符串
	public String restoreString(String s, int[] indices) {
		int len = s.length();
		char[] ss = new char[len];
		for (int i = 0; i < len; i++) {
			ss[indices[i]] = s.charAt(i);
		}

		return String.valueOf(ss);
	}

	//面试题 01.01. 判定字符是否唯一
	public boolean isUnique2(String astr) {
		int len = astr.length();
		if (len < 2)
			return true;
		Set<Character> set = new HashSet<>();
		for (int i = 0; i < len; i++) {
			if (set.contains(astr.charAt(i)))
				return false;
			set.add(astr.charAt(i));
		}
		return true;
	}

	public boolean isUnique(String astr) {
		int len = astr.length();
		for (int i = 0; i < len - 1; i++) {
			for (int j = i + 1; j < len; j++) {
				if (astr.charAt(i) == astr.charAt(j))
					return false;
			}
		}
		return true;
	}

	// 412. Fizz Buzz
	public List<String> fizzBuzz2(int n) {
		if (n <= 0)
			return null;
		List<String> ans = new ArrayList<>();

		Map<Integer, String> dic = new LinkedHashMap<>() {
			{
				put(3, "Fizz");
				put(5, "Buzz");
			}
		};

		for (int i = 1; i <= n; i++) {
			String element = "";
			for (Integer key : dic.keySet()) {
				if (i % key == 0) {
					element += dic.get(key);
				}
			}

			if ("".equals(element))
				element = Integer.toString(i);

			ans.add(element);
		}
		return ans;
	}

	public List<String> fizzBuzz(int n) {
		if (n <= 0)
			return null;
		List<String> ans = new ArrayList<>();

		for (int i = 1; i <= n; i++) {
			if (i % 15 == 0)
				ans.add("FizzBuzz");
			else if (i % 5 == 0)
				ans.add("Buzz");
			else if (i % 3 == 0)
				ans.add("Fizz");
			else
				ans.add("" + i);
		}
		return ans;
	}

	//91. 解码方法
	public int numDecodings_1(String s) {
		if (s == null || s.length() == 0) return 0;
		int n = s.length();
		int[] dp = new int[n + 1];
		
		dp[n] = 1;
		if (s.charAt(n-1) != '0')
			dp[n-1] = 1;
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

		 if (cnt[start] != -1) return cnt[start];
		
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
		 
		 cnt[start] = res;
		 return res;
	}


	//递归
	public int numDecodings(String s) {
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
	public int numDecodings2(String s) {
		if (s == null || s.length() == 0) return 0;

		int[] mem = new int[s.length() + 1];
		Arrays.fill(mem, -1);
		return numDecodings_helper2(s, 0, mem);
	}

	private int numDecodings_helper2(String s, int start, int[] mem) {
		if (start == s.length()) return 1;
		int ans = 0;
		if (s.charAt(start) == '0') {
			return 0;
		}
		if (mem[start] != -1)
			return mem[start]; //计算过

		ans += numDecodings_helper2(s, start + 1, mem);
		if (start < s.length() - 1) {
			int a = (s.charAt(start) - '0') * 10;
			int b = s.charAt(start + 1) - '0';

			if (a + b <= 26) {
				ans += numDecodings_helper2(s, start + 2, mem);
			}
		}
		mem[start] = ans;
		return ans;
	}

	//动规
	public int numDecodings3(String s) {
		if (s == null || s.length() == 0) return 0;

		int len = s.length();
		int[] dp = new int[len + 1];
		dp[len] = 1;
		if (s.charAt(len - 1) != '0')
			dp[len - 1] = 1;

		for (int i = len - 2; i >= 0; i--) {
			if (s.charAt(i) == '0')
				continue;
			
			dp[i] += dp[i + 1];
			int a = (s.charAt(i) - '0') * 10;
			int b = s.charAt(i + 1) - '0';
			if (a + b <= 26)
				dp[i] += dp[i + 2];
		}
		return dp[0];
	}
	//动规空间优化 TODO

	//258. 各位相加
	public int addDigits(int num) {
		if (num < 10) return num;
		int ans = 0;
		while (num >= 10) {
			while (num > 0) {
				ans += num % 10;
				num /= num;
			}
			num = ans;
			ans = 0;
		}
		return num;
    }
	// Test
	public static void main(String args[]) {
		 OneDayOneAlgorithm test = new OneDayOneAlgorithm();
		 
		 String pattern = "abba"; String str = "dog cat cat dog";
		 System.out.println(test.wordPattern(pattern, str));
	}
}
