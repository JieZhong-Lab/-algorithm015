import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class ExerciseTree {
    //145. 二叉树的后序遍历
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        helper_postorder(root, output);

        return output;
    }

    private void helper_postorder(TreeNode root, List<Integer> output) {
        if (root == null) return;

        helper_postorder(root.left, output);
        helper_postorder(root.right, output);

        output.add(root.val);
    }

    public List<Integer> postorderTraversal_2(TreeNode root) {
        LinkedList<Integer> output = new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();

        if (root == null) {
            return output;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.addFirst(node.val);

            if (node.left != null)
                stack.push(node.left);
            if (node.right != null)
                stack.push(node.right);
        }
        return output;
    }
    //94. 二叉树的中序遍历
    //Iteration
    public List<Integer> inorderTraversal_2(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        TreeNode curr = root;
        while (curr != null && !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            output.add(curr.val);
            curr = curr.right;
        }
        return output;
    }
    //Recursion
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        helper_inorder(root, output);

        return output;
    }

    private void helper_inorder(TreeNode root, List<Integer> output) {
        if (root == null) 
            return;
        
        helper_inorder(root.left, output);
        output.add(root.val);
        helper_inorder(root.right, output);
    }


    //144. 二叉树的前序遍历
    //迭代
    public List<Integer> preorderTraversal_2(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        if (root == null) {
            return output;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            } 
        }
        return output;
    }

    //递归
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> output = new ArrayList<>();
        helper_preorder(root, output);
        
        return output;
    }

    private void helper_preorder(TreeNode node, List<Integer> output) {
        if (node == null) return;
        output.add(node.val);
        helper_preorder(node.left, output);
        helper_preorder(node.right, output);
    }

    //589. N叉树的前序遍历
    public List<Integer> preorder(Node root) {
        List<Integer> output = new ArrayList<>();
        helper_pre(root, output);

        return output;
    }

    private void helper_pre(Node root, List<Integer> output) {
        if (root == null) return;

        output.add(root.val);
        for (Node node : root.children) {
            helper_pre(node, output);
        }
    }

    //recursion
    public List<Integer> preorder_2(Node root) {
        List<Integer> output = new ArrayList<>();
        Stack<Node> stack = new Stack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            output.add(node.val);

            Collections.reverse(node.children);
            for (Node child : node.children) {
                stack.push(child);
            }
        }
        return output;
    }

    //590. N叉树的后序遍历
    public List<Integer> postorder(Node root) {
        List<Integer> output = new ArrayList<> ();

        helper_post(root, output);

        return output;
    }

    private void helper_post(Node root, List<Integer> output) {
        if (root == null) return;

        for (Node node : root.children) {
            helper_post(node, output);
        }
        output.add(root.val);
    }

    //recursion
    public List<Integer> postorder_2(Node root) {
        LinkedList<Integer> output = new LinkedList<>();
        Stack<Node> stack = new Stack<>();

        if (root == null) {
            return output;
        }

        stack.push(root);
        while (!stack.isEmpty()) {
            Node curr = stack.pop();
            output.addFirst(curr.val);
            for (Node node : curr.children) {
                if (node != null) {
                    stack.push(node);
                }
            }
        }
        return output;
    }

    //429. N叉树的层序遍历
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> output = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();

        if (root == null) return output;
        queue.offer(root);

        List<Integer> list;
        while (!queue.isEmpty()) {
            int size = queue.size();
            list = new ArrayList<>();
            while (size > 0) {
                Node node = queue.poll();
                list.add(node.val);
                size--;
            }
            output.add(list);
            for (Node item : node.children) {
                queue.offer(item);
            }
        }
        return output;        
    }

    //108. 将有序数组转换为二叉搜索树
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;
        return helper(nums, 0, nums.length - 1);
    }
    private TreeNode helper(int[] nums, int left, int right) {
        if (left > right) 
            return null;

        int mid = (left + right) / 2;
        TreeNode node = new TreeNode(nums[mid]);
        node.left = helper(nums, left, mid - 1);
        node.right = helper(nums, mid + 1, right);

        return node;
    }

    //107. 二叉树的层次遍历 II
    //BFS
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return new ArrayList<>();
        Stack<List<Integer>> stack = new Stack<>();
        Queue<TreeNode> q = new LinkedList<>();
        List<List<Integer>> ans = new ArrayList<>();

        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            List<Integer> level = new ArrayList<>();
            while (size > 0) {
                TreeNode node = q.poll();
                level.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                } 
                if (node.right != null) {
                    q.offer(node.right);
                }
                size--;
            }
            stack.push(level);
        }

        while(!stack.isEmpty()) ans.add(stack.pop());
        return ans;
    }

    public List<List<Integer>> levelOrderBottom_1(TreeNode root) {
        List<List<Integer>> ans = new ArrayList<>();
        DFS_2(root, 0, ans);
        return ans;
    }
    //DFS - 自顶向下
    private void DFS_1(TreeNode root, int level, List<List<Integer>> ans) {
        if (root == null) return;
        //当前层数还没有元素，先 new 一个空的列表
        if (ans.size() <= level) {
            ans.add(new ArrayList<>());
        }
        //将当前值加入
        ans.get(level).add(root.val);
        DFS_1(root.left, level + 1, ans);
        DFS_1(root.right, level + 1, ans);
    }
    //DFS - 自底向上
    private void DFS_2(TreeNode root, int level, List<List<Integer>> ans) {
        if (root == null) return;

        if (ans.size() <= level) {
            ans.add(0, new ArrayList<>());
        }
        ans.get(ans.size() - 1 - level).add(root.val);//将当前值加入到正在处理的层
        DFS_2(root.left, level + 1, ans);
        DFS_2(root.right, level + 1, ans);
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

class Node {
    public int val;
    public List<Node> children;

    public Node() {}

    public Node(int val) {
        this.val = val;
    } 

    public Node(int val, List<Node> children) {
        this.val =val;
        this.children = children;
    }
}