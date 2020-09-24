public class JumpGame {
    //55. 跳跃游戏
    //Greedy
    public boolean canJump(int[] nums) {
        if (nums == null || nums.length == 0) return false;
        int n = nums.length;
        int reach = n - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] + i >= reach) {
                reach = i;
            }
        }
        return reach == 0;
    }

    //45. 跳跃游戏 II
    //反向查找
    public int jump_2(int[] nums) {
        int position = nums.length - 1;
        int steps = 0;
        while (position > 0) {
            for (int i = 0; i < position; i++) {
                if (i + nums[i] >= position) {
                    position = i;
                    steps++;
                    break;
                }
            }
        }
        return steps;
    }

    //动规. 超出时间限制
    public int jump(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        int n = nums.length;
        //dp[i]表示从第i个格子出发所需要的最小的步数
        int[] dp = new int[n];
        dp[n-1] = 0;
        
        for (int i = n - 2; i >= 0; i--) {
            dp[i] = n;
            for (int j = nums[i]; j >= 1; j--) {
                if (i + j >= n -1) {
                    dp[i] = 1;
                    break;
                } else {
                    dp[i] = Math.min(dp[i], dp[i+j] + 1);  
                }
            }
        }

        return dp[0];
    }

    public static void main(String args[]) {
        JumpGame j = new JumpGame();
        System.out.println(j.jump(new int[] {2,3,0,1,4}));
    }
}
