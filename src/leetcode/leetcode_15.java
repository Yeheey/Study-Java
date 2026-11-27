package leetcode;

import java.util.*;

public class leetcode_15 {
    /*
    【15.三数之和】--【双指针】
    给你一个整数数组 nums ，判断是否存在三元组 [nums[i], nums[j], nums[k]]
    满足 i != j、i != k 且 j != k ，同时还满足 nums[i] + nums[j] + nums[k] == 0 。
    请你返回所有和为 0 且不重复的三元组。
    
    注意：答案中不可以包含重复的三元组
    */
    
    
    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        
        // 固定i
        for (int i = 0; i < n; i++) {
            // 双指针：j在i后面，k在末尾
            int j = i + 1;
            int k = n - 1;
            while (j < k) {
                int ni = nums[i];
                int nj = nums[j];
                int nk = nums[k];
                int sum = ni + nj + nk;
                if (sum == 0) {
                    List<Integer> tmp = Arrays.asList(ni, nj, nk);
                    res.add(tmp);
                    // 找到一组，同时移动两个指针继续找其他组合
                    j++;
                    k--;
                } else if (sum < 0) {
                    // 和太小，左指针右移，变大
                    j++;
                } else {
                    // 和太大，右指针左移，变小
                    k--;
                }
            }
        }
        
        // 依然用你熟悉的HashSet去重逻辑，不换去重方式
        Set<List<Integer>> set = new HashSet<>(res);
        return new ArrayList<>(set);
    }
    
    
    // 双重for会超时
    public static List<List<Integer>> threeSum1(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;  // 3 <= n <= 3000
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int k = j + 1;
                
                // num[l] + nums[r]= -num[i]
                while (j < k && k < n) {
                    int ni = nums[i];
                    int nj = nums[j];
                    int nk = nums[k];
                    if (ni + nj + nk == 0) {
                        // 直接添加List<Integer>，不再存int[]
                        List<Integer> tmp = Arrays.asList(ni, nj, nk);
                        res.add(tmp);
                    }
                    k++;
                }
            }
        }
        
        // 去重：直接把res丢进HashSet，不用中间转来转去
        Set<List<Integer>> set = new HashSet<>(res);
        
        return new ArrayList<>(set);
    }
    
    static void main() {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        // 输出：[[-1,-1,2],[-1,0,1]]
        System.out.println(threeSum(nums));
    }
}
