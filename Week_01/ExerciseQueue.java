public class ExerciseQueue {
	//https://leetcode-cn.com/problems/sliding-window-maximum
	//239. 滑动窗口最大值
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

	public int[] maxSlidingWindow2(int[] nums, int k) {
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

	//TODO .. use Deque
}
