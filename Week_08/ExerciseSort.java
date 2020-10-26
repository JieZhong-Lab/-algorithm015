import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class ExerciseSort {
    //1122. 数组的相对排序
    //计数排序
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int[] buckets = new int[1001];
        int[] res = new int[arr1.length];
        //将arr1的各个元素放入桶
        for (int n : arr1) {
            buckets[n]++; 
        }
        //按arr2的顺序收集桶中的数据
        int i = 0;
        for (int n : arr2) {
            while (buckets[n]-- > 0) {
                res[i++] = n;
            }
        }
        //按升序收集桶中剩余的元素
        for (int j = 0; j < 1001; j++) {
            while (buckets[j]-- > 0) { //直到这桶内的值耗尽
                res[i++] = j;
            }
        }
        return res;
    }

    //56. 合并区间
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return new int[0][2];
        int n = intervals.length;
        Arrays.sort(intervals, (a1, a2) -> a1[0] - a2[0]);

        Deque<int[]> merged = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int l = intervals[i][0], r = intervals[i][1];
            if (merged.size() == 0 || merged.getLast()[1] < l) {
                merged.add(new int[]{l, r});
            } else {
                merged.getLast()[1] = Math.max(merged.getLast()[1], r);
            }
        }
        return merged.toArray(new int[merged.size()][]);
    }

    //493. 翻转对
    //归并排序
    public int reversePairs(int[] nums) {
        if (nums == null || nums.length < 2) return 0;
        return mergeSortAndCount(nums, 0, nums.length - 1);
    }
    private int mergeSortAndCount(int[] nums, int s, int e) {
        if (s >= e) return 0;
        int mid = s + (e - s) / 2;
        int count = mergeSortAndCount(nums, s, mid) + mergeSortAndCount(nums, mid + 1, e);
        int j = mid + 1;
        for (int i = s; i <= mid; i++) {
            while (j <= e && nums[i]/2.0 > nums[j]) j++; // 除以2.0 用浮点数比较，或者转为long型 * 2
            count += j - (mid + 1); //[mid + 1, j) 都小于nums[i] * 2
        }
        merge(nums, s, mid, e);
        // Arrays.sort(nums, s, e + 1);
        return count;
    }

    private void merge(int[] nums, int s, int mid, int e) {
        int[] tmp = new int[e - s + 1];
        int p = 0, p1 = s, p2 = mid + 1;
        while (p1 <= mid && p2 <= e) {
            tmp[p++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
        }
        while (p1 <= mid) tmp[p++] = nums[p1++];
        while (p2 <= e) tmp[p++] = nums[p2++];

        System.arraycopy(tmp, 0, nums, s, e - s + 1);
    }

    public static void main(String args[]) {
        ExerciseSort es = new ExerciseSort();
        // int[][] intervals = new int[][] {
        //     {1, 3},
        //     {2, 6},
        //     {8,10},
        //     {15,18}
        //     // {1,4},
        //     // {2,3}
        // };
        // int[][] merged = es.merge(intervals);
        // for (int[] interval : merged) {
        //     System.out.println(interval[0] + "-" + interval[1]);
        // }
        int[] nums = new int[] {2,4,3,5,1};
        System.out.println(es.reversePairs(nums));
        // for (int n : nums) System.out.println(n);
    }
}
