import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OneDayOneAlgorithm {
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

	// Test
	public static void main(String args[]) {
		 OneDayOneAlgorithm test = new OneDayOneAlgorithm();
		 
		 String pattern = "abba"; String str = "dog cat cat dog";
		 System.out.println(test.wordPattern(pattern, str));
	}
}
