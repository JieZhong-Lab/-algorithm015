package jie.leetcode.camp.stackqueue;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ExerciseStack {
	//20. 有效的括号
	//https://leetcode-cn.com/problems/valid-parentheses/
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
	
	//https://leetcode-cn.com/problems/largest-rectangle-inhistogram
	
	
	//42. 接雨水
	//https://leetcode-cn.com/problems/trapping-rain-water/
	public int trap(int[] height) {
		return 0; //TODO ..
    }
}
