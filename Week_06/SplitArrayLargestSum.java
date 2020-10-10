import java.util.Arrays;

public class SplitArrayLargestSum {
    //410. 分割数组的最大值
    //DP
    public int splitArray(int[] nums, int m) {
        if (nums == null || nums.length == 0 || m > nums.length) return 0;
        int n = nums.length;
        int[][] dp = new int[n+1][m+1]; //dp[i][j]表示将数组的前i个数划分为j组所能得到的最大连续子数组的最小值
        for(int i = 0; i <= n; i++) //i < j 是不合法的划分，可以赋值一个很大的数
            Arrays.fill(dp[i], Integer.MAX_VALUE);

        dp[0][0] = 0; //初始状态

        //前缀和
        int[] presum = new int[n+1];
        for (int i = 0; i < n; i++) {
            presum[i + 1] = presum[i] + nums[i];
        }
        for (int i = 1; i <= n; i++) { //前i个数分成一组，即前缀和
            dp[i][1] = presum[i];
            for (int j = 2; j <= Math.min(i, m); j++) {
                for (int k = j-1; k < i; k++) {
                    //范围 [0,i-1]内找一个位置k ,使[0, k-1] 至少可以分成 j-1 组，即前k个元素要能被分为 j-1 组，[k, i-1] 至少剩余1个元素
                    dp[i][j] = Math.min(dp[i][j], Math.max(dp[k][j-1], presum[i] - presum[k]));
                }
            }
        }
        return dp[n][m];
    }

    //二分法+贪心
    //“使……最大值尽可能小” 是二分搜索题目常见的问法。
    /**
     * 子数组最大值的范围，在区间[max(nums), sum(nums)]
     */
    public int splitArray_2(int[] nums, int m) {
        if (nums == null || nums.length == 0 || m > nums.length) return 0;
        int left = 0, right = 0;
        for (int n : nums) {
            left = Math.max(left, n);
            right += n;
        }
        while (left < right) {
            int mid = left + (right - left)/2;
            int cnt = getSubArrays(nums, mid);

            if (cnt <= m) { //分割数太小，说明“子数组各自的和的最大值”太大，需要将“子数组各自的和的最大值”调小；分割数正好，仍然可以探索更小的“子数组各自和的最大值”，下一轮搜索区间就是[left, mid]
                right = mid;
            } else { //cnt > m 分割数太多，说明“子数组各自的和的最大值”太小，需要将“子数组各自的和的最大值”调大，下一轮搜索的区间是[mid + 1, right]
                left = mid + 1;
            }
        }
        return left;
    }
    private int getSubArrays(int nums[], int x) {
        int sum = 0;
        int cnt = 1;
        for (int i = 0; i < nums.length; i++) {
            if (sum + nums[i] > x) {
                cnt++;
                sum = nums[i];
            } else {
                sum += nums[i];
            }
        }
        return cnt;
    }
}
