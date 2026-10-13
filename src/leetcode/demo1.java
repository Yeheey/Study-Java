package leetcode;

public class demo1 {
/*    给你一个非负整数 x ，计算并返回 x 的 算术平方根 。

    由于返回类型是整数，结果只保留 整数部分 ，小数部分将被 舍去 。*/
    public int mySqrt(int x) {
        if (x < 2) return x;

        int start = 1;
        int end = x / 2;      // sqrt(x) 不超过 x/2（x>=4）
        int result = 0;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            long sq = (long) mid * mid;

            if (sq == x) {
                return mid;
            } else if (sq < x) {
                result = mid;     // 记下当前可行解
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        demo1 d = new demo1();
        System.out.println(d.mySqrt(8));   // 2
        System.out.println(d.mySqrt(9));   // 3
    }
}