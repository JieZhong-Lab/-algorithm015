import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class ExerciseHeap {
    //剑指 Offer 40. 最小的k个数
    //sort
    public int[] getLeastNumbers(int[] arr, int k) {
        Arrays.sort(arr);
        
        int ans[] = new int[k];
        for (int i = 0; i < k; i++) {
            ans[i] = arr[i];
        }
        return ans;
    }
    //heap
    public int[] getLeastNumbers2(int[] arr, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        int ans[] = new int[k];
        for (int n : arr) {
            heap.offer(n);
        }
        for (int i = 0; i < k; i++) {
            ans[i] = heap.poll();
        }
        return ans;
    }

    public int[] getLeastNumber3(int[] arr, int k) {
        if (k == 0) return new int[0];
        PriorityQueue<Integer> heap = new PriorityQueue<>((o1, o2) -> (o2 - o1));
        int ans[] = new int[k];
        for (int i = 0; i < arr.length; i++) {
            if (i < k) {
                heap.offer(arr[i]);
            } else {
                if (heap.peek() > arr[i]) {
                    heap.poll();
                    heap.offer(arr[i]);
                }
            }
        }

        for (int i = 0; i < k; i++) {
            ans[i] = heap.poll();
        }
        return ans;
    }
    //快排
    //347. 前 K 个高频元素
    //heap - 大顶堆
    public int[] topKFrequent2(int[] nums, int k) {
        int[] ans = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((o1, o2) -> (o2.getValue() - o1.getValue()));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            maxHeap.offer(entry);
        }

        for (int i = 0; i < k; i++) {
            ans[i] = maxHeap.poll().getKey();
        }
        return ans;
    }
    //heap - 小顶堆
    public int[] topKFrequent(int[] nums, int k) {
        int[] ans = new int[k];
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            map.put(n, map.getOrDefault(n, 0)+1);
        }

        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((o1, o2) -> (o1.getValue() - o2.getValue()));
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (heap.size() >= k) {
                if (heap.peek().getValue() < entry.getValue()) {
                    heap.poll();
                    heap.offer(entry);
                }
            } else {
                heap.offer(entry);
            }
        }
        
        for (int i = 0; i < k; i++) 
            ans[i] = heap.poll().getKey();
        return ans;
    }
    //剑指 Offer 49. 丑数
    //动态规划
    public int nthUglyNumber_2(int n) {
        int[] dp = new int[n];
        dp[0] = 1;
        int a = 0, b = 0, c = 0;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a] * 2, n3 = dp[b] * 3, n5 = dp[c] * 5;
            dp[i] = Math.min(Math.min(n2, n3), n5);
            if (dp[i] == n2) a++;
            if (dp[i] == n3) b++;
            if (dp[i] == n5) c++;
        }
        return dp[n-1];
    }
    //heap- 小顶堆
    //分析：任何丑数乘以2，3，5，其结果也是丑数
    public int nthUglyNumber(int n) {
        int[] primeFactorArr = new int[] {2, 3, 5};
        PriorityQueue<Long> minHeap = new PriorityQueue<>();//小顶堆，取堆顶，下面的元素有可能超过int的范围
        minHeap.offer(1L);

        int count = 0; //记录出堆的元素，按照从小到大排序
        while (!minHeap.isEmpty()) {
            long num = minHeap.poll();
            if (++count >= n) {
                return (int)num;
            }
            for (int prime : primeFactorArr) {
                if (!minHeap.contains(num * prime)) {
                    minHeap.offer(num * prime);
                }
            }
        }
        return -1;
    }

    //239. 滑动窗口最大值
    //heap -大顶堆
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0 || k == 0) return new int[]{0};
        int len = nums.length;
        int[] ans = new int[len - k + 1];
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> (o2 - o1));

        for (int i = 0; i < len; i++) {
            int start = i - k;
            if (start >= 0) {
                maxHeap.remove(nums[start]);
            }
            maxHeap.offer(nums[i]);
            if (maxHeap.size() == k) {
                ans[i - k + 1] = maxHeap.peek();
            }
        }
        return ans;
    }
}
