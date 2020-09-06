import java.util.Arrays;
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
}
