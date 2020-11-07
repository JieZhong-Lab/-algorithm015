import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class ExerciseW9 {
    //300. 最长上升子序列
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int maxLen = 1;
        int[] dp = new int[n]; //dp[i] 表示以i为终点的最长上升子序列的长度
        Arrays.fill(dp, 1);
        for (int i = 1; i < n; i++) {
            //求以第i个数为终点的最长上升子序列的长度
            for (int j = 0; j < i; j++) {
                //查看以第j个数为终点的最长上升子序列
                if (nums[i] > nums[j]) { //加上num[i], 可以组成上升子序列
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }
    //1024. 视频拼接
    //T - 持续时长为T秒的视频
    public int videoStitching(int[][] clips, int T) {
        //注意：0 <= T <= 100
        int[] dp = new int[T + 1]; //dp[i]表示覆盖[0，i)所需要的最少子区间数量，即覆盖i秒视频所需要的最少片段
        Arrays.fill(dp, 101);
        dp[0] = 0; 
        for (int i = 1; i <= T; i++) {
            for (int[] clip : clips) {
                if (i > clip[0] && i <= clip[1]) {
                    dp[i] = Math.min(dp[i], dp[clip[0]] + 1);
                }
            }
        }
        return dp[T] == 101 ? -1 : dp[T];
    }

    //129. 求根到叶子节点数字之和
    //DFS
    public int sumNumbers(TreeNode root) {
        return dfs(root, 0);
    }

    private int dfs(TreeNode root, int prevSum) {
        if (root == null) return 0;
        int sum = prevSum * 10 + root.val;
        if (root.left == null && root.right == null) return sum;
        return dfs(root.left, sum) + dfs(root.right, sum);
    }

    //BFS
    public int sumNumbers_2(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        Queue<Integer> numQueue = new LinkedList<>();
        int sum = 0;
        nodeQueue.offer(root);
        numQueue.offer(root.val);
        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int num = numQueue.poll();
            TreeNode left = node.left, right = node.right;
            if (left == null && right == null) {
                sum += num;
            } else {
                if (left != null) {
                    nodeQueue.offer(left);
                    numQueue.offer(num * 10 + left.val);
                }
                if (right != null) {
                    nodeQueue.offer(right);
                    numQueue.offer(num * 10 + right.val);
                }
            }
        }
        return sum;
    }

    //349. 两个数组的交集
    public int[] intersection(int[] nums1, int[] nums2) {
        Set<Integer> set = new HashSet<>();
        Set<Integer> ans = new HashSet<>();
        for (int num : nums1) {
            set.add(num);
        }
        for (int num : nums2) {
            if (set.contains(num)) {
                ans.add(num);
            } 
        }
        int[] res = new int[ans.size()];
        int i = 0;
        for (int n : ans) {
            res[i++] = n;
        }
        return res;
    }
    //双指针
    public int[] intersection2(int[] nums1, int[] nums2) {
        Set<Integer> ans = new HashSet<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                ans.add(nums1[i]);
                i++; j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            }
        }
        int[] res = new int[ans.size()];
        int idx = 0;
        for (int n : ans) {
            res[idx++] = n;
        }
        return res;
    }
    //二分查找
    public int[] intersection3(int[] nums1, int[] nums2) {
        Set<Integer> ans = new HashSet<>();
        Arrays.sort(nums2);
        for (int num : nums1) {
            if (binarySearch(nums2, num) && !ans.contains(num)) {
                ans.add(num);
            }
        }
        int[] res = new int[ans.size()];
        int idx = 0;
        for (int n : ans) {
            res[idx++] = n;
        }
        return res;
    }

    private boolean binarySearch(int nums[], int target) {
        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l)/2;
            if (nums[mid] == target) {
                return true;
            } else if (nums[mid] > target) {
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        ExerciseW9 es = new ExerciseW9();
        int[][] clips = new int[][]{
            {0, 2}, {4, 6}, {8,10}, {1,9},{1,5},{5,9}
        };
        es.videoStitching(clips, 10);
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        this.val = x;
    }
}