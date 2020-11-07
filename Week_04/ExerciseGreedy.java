import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ExerciseGreedy {
    //860. 柠檬水找零
    public boolean lemonadeChange(int[] bills) {
        int five = 0, ten = 0;
        for(int bill : bills) {
            if (bill == 5) five++;
            else if (bill == 10) {
                if (five == 0) return false;
                five--;
                ten++;
            } else {
                if (five > 0 && ten > 0) {
                    five--; ten--;
                } else if (five >= 3) {
                    five -= 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    //455. 分发饼干
    public int findContentChildren(int[] g, int[] s) {
        int ans = 0;
        Arrays.sort(g);
        Arrays.sort(s);

        int kids = g.length; int cookies = s.length;

        int i = 0, j = 0;
        while (i < kids && j < cookies) {
            if (g[i] <= s[j]) {
                ans++;
                i++;
                j++;
            } else {
                j++;
            }
        }
        return ans;
    }

    //122. 买卖股票的最佳时机 II
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) return 0;
        int maxProfit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1]) {
                maxProfit += prices[i] - prices[i-1];
            }
        }
        return maxProfit;
    }
    //874. 模拟行走机器人
    public int robotSim(int[] commands, int[][] obstacles) {
        if (commands == null || commands.length == 0) return 0;
        int[] dirx = {0, 1, 0, -1};
        int[] diry = {1, 0, -1, 0};
        int x = 0, y = 0;
        int dirIdx = 0;
        int ans = 0;
        Set<String> set = new HashSet<>();
        for (int[] obstacle : obstacles) {
            set.add(obstacle[0]+"-"+obstacle[1]);
        }
        for (int i = 0; i < commands.length; i++){
            if (commands[i] == -1) { //向右转
                dirIdx = (dirIdx + 1) % 4;
            } else if (commands[i] == -2) { //向左转
                dirIdx = (dirIdx + 3) % 4;
            } else { //  1 <= x <= 9：向前移动 x 个单位长度
                for(int j = 0; j < commands[i]; j++){ 
                    //试图走出一步
                    int tx = x + dirx[dirIdx];
                    int ty = y + diry[dirIdx];
                    //判断将要走出的这一步是否是障碍点
                    if (!set.contains(tx+"-"+ty)) {
                        x = tx;
                        y = ty;
                        ans = Math.max(ans, x*x + y*y);
                    } else {
                        break;
                    }
                }
            }
        }
        return ans;
    }
}
