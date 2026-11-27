package leetcode;

public class leetcode_704 {
    /*
    【704.二分查找】--【二分法】
    给定一个 n 个元素有序的（升序）整型数组 nums 和一个目标值 target
    写一个函数搜索 nums 中的 target，如果 target 存在返回下标，否则返回 -1。
    你必须编写一个具有O(log n) 时间复杂度的算法。
    */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int n = nums.length;
        int start = 0;
        int end = n - 1;
        
        while (start <= end) {
            if (end - start == 1 && target != nums[end] && target != nums[start]) {
                return -1;
            }
            int mid = (end - start) / 2 + start;
            int m = nums[mid];
            if (m < target) {
                start = mid + 1;
            } else if (m > target) {
                end = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
    
    static void main() {
        int[] nums = new int[]{-1, 0, 3, 5, 9, 12};
        int target = 9;
        System.out.println(search(nums, target)); // 4
        System.out.println(search(nums, 2)); // -1
        System.out.println(search(nums, 12)); // 5
        System.out.println(search(nums, 0));  // 1
        System.out.println(search(nums, 5));  // 3
        System.out.println(search(new int[]{9}, 9));   // 0
        System.out.println(search(new int[]{1, 3}, 3)); // 1
        System.out.println(search(nums, 9));  // 4
        System.out.println(search(nums, -1)); // 0
        System.out.println(search(nums, 2));  // -1
        System.out.println(search(new int[]{1, 3}, 1)); // 0
        System.out.println(search(new int[]{9}, 1));    // -1
    }
}
