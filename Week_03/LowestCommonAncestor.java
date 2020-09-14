import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LowestCommonAncestor {
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
}
