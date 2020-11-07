import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class TaskScheduler {
    //621. 任务调度器
    //桶
    public int leastInterval(char[] tasks, int n) {
        if (tasks.length <= 1 || n < 1) return tasks.length;
        int[] counts = new int[26];
        for (char t : tasks)
            counts[t - 'A']++;
        Arrays.sort(counts);
        int max = counts[25];
        int maxCnt = 0;
        for (int cnt : counts) {
            if (cnt == max) maxCnt++;
        }

        return Math.max((n + 1) * (max - 1) + maxCnt, tasks.length);
    }

    //堆（优先队列）
    public int leastInterval_2(char[] tasks, int n) {
        if (tasks.length <= 1 || n < 1) return tasks.length;
        int[] counts = new int[26];
        for (char task : tasks) 
            counts[task - 'A']++;
        
        PriorityQueue<Integer> queue = new PriorityQueue<>(26, Collections.reverseOrder());
        for (int cnt : counts) {
            if (cnt > 0) queue.add(cnt);
        }
        int time = 0;
        while (!queue.isEmpty()) {
            int turn = n + 1; //一轮子任务
            List<Integer> tmp = new ArrayList<Integer>();
            while (turn-- > 0) { //从队列中取出n + 1个任务
                if (!queue.isEmpty()) {
                    if (queue.peek() > 1)
                        tmp.add(queue.poll() - 1);
                    else 
                        queue.poll();
                }
                time++;

                if (queue.isEmpty() && tmp.size() == 0) break;
            }
            //执行完一轮子任务，再放回堆中
            for (int cnt : tmp) {
                queue.add(cnt);
            }
        }
        return time;
    }

    public static void main(String args[]) {
        TaskScheduler ts = new TaskScheduler();
        char[] tasks = new char[] {'A','A','A','B','B','B'};
        System.out.println(ts.leastInterval_2(tasks, 2));
    }
}
