package com.huawei;

public class v260812_3_ysy {
    /*
    【26.8.12-3】末世分配资源包--【二分法+验证贪心】--思考40min+写40min+看答案15min
    【⭐注意边界】
    题目描述
    末世时代，政府为各地分配资源，现有资源分配表 nums[n]，要求按如下规则分配给 k 个营地：

    每个营地只分配一段连续的分配表
    每个营地至少分到一份资源
    所有的资源必须全部分出
    分配方式：尽量平均分配（即：得利最大的营地获得的资源值尽量小）

    输入描述
    资源存储数组 nums[n]（资源数 n: 0≤n≤1000，每份资源数：1≤nums[i]≤100000）
    营地数 k（1≤k≤min(50,n)）

    输出描述
    在最优平均分配情况下，得利最大团队所获得的资源数
    */
    
    static int backSeg(int[] nums, int target) {
        int seg = 1;
        int temp = nums[1];
        for (int i = 1; i < nums.length; i++) {
            if (temp + nums[i] > target) {
                seg++;
                temp = nums[i];
            } else {
                temp += nums[i];
            }
        }
        return seg;
    }
    
    static int solve(int[] nums, int k) {
        int left = 0; // 下限：最大资源包的值
        int right = 0; // 上限：所有资源全部给一个营地
        
        for (final int num : nums) {
            left = Math.max(left, num);
            right += num;
        }
        
        while (left <= right) {
            int mid = (right - left) / 2 + left;
            // 按照mid大小分 最多分给seg个营地
            int seg = backSeg(nums, mid);
            
            if (seg > k) {
                // 按照mid分成好多段后 还剩资源 说明target落在[mid,right]之间
                left = mid + 1;
            } else {
                // 按照mid分成好多段后 不够分 说明mid找大了
                right = mid - 1;
            }
        }
        
        return left;
    }
    
    static void main() {
        int[] nums = {3, 4, 2, 1};
        int k = 4;
        // 4
        System.out.println(solve(nums, k));
    }
}
