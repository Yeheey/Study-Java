package leetcode;

public class demo3 {
    
    // 把字符串放数组里（右对齐）
    public static int[] copyData(String str, int len) {
        int[] arr = new int[len];
        int index = arr.length - 1;
        for (int i = str.length() - 1; i >= 0; i--) {
            arr[index] = str.charAt(i) - '0';  // '0' 代替 48
            index--;
        }
        return arr;
    }
    
    // 主函数入口
    public static void main(String[] args) {
        /*
          字符串计算-转为int数组，对位相加，长度+1，防止有进位情况
          定义两个字符串，记录为非负整数，求它们的和。
          输入:"12395"和"133"，输出:"12528"
          注意:需要数据过大，超出int取值范围的情况
         */
        
        // 1.定义两个字符串
        String str1 = "12358";
        String str2 = "999";
        
        // 2.把两个字符串放入到int数组里
        int len = Math.max(str1.length(), str2.length());
        int[] arr1 = copyData(str1, len);
        int[] arr2 = copyData(str2, len);
        
        // 3.定义一个结果数组，长度+1（预留最高位进位）
        int[] result = new int[len + 1];
        
        // 4.定义一个值表示进位
        int supNum = 0;
        
        // 5.核心：从个位开始，逐位相加
        for (int i = len - 1; i >= 0; i--) {
            int temp = arr1[i] + arr2[i] + supNum;
            result[i + 1] = temp % 10;  // 本位：i+1 是因为 result 前面多一个进位占位
            supNum = temp / 10;         // 进位
        }
        
        // 6.考虑最高位的进位值
        result[0] = supNum;
        
        // 7.数组转字符串
        // 若最高位无进位（result[0]==0），从下标 1 开始输出；否则从 0 开始
        StringBuilder sb = new StringBuilder();
        int start = (result[0] == 0) ? 1 : 0;
        for (int i = start; i < result.length; i++) {
            sb.append(result[i]);
        }
        
        System.out.println(sb);  // 13357
    }
}