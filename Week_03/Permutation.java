import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Permutation {
    // 46. 全排列
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
        Arrays.sort(nums); //剪枝的前提是排序
        
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
            //剪枝：这次搜索的起点和上次搜索的起点一样，但上次起点一样，但上次搜索但相同的数刚刚被撤销，这种情况一定会产生重复
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
}
