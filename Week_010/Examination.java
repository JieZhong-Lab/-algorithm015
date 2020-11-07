import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Examination {
    //从前往后
    public int numDecodings(String s) {
        if (s.startsWith("0")) return 0;
        int n = s.length();
        int[] dp = new int[n + 1]; //dp[i]表示子串[0, i - 1]的解码方法总数
        dp[0] = 1;
        dp[1] = 1;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == '0') {
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2')
                    dp[i + 1] = dp[i - 1];
            } else {
                if (s.charAt(i - 1) == '1' || s.charAt(i - 1) == '2' && s.charAt(i) <= '6') {
                    dp[i + 1] = dp[i - 1] + dp[i]; //可以单个解码，也可以两位解码
                } else {
                    dp[i + 1] = dp[i]; //只能单个解码
                }
            }
        }
        return dp[n];
    }
    //从后往前
    public int numDecodings_2(String s) {
        int n = s.length();
        int[] dp = new int[n + 1]; //dp[i] 表示子串[i, n-1]的解码方法总数
        dp[n] = 1;

        if (s.charAt(n - 1) != '0') {
            dp[n - 1] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            if (s.charAt(i) == '0') continue; //当前子串不可解码
            dp[i] += dp[i + 1];
            
            int a = s.charAt(i) - '0';
            a = a * 10 + (s.charAt(i + 1) - '0');
            if (a <= 26)
                dp[i] += dp[i + 2];
        }
        return dp[0];
    }

    //单词接龙
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        int steps = 1;
        Set<String> dict = new HashSet<>(wordList);
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);

        int len = beginWord.length();
        Set<String> visited = new HashSet<>();
        while (!beginSet.isEmpty() && !endSet.isEmpty()) {
            if (beginSet.size() > endSet.size()) {
                Set<String> tmpSet = beginSet;
                beginSet = endSet;
                endSet = tmpSet;
            }

            Set<String> layer = new HashSet<>();
            for (String word: beginSet) {
                char[] currWord = word.toCharArray();
                for (int i = 0; i < len; i++) {
                    char originCh = currWord[i];
                    for (char c = 'a'; c <= 'z'; c++) {
                        currWord[i] = c;
                        String tmpWord = String.valueOf(currWord);
                        if (endSet.contains(tmpWord)) return steps + 1;

                        if (!visited.contains(tmpWord) && dict.contains(tmpWord)) {
                            layer.add(tmpWord);
                            visited.add(tmpWord);
                        }
                    }
                    currWord[i] = originCh;
                }
            }
            beginSet = layer;
            steps++;
        }
        return 0;
    }

    public static void main(String args[]) {
        Examination exam = new Examination();
    }
}
