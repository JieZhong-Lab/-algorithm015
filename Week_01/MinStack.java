package jie.leetcode.camp.stackqueue;

import java.util.Stack;

public class MinStack {
	Stack<Integer> stack = new Stack<>();
	Stack<Integer> min_stack = new Stack<>();
	/** initialize your data structure here. */
    public MinStack() {
    	min_stack.push(Integer.MAX_VALUE);
    }
    
    public void push(int x) {
    	stack.push(x);
    	min_stack.push(Math.min(x, min_stack.peek()));
    }
    
    public void pop() {
    	stack.pop();
    	min_stack.pop();
    }
    
    public int top() {
    	return stack.peek();
    }
    
    public int getMin() {
    	return min_stack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
