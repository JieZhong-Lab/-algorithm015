public class MinWinowSubstring {
    //76. 最小覆盖子串
    //滑动窗口
    public String minWindow(String s, String t) {
        if (s == null || s.length() == 0 || t == null || t.length() == 0 || s.length() < t.length()) return "";

        int[] need = new int[128];
        //记录需要的字符以及个数
        for (char ch : t.toCharArray()) {
            need[ch]++;
        }
        int l = 0, r = 0, start = 0;
        int len = s.length();
        int ans = Integer.MAX_VALUE;
        int needCnt = t.length(); //初始化需要字符的个数

        while (r < len) {
            char ch = s.charAt(r); 
            if (need[ch] > 0) needCnt--; //是需要的字符

            need[ch]--; //加入窗口

            if (needCnt == 0) {//窗口中已经包含所有字符
                while (l < r && need[s.charAt(l)] < 0) { //小于零说明l指向的字符是多余的，左指针可以继续右移
                    need[s.charAt(l)]++; //释放移出窗口的字符
                    l++;
                }
                //记录最小窗口并记录开始位置
                if (r - l + 1 < ans) {
                    ans = r - l + 1;
                    start = l;
                }
                //释放掉一个所需字符，准备开始下一轮
                need[s.charAt(l)]++;
                l++;
                needCnt++;
            }
            r++;
        }
        return ans == Integer.MAX_VALUE ? "" : s.substring(start, start + ans);
    }

    public static void main(String args[]) {
        MinWinowSubstring minWinowSubstring = new MinWinowSubstring();
        System.out.println(minWinowSubstring.minWindow("ADOBECODEBANC", "ABC"));
    }
}
