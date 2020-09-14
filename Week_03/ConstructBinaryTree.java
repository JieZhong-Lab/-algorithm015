import java.util.HashMap;
import java.util.Map;

class ConstructBinaryTree {
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
        root.left = buildTree_recur(preorder, inorder, preorder_left + 1, preorder_left + size_left_subtree, inorder_left, inorder_rootInd - 1);
        root.right = buildTree_recur(preorder, inorder, preorder_left + size_left_subtree + 1, preorder_right, inorder_rootInd + 1, inorder_right);
        return root;
    }

    //106. 从中序与后序遍历序列构造二叉树
    public TreeNode buildTreeII(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length) return null;

        inorderMap = new HashMap<>();
        int n = inorder.length;
        for (int i = 0; i < n; i++) 
            inorderMap.put(inorder[i], i);
        
        return buildTreeII_recur(inorder, postorder, 0, n - 1, 0, n - 1);
    }

    private TreeNode buildTreeII_recur(int[] inorder, int[] postorder, int inorder_left, int inorder_right, int postorder_left, int postorder_right) {
        if (inorder_left > inorder_right) return null;

        int inorder_rootInx = inorderMap.get(postorder[postorder_right]);
        int size_left_subtree = inorder_rootInx - inorder_left - 1;

        TreeNode root = new TreeNode(postorder[postorder_right]);
        root.left = buildTreeII_recur(inorder, postorder, inorder_left, inorder_rootInx - 1, postorder_left, postorder_left + size_left_subtree);
        root.right = buildTreeII_recur(inorder, postorder, inorder_rootInx + 1, inorder_right, postorder_left + size_left_subtree + 1, postorder_right - 1);
        return root;
    }
}