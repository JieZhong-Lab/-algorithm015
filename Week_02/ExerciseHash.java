import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExerciseHash {
    //242. 有效的字母异位词
    public boolean isAnagram2(String s, String t) {
        if (s == null || t == null || s.length() != t.length()) return false;

        int[] arr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            arr[s.charAt(i)-'a']++;
            arr[t.charAt(i)-'a']--;
        }
        for (int n : arr) {
            if (n != 0) 
                return false;
        }
        return true;
    }
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) return false;

        int[] dic = new int[26];
        for (int i = 0; i < s.length(); i++) {
            dic[s.charAt(i) - 'a']++;
            dic[t.charAt(i) - 'a']--;
        }
        for (int n : dic) {
            if (n != 0) 
                return false;
        }
        return true;
    }
    //49. 字母异位词分组
    public List<List<String>> groupAnagrams2(String[] strs) {
        if (strs == null || strs.length == 0) 
            return Collections.emptyList();

        Map<String, List<String>> map = new HashMap<>();
        int[] arr = new int[26];
        for (String s : strs) {
            Arrays.fill(arr, 0);
            for (char c : s.toCharArray()) {
                arr[c - 'a']++;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= 26; i++) {
                sb.append(arr[i]).append('#');
            }
            String pattern = sb.toString();
            if (!map.containsKey(pattern)) 
                map.put(pattern, new ArrayList<String>());
            map.get(pattern).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }


    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0)
            return Collections.emptyList();
        Map<String, List<String>> map = new HashMap<> ();
        int count[] = new int[26];

        for (String s : strs) {
            Arrays.fill(count, 0);
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#').append(count[i]);
            }
            String key = sb.toString();
            if (!map.containsKey(key)){
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(s);
        }

        return new ArrayList<List<String>>(map.values());
    }

    public List<List<String>> groupAnagrams3(String[] strs) {
        if (strs.length == 0) return Collections.emptyList();

        Map<String, List<String>> map = new HashMap<>();
        for (String s : strs) {
            char[] chs = s.toCharArray();
            Arrays.sort(chs);
            String key = String.valueOf(chs);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(s);
        }
        return new ArrayList<List<String>>(map.values());
    }

    //350. 两个数组的交集 II
    public int[] intersect2(int[] nums1, int[] nums2) {
		if (nums1.length > nums2.length) 
			return intersect2(nums2, nums1);
		Map<Integer, Integer> map = new HashMap<>();
		for (int n : nums1) {
			map.put(n, map.getOrDefault(n, 0) + 1);
		}

		int[] ans = new int[nums1.length];
		int index = 0;
		for (int n : nums2) {
			if (map.containsKey(n)) {
				ans[index++] = n;
				if (map.get(n) > 1) 
					map.put(n, map.get(n) - 1);
				else
					map.remove(n);
			} 
		}

		return Arrays.copyOfRange(ans, 0, index);
    }
    
    public int[] twoSum(int[] nums, int target) {
        // a -> if (target - a) exist in nums
        //maintain [a, i] in a map

        Map<Integer, Integer> map = new HashMap<> ();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length; i++) {
            int b = target - nums[i];
            if (map.containsKey(b) && map.get(b) != i) {
                return new int[] {i, map.get(b)};
            }
        }
        throw new IllegalArgumentException("No nums equals to target!");
    }

    public int[] twoSum_2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] {map.get(complement), i};
            } 
            map.put(nums[i], i);
        }

        throw new IllegalArgumentException("No nums equals to target!");
    }

    //15. 三数之和
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null || nums.length == 0) 
            return Collections.EMPTY_LIST;
        
        Set<List<Integer>> ans = new HashSet<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) 
            for (int j = i + 1; j < nums.length - 1; j++)
                for (int k = j + 1; k < nums.length; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0)
                        ans.add(Arrays.asList(nums[i], nums[j], nums[k]));
                }
        return new ArrayList<>(ans);
    }

    public List<List<Integer>> threeSum_2(int[] nums) {
        if (nums == null || nums.length == 0)
            return Collections.EMPTY_LIST;
        
        Map<Integer, Integer> map = new HashMap<> ();
        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }
        for (int i = 0; i < nums.length - 2; i++) {
            for (int j = i + 1; j < nums.length - 1; j++) {
                int complement = 0 - (nums[i] + nums[j]);
                if (map.containsKey(complement) && map.get(complement) != i && map.get(complement) != j) {
                    Integer[] tmp = new Integer[] {nums[i], nums[j], complement};
                    Arrays.sort(tmp);
                    set.add(Arrays.asList(tmp));
                } 
            }
        }
        return new ArrayList<>(set);
    }

    public List<List<Integer>> threeSum_3(int[] nums) {
        if (nums == null || nums.length == 0) return Collections.emptyList();
        List<List<Integer>> ans = new ArrayList<> ();
        Arrays.sort(nums);
        int i = 0, j = 0;
        for (int k = 0; k < nums.length - 2; k++) {
            if (nums[k] > 0) break;
            if (k > 0 && nums[k] == nums[k - 1])
                continue;

            i = k + 1; j = nums.length - 1;
            while (i < j) {
                int sum = nums[i] + nums[k] + nums[j];
                if (sum < 0) {
                    while (i < j && nums[i] == nums[++i]);
                } else if (sum > 0) {
                    while (i < j && nums[j] == nums[--j]);
                } else {
                    ans.add(Arrays.asList(nums[k], nums[i], nums[j]));
                    while (i < j && nums[i] == nums[++i]);
                    while (i < j && nums[j] == nums[--j]);
                }
            }
        }
        return ans;
    }

    public static void main(String args[]) {
        ExerciseHash test = new ExerciseHash();
        List<List<Integer>> ans = test.threeSum_3(new int[] {-1, 0, 1, 2, -1, -4});
        for (List<Integer> a : ans)
            System.out.println(a);
    }
}
