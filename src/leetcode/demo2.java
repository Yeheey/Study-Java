package leetcode;

public class demo2 {
    public static void main(String[] args) {
        /*
         * 去除递增有序数组中的重复元素 - 快慢指针
         * 慢指针：存入的位置
         * 快指针：找不重复的数据
         * 相等：舍弃快指针
         * 不等：快指针存到慢指针的位置
         */
        int[] arr = new int[]{1, 2, 2, 2, 2, 3, 3, 3};
        
        int slow = 0;
        int fast = 1;
        
        while (fast < arr.length) {
            // 判断两指针值是否相同
            if (arr[slow] == arr[fast]) {
                // 相同：舍弃快指针，快指针自增
                fast++;
            } else {
                // 不相同：慢指针先自增，再把快指针的值存到慢指针位置
                slow++;
                arr[slow] = arr[fast];
                fast++;
            }
        }
        
        // 此时 arr[0..slow] 就是去重后的结果，长度为 slow + 1
        for (int i = 0; i <= slow; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        System.out.println("去重后长度：" + (slow + 1));
    }
}