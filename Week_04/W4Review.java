import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class W4Review {
    //39. 组合总和
    //Solution 1.
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return ans;

        Deque<Integer> output = new LinkedList<>();
        combinationSum_recur(candidates, target, 0, output, ans);
        return ans;
    }

    /*
     * target - 每次减去一个数，目标值变小
     * begin - 搜索起点
     */ 
    private void combinationSum_recur(int[] candidates, int target, int begin, Deque<Integer> output, List<List<Integer>> ans) {
        if (target < 0) return; //走不通，不再产生新的结点，终止递归
        if (target == 0) { //得到结果
            ans.add(new ArrayList<>(output));
            return;
        }

        for (int i = begin; i < candidates.length; i++) {
            output.addLast(candidates[i]);
            //因为元素可以重复使用，下一轮搜索的起点仍然是i
            combinationSum_recur(candidates, target-candidates[i], i, output, ans);
            //restore current status
            output.removeLast();
        }
    }

    //Solution 1. 剪枝优化
    public List<List<Integer>> combinationSum_2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return ans;

        Deque<Integer> output = new LinkedList<>();
        Arrays.sort(candidates);
        combinationSum_2_recur(candidates, target, 0, output, ans);
        return ans;
    }
    private void combinationSum_2_recur(int[] candidates, int target, int begin, Deque<Integer> output, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(output));
            return;
        }
        for (int i = begin; i < candidates.length; i++) {
            if (target - candidates[i] < 0) break;
            output.addLast(candidates[i]);
            combinationSum_2_recur(candidates, target - candidates[i], i, output, ans);
            output.removeLast();
        }
    }

    //Solution 2.
    public List<List<Integer>> combinationSum_3(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return ans;

        Deque<Integer> combine = new LinkedList<>();
        combinationSum_3_recur(candidates, target, 0, combine, ans);
        return ans;
    }
    /**
     * 
     * @param candidates
     * @param target -  还剩余target需要组合
     * @param idx   -   当前在candidates数组的第idx位
     * @param combine - 已经组合的列表
     * @param ans
     */
    private void combinationSum_3_recur(int[] candidates, int target, int idx, Deque<Integer> combine, List<List<Integer>> ans) {
        if (idx == candidates.length) return;
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }

        //选择当前的数
        if (target - candidates[idx] >= 0) {
            combine.addLast(candidates[idx]);
            combinationSum_3_recur(candidates, target - candidates[idx], idx, combine, ans);
            combine.removeLast();
        } 

        //跳过当前的数
        combinationSum_3_recur(candidates, target, idx + 1, combine, ans);
    }

    //40. 组合总和 II
    //Solution 1.
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return ans;

        Deque<Integer> combine = new LinkedList<>();
        Arrays.sort(candidates);
        combinationSum2_recur(candidates, target, 0, combine, ans);
        return ans;
    }
    /*
     * target - 每次减去一个数，目标值变小
     * begin - 搜索起点
     */ 
    private void combinationSum2_recur(int[] candidates, int target, int begin, Deque<Integer> combine, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }
        for (int i = begin; i < candidates.length; i++) {
            //剪枝
            if (target - candidates[i] < 0) break;

            //去重
            if (i > begin && candidates[i] == candidates[i-1]) continue;

            combine.addLast(candidates[i]);
            combinationSum2_recur(candidates, target - candidates[i], i + 1, combine, ans);
            combine.removeLast();
        }
    }

    //Solution 2.
    public List<List<Integer>> combinationSum2_2(int[] candidates, int target) {
        List<List<Integer>> ans = new ArrayList<>();
        if (candidates == null || candidates.length == 0) return ans;

        Deque<Integer> combine = new LinkedList<>();
        Arrays.sort(candidates);

        //构建一个相当于哈希表的集合，数字-数字出现的次数
        List<int[]> candidatesMap = new ArrayList<>();
        for (int n : candidates) {
            int size = candidatesMap.size();
            if (candidatesMap.isEmpty() || n != candidatesMap.get(size - 1)[0]) 
                candidatesMap.add(new int[]{n, 1});
            else
                candidatesMap.get(size - 1)[1]++;
        }
        combinationSum2_2_recur(candidatesMap, target, 0, combine, ans);
        return ans;
    }

    /**
     * idx   -   当前在哈希表的第idx位
     */
    private void combinationSum2_2_recur(List<int[]> candidatesMap, int target, int idx, Deque<Integer> combine, List<List<Integer>> ans) {
        if (target == 0) {
            ans.add(new ArrayList<>(combine));
            return;
        }
        
        if (idx == candidatesMap.size() || target < candidatesMap.get(idx)[0]) return;

        //选择当前的数字
        int cnt = Math.min(candidatesMap.get(idx)[1], target / candidatesMap.get(idx)[0]);
        for (int i = 1; i <= cnt; i++) {
            combine.addLast(candidatesMap.get(idx)[0]);
            combinationSum2_2_recur(candidatesMap, target - i * candidatesMap.get(idx)[0], idx + 1, combine, ans);
        }

        for (int i = 1; i <= cnt; i++) { 
            combine.removeLast();
        }
        //跳过当前的数字
        combinationSum2_2_recur(candidatesMap, target, idx + 1, combine, ans);
    }

    //47. 全排列 II
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null) return ans;

        int n = nums.length;
        Deque<Integer> output = new LinkedList<>();
        boolean[] used = new boolean[n];
        Arrays.sort(nums);
        permuteUnique_rec(nums, n, used, output, ans);
        return ans;
    }

    private void permuteUnique_rec(int[] nums, int n, boolean[] used, Deque<Integer> output, List<List<Integer>> ans) {
        if (output.size() == n) {
            ans.add(new ArrayList<>(output));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (used[i]) continue;
            if (i > 0 && nums[i-1] == nums[i] && !used[i-1]) continue;

            used[i] = true;
            output.addLast(nums[i]);
            permuteUnique_rec(nums, n, used, output, ans);
            output.removeLast();
            used[i] = false;
        }
    }

    //面试题 08.01. 三步问题
    public int waysToStep(int n) {
        int[] dp = new int[n+1];
        dp[0] = 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        if (n == 3) return 4;

        dp[1] = 1; 
        dp[2] = 2; 
        dp[3] = 4;
        for (int i = 4; i <= n; i++) {
            dp[i] = (dp[i-1] + (dp[i-2] + dp[i-3])%1000000007)%1000000007; 
        }
        return dp[n];
    }

    //617. 合并二叉树
    //DFS
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;

        TreeNode ans = new TreeNode(t1.val + t2.val);
        ans.left = mergeTrees(t1.left, t2.left);
        ans.right = mergeTrees(t1.right, t2.right);
        return ans;
    }

    public TreeNode mergeTree_2(TreeNode t1, TreeNode t2) {
        if (t1 == null) return t2;
        if (t2 == null) return t1;

        TreeNode ans = new TreeNode(t1.val + t2.val);
        Queue<TreeNode> queue = new LinkedList<>();
        Queue<TreeNode> q1 = new LinkedList<>();
        Queue<TreeNode> q2 = new LinkedList<>();

        queue.offer(ans);
        q1.offer(t1);
        q2.offer(t2);

        while(!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode node = queue.poll();
            TreeNode node1 = q1.poll();
            TreeNode node2 = q2.poll();
            TreeNode left1 = node1.left, left2 = node2.left;
            TreeNode right1 = node1.right, right2 = node2.right;
            if (left1 != null && left2 != null) {
                TreeNode left = new TreeNode(left1.val + left2.val);
                node.left = left;
                queue.offer(left);
                q1.offer(node1.left);
                q2.offer(node2.left);
            } else if (left1 != null) {
                node.left = left1;
            } else if (left2 != null) {
                node.left = left2;
            }

            if (right1 != null && right2 != null) {
                TreeNode right = new TreeNode(right1.val + right2.val);
                node.right = right;
                queue.offer(right);
                q1.offer(right1);
                q2.offer(right2);
            } else if (right1 != null) {
                node.right = right1;
            } else if (right2 != null) {
                node.right = right2;
            }
        }
        return ans;
    }

    //53. 最大子序和
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int ans = nums[0];
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            ans = Math.max(sum, ans);
            if (sum < 0)
                sum = 0;
        }
        return ans;
    }

    public int maxSubArray_2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int ans = nums[0];
        //arrSum[i]表示以第i个元素结尾的最大子序和
        int[] arrSum = new int[nums.length];
        arrSum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            //arrSum[i] = Math.max(arrSum[i-1] + nums[i], nums[i]);
            arrSum[i] = nums[i] + (arrSum[i-1] > 0 ? arrSum[i-1] : 0);
            ans = Math.max(arrSum[i], ans);
        }
        return ans;
    }

    //剑指 Offer 05. 替换空格
    public String replaceSpace_2(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ')
                sb.append("%20");
            else
                sb.append(s.charAt(i));
        }
        return sb.toString();
    }
    public String replaceSpace(String s) {
        if (s == null || s.length() == 0) return s;
        int len = s.length();
        char[] arr = new char[len * 3];
        int size = 0;
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                arr[size++] = '%';
                arr[size++] = '2';
                arr[size++] = '0';
            } else {
                arr[size++] = c;
            }
        }
        String newStr = new String(arr, 0, size);
        return newStr;
    }

    public static void main(String args[]) {
        W4Review re = new W4Review();
        int[] candidates = new int[] {2, 3, 6, 7};
        int target = 7;
        List<List<Integer>> ans = re.combinationSum(candidates, target);
        ans.forEach(output -> System.out.println(output));
    }
}
