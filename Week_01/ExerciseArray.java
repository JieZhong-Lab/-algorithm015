package jie.leetcode.camp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ExerciseArray {
	//66. 加一
	public int[] plusOne(int[] digits) {
		int len = digits.length;
		for (int i = len - 1; i >= 0; i--) {
			if (digits[i] < 9) {
				digits[i] += 1;
				return digits; //当前位小于9，则加1后直接返回
			} else {
				digits[i] = 0; //当前位等于9，则当前位置为0，进入下次循环
			}
		}

		//如果循环结束，而没有返回，说明每一位都是9，需要进位，原来的数组每一位的值全是0，再加一位1放在最前面
		int arr[] = new int[len + 1];
		arr[0] = 1;
		return arr;
	}
	
	//283. 移动零
	public void moveZeroes3(int[] nums) {
        for (int i = 0, j = 0; i < nums.length; i++) {
            if (nums[i] != 0 ) {
                int tmp = nums[j];
                nums[j] = nums[i];
                nums[i] = tmp;
                j++;
            }
        }
	}
	
	public void moveZeroes2(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[j] = nums[i];
                if (i != j) {
                nums[i] = 0;
                }
                j++;
            } 
        }
    }
	
	public void moveZeroes(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index++] = num;
            } 
        }
        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
	
	//1. 两数之和
	public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<> ();
		for (int i=0; i < nums.length; i++) {
			int n = target - nums[i];
			if (map.containsKey(n))
				return new int[] {map.get(n), i};
			
			map.put(nums[i], i);
		}
		throw new IllegalArgumentException("No nums equals to target");
    }
	
	public int[] twoSum(int[] nums, int target) {
		for (int i= 0; i < nums.length - 1; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
					return new int[] {i, j};
				}
			}
		}
		throw new IllegalArgumentException("No nums equals to target");
	}
	
	//15. 三数之和
	public List<List<Integer>> threeSum2(int[] nums) {
		if (nums == null || nums.length == 0) 
			return Collections.EMPTY_LIST;
		
		List<List<Integer>> ans = new ArrayList<>();
		
		int len = nums.length;
		Arrays.sort(nums);
		for (int k = 0; k < len - 2; k++) {
			if (nums[k] > 0)
				break;
			
			if (k > 0 && nums[k] == nums[k - 1])
				continue;
			int i = k + 1, j = len - 1;
			while (i < j) {
				int sum = nums[k] + nums[i] + nums[j];
				if (sum < 0) {
					while (i < j && nums[i] == nums[++i]) {
					}
				} else if (sum > 0) {
					while (i < j && nums[j] == nums[--j]);
				} else {
					List<Integer> list = Arrays.asList(nums[k], nums[i], nums[j]);
					ans.add(list);
					while (i < j && nums[i] == nums[++i]);
					while (i < j && nums[j] == nums[--j]);
				}
			}
		}
		return ans;
	}
	public List<List<Integer>> threeSum(int[] nums) {
		if (nums == null || nums.length == 0)
			return Collections.EMPTY_LIST;
		
		Set<List<Integer>> set = new HashSet<>();
		Arrays.sort(nums);
		int len = nums.length;
		for (int i = 0; i < len - 2; i++) {
			for (int j = i + 1; j < len - 1; j++) {
				for (int k = j + 1; k < len; k++) {
					if (nums[i] + nums[j] + nums[k] == 0) {
						List<Integer> candidate = Arrays.asList(nums[i], nums[j], nums[k]);
						set.add(candidate);
					}
				}
			}
		}
		return new ArrayList<>(set);
    }
	
	//88. 合并两个有序数组
	public void merge3(int[] nums1, int m, int[] nums2, int n) {
		int p1 = m - 1, p2 = n - 1, p = m + n -1;
		
		while (p1 >= 0 && p2 >= 0) {
			nums1[p--] = nums1[p1] < nums2[p2] ? nums2[p2--] : nums1[p1--];
		}
		
		while (p2 >= 0) {
			nums1[p--] = nums2[p2--];
		}
	}
	
	public void merge2(int[] nums1, int m, int[] nums2, int n) {
		int p1 = m - 1;
		int p2 = n - 1;
		int p = m + n -1;
		
		while (p1 >= 0 && p2 >= 0) {
			//compare two elements from nums1 and nums2
			//and add the largest one in nums1
			nums1[p--] = (nums1[p1] < nums2[p2] ? nums2[p2--] : nums1[p1--]);
		}
		System.arraycopy(nums2, 0, nums1, 0, p2 + 1);
	}
	
	public void merge(int[] nums1, int m, int[] nums2, int n) {
			int[] nums1_copy = new int[m];
			System.arraycopy(nums1, 0, nums1_copy, 0, m);
			
			int p = 0, p1 = 0, p2 = 0;
			
			while (p1 < m && p2 < n) {
				nums1[p++] = nums1_copy[p1] < nums2[p2] ? nums1_copy[p1++] : nums2[p2++];
			}
			
			if (p1 < m) {
				System.arraycopy(nums1_copy, p1, nums1, p1 + p2, m + n - p1 -p2);
			} 
			if (p2 < n) {
				System.arraycopy(nums2, p2, nums1, p1 + p2, m + n - p1 - p2);
			}
    }
	
	//189. 旋转数组
	public void rotate3(int[] nums, int k) {
		int len = nums.length;
		int[] tmp = new int[len];
		
		for (int i = 0; i < len; i++) {
			tmp[(i+k)%len] = nums[i];
		}
		for (int j =0; j < len; j++) {
			nums[j] = tmp[j];
		}
	}
	public void rotate2(int[] nums, int k) {
		int len = nums.length;
		while (len > 1 && k > 0) {
			int tmp = nums[len - 1];
			for (int i = len -2; i >= 0; i--) {
				nums[i+1] = nums[i];
			}
			nums[0] = tmp;
			k--;
		}
	}
	public void rotate(int[] nums, int k) {
		int tmp, prev;
		int len = nums.length;
		for (int i = 0; i < k; i++) {
			prev = nums[len-1];
			for (int j = 0; j < len; j++) {
				tmp = nums[j];
				nums[j] = prev;
				prev = tmp;
			}
		}
	}
	
	//26. 删除排序数组中的重复项
	public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int j =0; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return i+1;
    }
	
	//11. 盛最多水的容器
	public int maxArea(int[] height) {
		int ans = Integer.MIN_VALUE;
		for (int i = 0; i < height.length - 1; i++) {
			for (int j = i + 1; j < height.length; j++) {
				int h = Math.min(height[i], height[j]);
				int w = j - i;
				ans = Math.max(h*w, ans);
			}
		}
		return ans;
    }
	
	public int maxArea2(int[] height) {
		int ans = Integer.MIN_VALUE;
		int l = 0, r = height.length-1;
		while (l < r) {
			int w = r - l;
			int h = Math.min(height[l], height[r]);
			ans = Math.max(ans, w * h);
			
			if (height[r] < height[l]) 
				r--;
			else 
				l++;
		}
		return ans;
	}
	
	//Test
	public static void main(String args[]) {
		ExerciseArray test = new ExerciseArray();
		/*
		 * int nums1[] = new int[] {56,71,99,0,0,0}; int nums2[] = new int[] {2,5,66};
		 * test.merge3(nums1, 3, nums2, 3); System.out.println(Arrays.toString(nums1));
		 */
		/*
		 * int nums[] = new int[] {3,0,-2,-1,1,2}; List<List<Integer>> ans =
		 * test.threeSum2(nums); for (List<Integer> l : ans) System.out.println(l);
		 */
		
		int[] height = new int[] {1,8,6,2,5,4,8,3,7};
		System.out.println(test.maxArea2(height));
		
	}
}

