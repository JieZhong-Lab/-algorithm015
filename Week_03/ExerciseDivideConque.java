import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class ExerciseDivideConque {
    //50. Pow(x, n)
    public double myPow(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        if (n == -1) return 1/x;
        double half = myPow(x, n/2);
        double rest = myPow(x, n%2);
        return half * half * rest;
    }

    public double myPow2(double x, int n) {
        return n >= 0 ? quickMul(x, n) : 1.0/quickMul(x, -n);
    }
    public double quickMul(double x, int N) {
        if (N == 0) return 1.0;

        double half = quickMul(x, N /  2);
        return N % 2 == 0 ? half * half : half * half * x;
    }

    //78. 子集
    //Recursion
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        if (nums == null || nums.length == 0) return ans;

        Deque<Integer> tmp = new LinkedList<>();

        subsets_recur(nums, 0, tmp, ans);
        return ans;
    }

    private void subsets_recur(int[] nums, int index, Deque<Integer> output, List<List<Integer>> ans) {
        //terminator
        if (index == nums.length) {
            ans.add(new ArrayList<>(output));
            return;
        }

        //not pick the current index
        subsets_recur(nums, index + 1, output, ans);

        //pick the current index
        output.addLast(nums[index]);
        subsets_recur(nums, index + 1, output, ans);

        //restore current state
        output.removeLast();      
    }

    //Iteration
    public List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        ans.add(new ArrayList<>());
        for (int num : nums) {
            List<List<Integer>> newSubsets = new ArrayList<>();
            for (List<Integer> curr : ans) {
                List<Integer> new_output = new ArrayList<>(curr);
                new_output.add(num);
                newSubsets.add(new_output);
            }
            newSubsets.forEach(tmp -> ans.add(tmp));
        }
        return ans;
    }

    //169. 多数元素
    /* 分治
     * 如果数a是数组nums的众数，如果我们将nums分成两部分，那么a必定是至少一部分的众数
     * */
    public int majorityElement3(int[] nums) {
        return majorityElement3_recur(nums, 0, nums.length - 1);
    }

    private int majorityElement3_recur(int[] nums, int left, int right) {
        if (left == right) return nums[left];

        int mid = left + (right - left)/2;
        int left_maj = majorityElement3_recur(nums, left, mid);
        int right_maj = majorityElement3_recur(nums, mid + 1, right);

        if (left_maj == right_maj) return left_maj;
        
        //else, count each element and return the winner
        int leftCnt = countInRange(nums, left_maj, left, right);
        int rightCnt = countInRange(nums, right_maj, left, right);

        return leftCnt > rightCnt ? left_maj : right_maj;
    }

    private int countInRange(int[] nums, int num, int l, int r) {
        int count = 0;
        for (int i = l; i <= r; i ++) {
            if (nums[i] == num) 
                count++;
        }
        return count;
    }

    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        return nums[nums.length/2];
    }
    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int n : nums) 
            map.put(n, map.getOrDefault(n, 0) + 1);
        
        int n = nums.length/2;
        for (int key : map.keySet()) {
            if (map.get(key) > n) {
                return key;
            }
        }
        return 0;
    }

    //17. 电话号码的字母组合
    Map<Character, String> dic = null;
    public List<String> letterCombinations(String digits) {
        dic = new HashMap<>();
        dic.put('2', "abc");
        dic.put('3', "def");
        dic.put('4', "ghi");
        dic.put('5', "jkl");
        dic.put('6', "mno");
        dic.put('7', "pqrs");
        dic.put('8', "tuv");
        dic.put('9', "wxyz");
        
        List<String> ans = new ArrayList<>();
        if (digits == null || digits.length() == 0)
            return ans;
        
        letterCombinations_recur(digits, 0, "", ans);
        return ans;
    }
    //index - digits的元素下标
    private void letterCombinations_recur(String digits, int index, String output, List<String> ans) {
        //terminator
        if (index == digits.length()) {
            ans.add(output);
            return;
        }
        //process current logic
        String letters = dic.get(digits.charAt(index));
        System.out.println(letters);
        for (char ch : letters.toCharArray()) {
            //drill down
            letterCombinations_recur(digits, index + 1, output + ch, ans);
        }
    }

    //BST
    public List<String> letterCombinations_2(String digits) {
        LinkedList<String> ans = new LinkedList<>();
        if (digits == null || digits.isEmpty()) return ans;

        String[] dic = new String[] {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        ans.add("");
        int curr_len = ans.peek().length();
        while (curr_len != digits.length()) {
            String curr = ans.poll();
            String chrs = dic[digits.charAt(curr_len) - '0'];
            for (char c : chrs.toCharArray()) {
                ans.add(curr + c);
            }
            curr_len = ans.peek().length();
        }
        return ans;
    }

    public static void main(String[] args) {
        ExerciseDivideConque ed = new ExerciseDivideConque();
        // System.out.println(ed.myPow2(2.00000, -2147483648));
        // int[] nums = new int[] {1,2,3};
        // List<List<Integer>> ans = ed.subsets2(nums);
        // ans.forEach((output) -> System.out.println(output));
        System.out.println(ed.letterCombinations_2("23"));
    }
}