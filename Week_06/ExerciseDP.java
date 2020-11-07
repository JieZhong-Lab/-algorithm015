import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.HashSet;

public class ExerciseDP {
    //64. 最小路径和
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        //dp[i][j]表示从左上角出发到点i，j的最小距离
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];

        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[m-1][n-1];
    }

    //322. 零钱兑换
    //暴力递归
    int ans = Integer.MAX_VALUE;
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        coinChange_recur(coins, amount, 0);
        //如果没有任何一种组合能组成金额，返回-1
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private void coinChange_recur(int[] coins, int amount, int count) {
        if (amount < 0) return;
        if (amount == 0) ans = Math.min(ans, count);

        for (int i = 0; i < coins.length; i++) {
            coinChange_recur(coins, amount - coins[i], count + 1);
        }
    }

    //记忆化搜索 - 自顶向下
    public int coinChange_2(int[] coins, int amount) {
        if (coins == null || coins.length == 0) return -1;
        int[] mem = new int[amount]; //mem[n] 表示钱币n可以被换取的最少的硬币数量，不能换取则返回-1

        return coinChange_2_recur(coins, amount, mem);
    }

    public int coinChange_2_recur(int[] coins, int remain, int[] mem) {
        if (remain < 0) return -1;
        if (remain == 0) return 0;

        if (mem[remain - 1] != 0) return mem[remain - 1];

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < coins.length; i++) {
            //drill down
            //兑换remain-coins[i]所需最少硬币个数
            int nums = coinChange_2_recur(coins, remain-coins[i], mem);
            if (nums != -1 && nums < min) //如果可以兑换，并且所需硬币数小于当前得到的min
                min = nums + 1;
        }
        mem[remain - 1] = (min == Integer.MAX_VALUE ? -1 : min);
        return mem[remain - 1];
    }

    //动规 - 自底向上
    //sub problem
    //DP array: f(n) = min(f(n-k), for k in [1,2,5]) + 1
    //DP方程
    public int coinChange_3(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1]; //dp[i] 表示兑换金额i所需要的最少硬币数量
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == max ? -1 : dp[amount];
    }

    //BFS
    public int coinChange_4(int[] coins, int amount) {
        if (coins == null || coins.length == 0) return -1;
        if (amount == 0) return 0;

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(amount);
        int steps = 0;
        Set<Integer> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            int size = queue.size();
            steps++;
            while (size-- > 0) {
                int tmp = queue.poll();
                for (int i = 0; i < coins.length; i++) {
                    int remain = tmp - coins[i];
                    if (remain == 0) return steps;
                    if (remain > 0 && !visited.contains(remain)) {
                        queue.offer(remain);
                        visited.add(remain);
                    }
                }
            }
        }
        return -1;
    }

    //198. 打家劫舍
    //二维dp数组
    public int rob(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int n = nums.length;
        int[][] dp = new int[n][2]; //dp[i][0]表示不偷第i个房子；dp[i][1]表示偷第i个房子
        
        dp[0][0] = 0;
        dp[0][1] = nums[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1]);
            dp[i][1] = dp[i-1][0] + nums[i];
        }
        return Math.max(dp[n-1][0], dp[n-1][1]);
    }

    //一维dp数组
    public int rob_2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        int n = nums.length;

        int[] dp = new int[n]; //dp[i]表示抢[0 - i]个房子所能获取第最大值
        //dp[i] = max(dp[i-1], dp[i-2] + nums[i])
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n; i++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i]);
        }
        return dp[n-1];
    }

    //213. 打家劫舍 II
    public int rob2(int[] nums) {
        if (nums == null|| nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);

        int n = nums.length;
        int[] dp1 = new int[n]; //不偷最后一个房子
        int[] dp2 = new int[n]; //不偷第一个房子

        //不偷最后一个房子
        dp1[0] = nums[0];
        dp1[1] = Math.max(nums[0], nums[1]);
        for (int i = 2; i < n - 1; i++) 
            dp1[i] = Math.max(dp1[i-1], dp1[i-2] + nums[i]);

        //不偷第一个房子
        dp2[0] = 0;
        dp2[1] = nums[1];
        for (int i = 2; i < n; i++) 
            dp2[i] = Math.max(dp2[i-1], dp2[i-2] + nums[i]);
        
        return Math.max(dp1[n-2], dp2[n-1]);
    }

    //152. 乘积最大子数组
    public int maxProduct(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        int[][] dp = new int[n][2]; //dp[i][0]表示以nums[i]结尾的连续子数组最小；dp[i][1]表示以nums[i]结尾的连续子数组最大
        dp[0][0] = nums[0];
        dp[0][1] = nums[0];

        for (int i = 1; i < n; i++) {
            if (nums[i] >= 0) {
                dp[i][0] = Math.min(nums[i], nums[i] * dp[i-1][0]);
                dp[i][1] = Math.max(nums[i], nums[i] * dp[i-1][1]);
            } else {
                dp[i][0] = Math.min(nums[i], nums[i] * dp[i-1][1]);
                dp[i][1] = Math.max(nums[i], nums[i] * dp[i-1][0]);
            }
        }

        int ans = dp[0][1];
        for (int i = 1; i < n; i++)
            ans = Math.max(ans, dp[i][1]);
        
        return ans;
    }

    //53. 最大子序和
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n]; //以i结尾的最大子序和
        int ans = nums[0];
        dp[0] = nums[0];
        for (int i = 1; i < n; i++) {
            dp[i] = nums[i] + (dp[i-1] > 0 ? dp[i-1] : 0); 
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    //120. 三角形最小路径和
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] dp = new int[n + 1][n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i + 1][j], dp[i + 1][j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0][0];
    }

    public int minimumTotal_2(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[j] = Math.min(dp[j], dp[j + 1]) + triangle.get(i).get(j);
            }
        }
        return dp[0];
    }

    public static void main(String[] args) {
        ExerciseDP edp = new ExerciseDP();
        int[] coins = new int[] {1, 2, 5}; 
        System.out.println(edp.coinChange_4(coins, 11));
    }
}
