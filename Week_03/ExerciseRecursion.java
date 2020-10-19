import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class ExerciseRecursion {
    //105. 从前序与中序遍历序列构造二叉树
    Map<Integer, Integer> inorderMap = null;
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) return null;
        
        inorderMap = new HashMap<>();
        int n = preorder.length;
        for (int i = 0; i < n; i++) {
            inorderMap.put(inorder[i], i);
        }
        return buildTree_recur(preorder, inorder, 0, n - 1, 0, n - 1);
    }
    private TreeNode buildTree_recur(int[] preorder, int[] inorder, int preorder_left, int preorder_right, int inorder_left, int inorder_right) {
        //terminator
        if (preorder_left > preorder_right) return null;
        //process current logic
        //在中序遍历中定位根结点
        int inorder_rootInd= inorderMap.get(preorder[preorder_left]);
        TreeNode root = new TreeNode(preorder[preorder_left]);
        //得到左子树中的结点数目
        int size_left_subtree = inorder_rootInd - inorder_left;
        //drill down
        root.left = buildTree_recur(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree,
                                    inorder_left, inorder_rootInd - 1);
        root.right = buildTree_recur(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right,
                                    inorder_rootInd + 1, inorder_right);
        return root;
    }

    //98. 验证二叉搜索树
    long prev = Long.MIN_VALUE;
    public boolean isValidBST_2(final TreeNode root) {
        if (root == null)
            return true;

        if (!isValidBST_2(root.left)) {
            return false;
        }
        if (root.val <= prev) {
            return false;
        }
        return isValidBST_2(root.right);
    }

    public boolean isValidBST_3(final TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        long preVal = Long.MIN_VALUE;

        TreeNode curr = root;
        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (curr.val <= preVal) return false;
            preVal = curr.val;
            curr = curr.right;
        }
        return true;
    }

    public boolean isValidBST(final TreeNode root) {
        return helper(root, null, null);
    }

    private boolean helper(final TreeNode root, final Integer minValue, final Integer maxValue) {
        // terminator
        if (root == null)
            return true;
        // process current logic
        if (minValue != null && root.val <= minValue)
            return false;
        if (maxValue != null && root.val >= maxValue)
            return false;
        // drill down
        if (!helper(root.left, minValue, root.val))
            return false;
        if (!helper(root.right, root.val, maxValue))
            return false;

        return true;
    }

    // 236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(final TreeNode root, final TreeNode p, final TreeNode q) {
        if (root == null || root == p || root == q)
            return root;
        final TreeNode leftLCA = lowestCommonAncestor(root.left, p, q);
        final TreeNode rightLCA = lowestCommonAncestor(root.right, p, q);

        if (leftLCA == null && rightLCA == null)
            return null;
        else {
            if (leftLCA == null)
                return rightLCA;
            if (rightLCA == null)
                return leftLCA;
        }
        return root; // leftLCA != null && rightLCA != null
    }

    public TreeNode ans = null;

    public TreeNode lowestCommonAncestor_2(final TreeNode root, final TreeNode p, final TreeNode q) {
        dfs(root, p, q);
        return ans;
    }

    // root结点的子树中是否包含p结点或q结点
    private boolean dfs(final TreeNode root, final TreeNode p, final TreeNode q) {
        // terminator
        if (root == null)
            return false;
        // drill down
        final boolean lson = dfs(root.left, p, q);
        final boolean rson = dfs(root.right, p, q);
        // process current logic
        // Found lowestCommonAncestor
        if ((lson && rson) || ((root.val == p.val || root.val == q.val) && (lson || rson))) {
            ans = root;
        }
        return lson || rson || (root.val == p.val) || (root.val == q.val);
    }

    // 用HashMap保存每个结点的父节点
    Map<Integer, TreeNode> parentMap = new HashMap<>();
    Set<Integer> visited = new HashSet<>();

    public TreeNode lowestCommonAncestor_3(final TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;
        travser(root);

        while (p != null) {
            visited.add(p.val);
            p = parentMap.get(p.val);
        }
        while (q != null) {
            if (visited.contains(q.val)) {
                return q;
            }
            q = parentMap.get(q.val);
        }
        return null;
    }

    private void travser(final TreeNode root) {
        if (root.left != null) {
            parentMap.put(root.left.val, root);
            travser(root.left);
        }
        if (root.right != null) {
            parentMap.put(root.right.val, root);
            travser(root.right);
        }
    }

    // 111. 二叉树的最小深度
    public int minDepth(final TreeNode root) {
        if (root == null)
            return 0;

        if (root.left == null && root.right == null)
            return 1;

        int min_depth = Integer.MAX_VALUE;
        if (root.left != null) {
            min_depth = Math.min(min_depth, minDepth(root.left));
        }
        if (root.right != null) {
            min_depth = Math.min(min_depth, minDepth(root.right));
        }
        return min_depth + 1;
    }

    // 104. 二叉树的最大深度
    public int maxDepth(final TreeNode root) {
        if (root == null)
            return 0;

        final int l_depth = maxDepth(root.left);
        final int r_depth = maxDepth(root.right);

        return Math.max(l_depth, r_depth) + 1;
    }

    // 226. 翻转二叉树
    public TreeNode invertTree(final TreeNode root) {
        if (root == null)
            return root;

        TreeNode right = invertTree(root.left);
        TreeNode left = invertTree(root.right);
        root.right = right;
        root.left = left;
        return root;
    }

    // 22. 括号生成
    public List<String> generateParenthesis(final int n) {
        final List<String> ans = new ArrayList<String>();

        helper(0, 0, n, "", ans);
        return ans;
    }

    // left - the number of left parenthesis
    // right - the number of right parenthesis
    private void helper(final int left, final int right, final int n, final String s, final List<String> ans) {
        if (left == n && right == n) {
            ans.add(s);
            return;
        }

        if (left < n) {
            helper(left + 1, right, n, s + "(", ans);
        }
        if (right < left) {
            helper(left, right + 1, n, s + ")", ans);
        }
    }

    // 77. 组合
    // Solution 1:
    public List<List<Integer>> combine_2(final int n, final int k) {
        final List<List<Integer>> ans = new ArrayList<>();
        final List<Integer> tmp = new ArrayList<>();

        combine_2_recur(n, k, 1, tmp, ans);
        return ans;
    }

    private void combine_2_recur(final int n, final int k, final int curr, final List<Integer> output,
            final List<List<Integer>> ans) {
        // 剪枝：temp 长度加上区间 [cur, n] 的长度小于 k，不可能构造出长度为 k 的 temp
        if (output.size() + (n - curr + 1) < k)
            return;

        // 递归终止条件
        if (output.size() == k) {
            ans.add(new ArrayList<Integer>(output)); // 记录合法答案
            return;
        }

        // 考虑选择当前位置
        output.add(curr);
        combine_2_recur(n, k, curr + 1, output, ans);
        output.remove(output.size() - 1);

        // 不考选择虑当前位置
        combine_2_recur(n, k, curr + 1, output, ans);
    }

    // solution 2:
    public List<List<Integer>> combine(final int n, final int k) {
        final List<List<Integer>> ans = new ArrayList<>();
        if (k <= 0 || n < k)
            return ans;

        final Deque<Integer> path = new LinkedList<>();
        combine_recur(n, k, 1, path, ans);
        return ans;
    }

    private void combine_recur(final int n, final int k, final int begin, final Deque<Integer> path,
            final List<List<Integer>> ans) {
        if (path.size() == k) {
            ans.add(new ArrayList<>(path));
            return;
        }

        // for (int i = begin; i <= n; i++) {
        /*
         * i - 要选择的元素位置 剪枝：分析搜索起点上界，进行剪枝 例如：n = 6 ，k = 4。 path.size() == 1
         * 的时候，接下来要选择3个数，搜索起点最大是 4，最后一个被选的组合是 [4, 5, 6]； path.size() == 2
         * 的时候，接下来要选择2个数，搜索起点最大是 5，最后一个被选的组合是 [5, 6]； path.size() == 3
         * 的时候，接下来要选择1个数，搜索起点最大是 6，最后一个被选的组合是 [6]； 搜索起点的上界 + 接下来要选择的元素的个数 - 1 = n
         * 接下来要选择的元素的个数 = k - path.size() 由此得到，搜索起点的上界 = n - (k - path.size()) + 1
         */
        for (int i = begin; i <= n - (k - path.size()) + 1; i++) {
            path.addLast(i);
            System.out.println("***" + i + " 递归之前 => " + path);
            combine_recur(n, k, i + 1, path, ans);
            path.removeLast();
            System.out.println("***" + i + " 递归之后 => " + path);
        }
    }

    // 46. 全排列
    public List<List<Integer>> permute_2(final int[] nums) {
        final List<List<Integer>> ans = new LinkedList<>();

        final int n = nums.length;
        if (n == 0)
            return ans;

        final Deque<Integer> output = new LinkedList<>();
        final boolean[] used = new boolean[n];
        permute_2_recur(nums, n, output, ans, 0, used);
        return ans;
    }

    private void permute_2_recur(final int[] nums, final int n, final Deque<Integer> output,
            final List<List<Integer>> ans, final int depth, final boolean[] used) {
        System.out.println("***Start depth=" + depth);
        // terminator
        if (output.size() == n) {
            ans.add(new ArrayList<>(output));
            return;
        }

        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                // process current logic
                used[i] = true;
                output.addLast(nums[i]);
                // drill down
                permute_2_recur(nums, n, output, ans, depth + 1, used);
                // restore current status
                used[i] = false;
                output.removeLast();
            }
        }
        System.out.println("***End depth=" + depth);
    }

    public List<List<Integer>> permute(final int[] nums) {
        final List<List<Integer>> ans = new LinkedList<>();

        final int n = nums.length;
        if (n == 0)
            return ans;

        final Deque<Integer> output = new LinkedList<>();
        final boolean[] used = new boolean[n];
        permute_recur(nums, n, output, ans, 0, used);
        return ans;
    }

    /*
     * index - 当前要确定的全排列中下标为index的那个数， 或者可以叫做depth（递归到第几层）
     */
    private void permute_recur(final int[] nums, final int n, final Deque<Integer> output,
            final List<List<Integer>> ans, final int index, final boolean[] used) {
        System.out.println("***Start depth " + index);
        // terminator
        // 递归终止条件 - 一个排列中的数字已经选够了
        if (index == n) {
            // System.out.println(" 找到一种排列 => " + output);
            ans.add(new ArrayList<>(output));
            return;
        }

        for (int i = 0; i < n; i++) {
            System.out.println("processing... " + i);
            if (!used[i]) {
                // process current logic
                used[i] = true;
                output.addLast(nums[i]);
                // drill down
                System.out.println("  递归之前 => " + output + " - use " + i);
                permute_recur(nums, n, output, ans, index + 1, used);
                // restore current status
                used[i] = false;
                output.removeLast();
                System.out.println("  递归之后 => " + output + " - return " + i);
            }
        }
        System.out.println("***End depth " + index);
    }

    //47. 全排列 II
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> ans = new ArrayList<> ();
        if (nums == null || nums.length == 0) return ans;
        Arrays.sort(nums); //排序, 剪枝的前提
        
        int n = nums.length;
        boolean used[] = new boolean[n];
        Deque<Integer> output = new LinkedList<>();
        
        permuteUnique_recur(nums, n, used, output, ans);
        return ans;
    }

    private void permuteUnique_recur(int[] nums, int n, boolean[] used, Deque<Integer> output, List<List<Integer>> ans) {
        //terminator
        if (output.size() == n) {
            ans.add(new ArrayList<>(output));
            return;
        }
        for (int i = 0; i < n; i++) {
            //剪枝：这次搜索的起点和上次搜索的起点一样，但上次搜索的相同的数刚刚被撤销，这种情况一定会产生重复
            if (i > 0 && nums[i-1] == nums[i] && !used[i-1]) continue;
            if (used[i]) continue;

            output.addLast(nums[i]);
            used[i] = true;
            //drill down
            permuteUnique_recur(nums, n, used, output, ans);
            output.removeLast();
            used[i] = false;
        }
    }

    public static void main(final String args[]) {
        final ExerciseRecursion ex = new ExerciseRecursion();
        // List<List<Integer>> ans = ex.combine_2(4, 2);
        // ans.forEach((path) -> System.out.println(path));
        final int[] nums = new int[] { 1, 2, 3 };
        final List<List<Integer>> ans = ex.permute_2(nums);
        ans.forEach((output) -> System.out.println(output));
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(final int x) {
        this.val = x;
    }
}