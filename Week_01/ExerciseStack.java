import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExerciseStack {
	//20. 有效的括号
	//https://leetcode-cn.com/problems/valid-parentheses/
	public boolean isValid3(String s) {
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < s.length(); i++) {
			if ('(' == s.charAt(i)) {
				stack.push(')');
			} else if ('[' == s.charAt(i)) {
				stack.push(']');
			} else if ('{' == s.charAt(i)) {
				stack.push('}');
			} else if (stack.isEmpty() || stack.pop() != s.charAt(i)) {
				return false;
			}
		}
		return stack.isEmpty();
	}
	public boolean isValid2(String s) {
        Map<Character, Character> dic = new HashMap<> () {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };
		Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (dic.containsKey(s.charAt(i))) {
				if (stack.isEmpty() || dic.get(s.charAt(i)) != stack.peek()) {
					return false;
				}
				stack.pop();
			} else {
				stack.push(s.charAt(i));
			}
		}
		return stack.isEmpty();
	}
	
	public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>() {
        	{
        		put(')', '(');
        		put(']', '[');
        		put('}', '{');
        	}
        };
        
        for (char c : s.toCharArray()) {
        	if (map.containsKey(c)) {
        		if (stack.isEmpty() || stack.peek() != map.get(c))
        			return false;
        		else 
        			stack.pop();
        	} else {
        		stack.push(c);
        	}
        }
        return stack.isEmpty(); 
	}
	
	
	
	//84. 柱状图中最大的矩形
	//https://leetcode-cn.com/problems/largest-rectangle-inhistogram
	public int largestRectangleArea(int[] heights) {
		return Integer.MIN_VALUE;
    }
	
	//42. 接雨水
	//https://leetcode-cn.com/problems/trapping-rain-water/
	public int trap(int[] height) {
		return 0; //TODO ..
    }
}
