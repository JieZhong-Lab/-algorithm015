import java.util.Deque;
import java.util.LinkedList;

public class ExerciseQueue {
	//https://leetcode-cn.com/problems/sliding-window-maximum
	//239. 滑动窗口最大值
	public int[] maxSlidingWindow3(int[] nums, int k) {
		if (nums == null || nums.length < 2) return nums;
		int n = nums.length;
		int ans[] = new int[n - k + 1];
		Deque<Integer> deque = new LinkedList<>();
		for (int i = 0; i < n; i++) {
			while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
				deque.pollLast();
			}
			deque.addLast(i);

			if (i >= k - 1) {
				if (deque.peekFirst() <= i - k) {
					deque.pollFirst();
				}
				ans[i - k + 1] = nums[deque.peekFirst()];
			}
		}
		return ans;
	}
	public int[] maxSlidingWindow2(int[] nums, int k) {
		if (nums == null || nums.length < 2) return nums;

		int n = nums.length;
		int[] ans = new int[n - k + 1];
		Deque<Integer> deque = new LinkedList<>();
		for (int i = 0; i < n; i++) {
				while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) 
					deque.pollLast();
				
				deque.addLast(i);

				if (i >= k - 1) {
					if (deque.peekFirst() <= i-k) 
						deque.pollFirst();
					
					ans[i - k +1] = nums[deque.peekFirst()];
				}
		}
		return ans;
	}

	public int[] maxSlidingWindow(int[] nums, int k) {
		int n = nums.length;
		
		if (n * k == 0) return new int[0];

		int[] ans = new int[n - k + 1];
		for (int i = 0; i < n - k + 1; i++) {
			int max = Integer.MIN_VALUE;
			for (int j = i; j < i + k; j++) {
				max = Math.max(max, nums[j]);
			}
			ans[i] = max;
		}
		return ans;
	}

	//TODO .. use heap
}
