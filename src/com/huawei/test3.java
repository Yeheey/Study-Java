package com.huawei;

public class test3 {
    /*    3.【26.9.6-2】嵌入式传感器寄存器数据解析
    题目描述
    系统从传感器的某个寄存器口读取一个32位的int 型整数packet。请按如下要求解析输入数据，然后按十进制格式输出结果。
    
    该数据的二进制结构定义如下:
    ·bit0(最低位):状态。1表示传感器正常，0表示传感器故障。
    ·bit1~bit2:寄存器ID。0~3.
    ·bit3:数据类型。1表示变长数据类型，0表示固定长度数据类型。
    ·bit4~bit11:数据修正因子。
    ·bit12~bit15:数据有效长度。0~15，数据类型为1时才生效。
    ·bit16~bit30:数据。数据类型为1时，从低到高依据数据有效长度确定实际有效数据位;数据类型为0时，全部为有效数据位。
    ·bit31:数据奇偶校验位:数据中的所有有效数据位与奇偶校验位中1的个数为偶数，表示数据校验通过;否则为不通过。
    
    解析流程:
    1.检查状态，如果传感器故障(状态为0)，返回-1。
    2.检查数据奇偶校验是否通过，不通过则返回-2。
    3.数据类型为1时，数据有效长度如果为0，则返回-3。
    4.寄存器ID为0时:返回有效数据与数据修正因子按位与的结果。
    5.寄存器ID为1时:返回有效数据与数据修正因子按位或的结果。
    6.寄存器ID为2时:返回有效数据与数据修正因子按位异或的结果。
    7.寄存器ID为3时:返回有效数据与数据修正因子按位同或的结果。
    注:解析流程中4、5、6、7步骤的结果返回前需要和0xOFFFFFFFF取与。
    
    输入描述：
    输入为一个32位整型数值packet。
    输出描述
    按十进制格式输出解析结果。
    */
    
    // 十进制转二进制
    static StringBuilder toTwo(long n) {
        StringBuilder sb = new StringBuilder();
        // 循环固定32次，保证一定32个字符，低位在前 sb[0]=bit0
        for(int i = 0; i < 32; i++){
            if (n % 2 == 0) {
                sb.append('0');
            } else {
                sb.append('1');
            }
            n /=2;
        }
        return sb;
    }

    
    // 二转十进制
    static int toTen(StringBuilder s) {
        int res = 0;
        
        for (int i = s.length() - 1; i >= 0; i--) {
            res += (s.charAt(i) - '0') * Math.pow(2,s.length() -1 -i);
            
        }
        return res;
    }
    
    // 数据解析
    static int solve(long packet) {
        StringBuilder s = toTwo(packet);
        if (s.charAt(0) == '0') {
            return -1;
        }
        int validLen = toTen(new StringBuilder(s.substring(12, 16)));
        boolean isVar = s.charAt(3) == '1';
        
        int oneCnt = 0;
        int start = 16;
        int endBit;
        if (!isVar) {
            endBit = 30;
        } else {
            endBit = 16 + validLen - 1;
        }
        for (int i = start; i <= endBit; i++) {
            if (s.charAt(i) == '1') oneCnt++;
        }
        if (s.charAt(31) == '1') oneCnt++;
        if (oneCnt % 2 != 0) {
            return -2;
        }
        
        if (isVar && validLen == 0) {
            return -3;
        }
        
        int id = toTen(new StringBuilder(s.substring(1, 3)));
        int modify = toTen(new StringBuilder(s.substring(4, 12)));
        int dataVal;
        if (!isVar) {
            dataVal = toTen(new StringBuilder(s.substring(16, 31)));
        } else {
            dataVal = toTen(new StringBuilder(s.substring(16, 16 + validLen)));
        }
        
        int ans;
        if (id == 0) {
            ans = dataVal & modify;
        } else if (id == 1) {
            ans = dataVal | modify;
        } else if (id == 2) {
            ans = dataVal ^ modify;
        } else {
            ans = ~(dataVal ^ modify);
        }
        return ans & 0xFFFFFFFF;
    }

    
    static void main() {
        // StringBuilder s = new StringBuilder("1001");
        // System.out.println(toTen(s));
        System.out.print(solve(343873)); // output:4
    }
}
