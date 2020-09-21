public class RotatedSortedArray {
    //33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;

        int l = 0, r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r-l)/2;
            if (nums[mid] == target) return mid;

            if (nums[l] <= nums[mid]) { //前半部分有序
                if (target >= nums[l] && target < nums[mid]) //target落在了前半部分，向前规约
                    r = mid - 1;
                else {
                    l = mid + 1;
                }
            } else { //后半部分有序
                if (target > nums[mid] && target <= nums[r]) {//target落在了后半部分，向后规约
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return -1;
    }

    public int search_2(int[] nums, int target) {
        if (nums == null || nums.length == 0) return -1;
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int mid = l + (r - l)/2;
            if (nums[mid] == target) return mid;
            //前半部分有序，并且target不在前半部分，则向后归约
            if (nums[l] <= nums[mid] && (target > nums[mid] || target < nums[l])) {
                l = mid + 1;
            //前半部分发生旋转，并且target不在前半部分，则向后规约
            } else if (target > nums[mid] && target < nums[0]) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    //153. 寻找旋转排序数组中的最小值
    public int findMin(int[] nums) {
        int l = 0;
        int r = nums.length - 1;
        while (l < r) {
            int mid = l + (r -l)/2;
            if (nums[r] < nums[mid]) {//后半部分无序，min一定出现在后半部分
                l = mid + 1;
            } else {//后半部分有序(包括前半部分有序或无序），min一定出现在前半部分
                r = mid;
            }
        }
        return nums[l];
    }

    public static void main(String[] args) {
        RotatedSortedArray r = new RotatedSortedArray();
        int[] nums = new int[] {5,1,3};
        // int target = 5;
        // System.out.println(r.search(nums, target));
        System.out.println(r.findMin(nums));
    }
}
