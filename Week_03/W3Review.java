import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class W3Review {
    //637. 二叉树的层平均值
    //DFS
    public List<Double> averageOfLevels_2(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        List<Integer> count = new LinkedList<>();
        List<Double> sums = new LinkedList<>();
        dfs(root, 0, count, sums);
        for (int i = 0 ; i < count.size(); i++) {
            ans.add(sums.get(i) / count.get(i));
        }
        return ans;
    }
    private void dfs(TreeNode root, int level, List<Integer> count, List<Double> sums) {
        if (root == null) return;

        if (level >= sums.size()) {//当前层还没计算
            sums.add(1.0*root.val);
            count.add(1);
        } else {                    //当前层已经算过，继续累加
            sums.set(level, sums.get(level) + root.val);
            count.set(level, count.get(level) + 1);
        }
        dfs(root.left, level + 1, count, sums);
        dfs(root.right, level + 1, count, sums);
    }
    //BFS
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ans = new ArrayList<>();
        if (root == null) return ans;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int count = size;
            double sum = 0;
            while (size > 0) {
                TreeNode node = queue.poll();
                sum += node.val;
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                size--;
            }
            ans.add(sum / count);
        }
        return ans;
    }

    //1021. 删除最外层的括号
    public String removeOuterParentheses(String S) {
        Stack<Character> stack = new Stack<>();
        StringBuilder sb = new StringBuilder();
        int start = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                stack.push(S.charAt(i));
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    sb.append(S.substring(start + 1, i));
                }
            }
        }
        return sb.toString();
    }
    //350. 两个数组的交集 II
	public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }

        Map<Integer, Integer> map = new HashMap<> ();
        for (int n : nums1) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }
        int ans[] = new int[nums1.length];
        int index = 0;
        for (int n : nums2) {
            if (map.containsKey(n)) {
                ans[index++] = n;
                if (map.get(n) > 1) {
                    map.put(n, map.get(n) - 1);
                } else {
                    map.remove(n);
                }
            }
        }
        return Arrays.copyOfRange(ans, 0, index);
    }

    public int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        List<Integer> ans = new ArrayList<> ();
        int i = 0, j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                ans.add(nums1[i]);
                i++;
                j++;
            }
        }
        int[] a = new int[ans.size()];
        int index = 0;
		for (int n : ans)
			a[index++] = n;
		return a;
    }

    //面试题 17.09. 第 k 个数
    public int  getKthMagicNumber_2(int k) {
        int[] dp = new int[k];
        dp[0] = 1;

        int a = 0, b = 0, c = 0;
        for (int i = 1; i < k; i++) {
            int n3 = dp[a] * 3, n5 = dp[b] * 5, n7 = dp[c] * 7;
            dp[i] = Math.min(Math.min(n3, n5), n7);
            if (n3 == dp[i]) a++;
            if (n5 == dp[i]) b++;
            if (n7 == dp[i]) c++;
        }
        return dp[k-1];
    }
    public int getKthMagicNumber(int k) {
        int[] primeArr = new int[] {3, 5, 7};
        PriorityQueue<Long> heap = new PriorityQueue<>();
        heap.offer(1L);
        int count = 1;

        while (!heap.isEmpty()) {
            long  num =  heap.poll();
            if (count++ >= k) 
                return (int)num;

            for (int prime : primeArr) {
                long canidate = num * prime;
                if (!heap.contains(canidate)) {
                    heap.offer(canidate);
                }
            }
        }
        return -1;
    }
}
