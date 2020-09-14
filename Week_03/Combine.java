import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Combine {
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
}
