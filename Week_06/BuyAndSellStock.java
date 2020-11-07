import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;

public class BuyAndSellStock {
    //121. 买卖股票的最佳时机
    //暴力
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) maxProfit = profit;
            }
        }
        return maxProfit;
    }
    //一次遍历
    public int maxProfit_2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
            
        }
        return maxProfit;
    }

    //DP
    public int maxProfit_3(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int minPrice = prices[0];
        int n = prices.length;
        int[] dp = new int[n];
        
        for (int i = 1; i < n; i++) {
            minPrice = Math.min(prices[i], minPrice);
            dp[i] = Math.max(dp[i - 1], prices[i] - minPrice);
        }
        return dp[n-1];
    }

    //单调栈 - 利用哨兵
    /** 
     * 1. 从栈底到栈顶单调递增 
     * 2. 栈空或将要入栈的元素大于栈顶元素，则直接入栈
     * 3. 将要入栈的元素小于栈顶元素，则循环弹栈，直到入栈元素大于栈顶元素或者栈空
     * 4. 每次弹出时， 拿它与买入的值（即栈底元素）做差，维护一个最大值
    */
    public int maxProfit_4(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        Deque<Integer> stack = new LinkedList<>();
        int n = prices.length;
        int[] prices2 = Arrays.copyOf(prices, n + 1);
        prices2[n] = -1;
        int maxProfit = 0;
        for (int i = 0; i <= n; i++) {
            while (!stack.isEmpty() && stack.peek() >= prices2[i]) {
                int top = stack.pop();
                if (stack.isEmpty()) break;
                maxProfit = Math.max(maxProfit, top - stack.peekLast());
            }
            stack.push(prices2[i]);
        }
        return maxProfit;
    }

    //122. 买卖股票的最佳时机 II
    //峰谷法
    public int maxProfitII_3(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int maxProfit = 0;
        int h = prices[0], l = prices[0];

        int i = 0;
        int len = prices.length;
        while (i < len - 1) {
            while (i < len - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            l = prices[i];
            while (i < len - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            h = prices[i];
            maxProfit += h - l;
        }
        return maxProfit;
    }
    //Greedy
    public int maxProfitII(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                maxProfit += prices[i] - prices[i-1];
            }
        }
        return maxProfit;
    }
    //DP
    public int maxProfitII_2(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][] dp = new int[n][2]; //dp[i][0]持有现金，dp[i][1]持有股票
        dp[0][0] = 0;
        dp[0][1] = -prices[0];

        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i - 1][0] - prices[i], dp[i - 1][1]);
        }
        return dp[n-1][0];
    }

    //309. 最佳买卖股票时机含冷冻期 - 卖出股票后，你无法在第二天买入股票 (即冷冻期为 1 天)
    public int maxProfitWithCoolDown(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;

        int[][] dp = new int[n][3]; //dp[i][0] - 不持股; dp[i][1] - 持股; dp[i][2] - 冷冻期
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        dp[0][2] = 0;
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][2] - prices[i]);
            dp[i][2] = dp[i-1][0];
        }
        return Math.max(dp[n-1][0], dp[n-1][2]);
    }

    //714. 买卖股票的最佳时机含手续费
    //DP
    public int maxProfitWithTxnFee(int[] prices, int fee) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;

        int[][] dp = new int[n][2]; //dp[i][0] - 不持股; dp[i][1] - 持股;
        dp[0][0] = 0;
        dp[0][1] = -prices[0];
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.max(dp[i-1][0], dp[i-1][1] + prices[i]);
            dp[i][1] = Math.max(dp[i-1][1], dp[i-1][0] - prices[i] - fee);
        }
        return dp[n-1][0];
    }

    //Greedy
    public int maxProfitWithTxnFee_2(int[] prices, int fee) {
        if (prices == null || prices.length < 2) return 0;
        int n = prices.length;

        int valley = prices[0];
        int maxProfit = 0;

        for (int i = 1; i < n; i++) {
            if (prices[i] < valley) {
                valley = prices[i];
            } else if (prices[i] > valley + fee)  {
                maxProfit += prices[i] - valley - fee;
                valley = prices[i] - fee;
            }
        }
        return maxProfit;
    }
    //123. 买卖股票的最佳时机 III
    public int maxProfitIII(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        int[][][] dp = new int[n][3][2];
        //dp[i][k][0] 第i天手上没有持有股票，至今最多可进行k次交易 
        //dp[i][k][1] 第i天手上持有股票，至今最多可进行k次交易 
        //k表示包含当天的最多可交易次数，k表示一种限制，而不是一种结果
        
        for (int i = 0; i < n; i++) {
            for (int k = 2; k >= 1; k--) {
                if (i == 0) {//i-1 == -1
                    dp[i][k][0] = 0;
                    dp[i][k][1] = -prices[i];
                    continue;
                }
                //dp[i][k][s] = max(buy, sell, rest)
                //i - [0 , n)
                //k - [1, K]
                //s - [0, 1]
                dp[i][k][0] = Math.max(dp[i-1][k][0], dp[i-1][k][1] + prices[i]);
                dp[i][k][1] = Math.max(dp[i-1][k][1], dp[i-1][k-1][0] - prices[i]);
            }
            
        }
        return dp[n-1][2][0];
    }

    //188. 买卖股票的最佳时机 IV
    public int maxProfitIV(int k, int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int n = prices.length;
        //k应该不超过n/2，因为买卖至少需要两天，超过n/2就等于没有限制，为避免dp数组太大，可以先处理k > n/2 的情况
        if (k > n/2) { 
            int maxProfit = 0;
            for (int i = 1; i < n; i++) {
                if (prices[i] > prices[i-1]) 
                    maxProfit += prices[i] - prices[i-1];
            }
            return maxProfit;
        }
        int[][][] dp = new int[n][k+1][2];
        for (int i = 0; i < n; i++) {
            for (int j = k; j >= 1; j--) {
                if (i == 0) {
                    dp[i][j][0] = 0;
                    dp[i][j][1] = -prices[i];
                    continue;
                }
                dp[i][j][0] = Math.max(dp[i-1][j][0], dp[i-1][j][1] + prices[i]);
                dp[i][j][1] = Math.max(dp[i-1][j][1], dp[i-1][j-1][0] - prices[i]);
            }
        }
        return dp[n-1][k][0];
    }

    public static void main(String args[]) {
        BuyAndSellStock bs = new BuyAndSellStock();
        int[] prices = new int[] {7, 1, 5, 3, 6, 4};
        System.out.println(bs.maxProfit_4(prices));
    }
}
