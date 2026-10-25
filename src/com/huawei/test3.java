package com.huawei;

public class test3 {
    /*
    3.【26.9.6-2】嵌入式传感器寄存器数据解析
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
    
    static long solve(long packet) {
        // 1.先按照无符号形式理解，右移+掩码提取各位置的值
        long res = packet & 0xFFFFFFFFL;
        // bit0 状态位 1正常 0故障
        long status = res & 1L;
        // bit1~2 寄存器id 0-3
        long regId = (res >> 1) & 3L; // 右移1位后 与0b11取余 也就是取1-2位
        // bit3 数据类型  1是变长数据类型 0是固定长度数据类型
        long dataType = (res >> 3) & 1L;
        // bit4~11 数据修正因子 右移4位 低取8位 0xFF
        long factor = (res >> 4) & 0xFFL;
        // bit12~15 数据有效长度 数据类型==1生效  右移12位 取4位
        long dataLen = (res >> 12) & 0xFL;
        // bi16~30 数据 数据类型==1 根据长度确定有效数据位 数据类型==0 全部有效
        long data = (res >> 16) & 0x7FFL;
        // bit31 数据奇偶校验位 有效数据位+奇偶校验位中1的个数==偶数  校验通过
        long isOdd = (res >> 31) & 1L;
        
        // 2.1校验 传感器故障 -1
        if (status == 0) {
            return -1;
        }
        // 2.2 数据有效长度
        long dataValidLen = dataType == 1 ? dataLen : 0;
        // 生成一个低 dataValidLen 位全为 1、其余位为 0 的位掩码（bit mask），用于后续按位与操作提取有效位
        long dataMask = dataValidLen == 0 ? 0 : ((1 << dataValidLen) - 1);
        // 取有效数据
        long validData = dataType == 1 ? (dataMask & data) : data;
        // 如果count值非偶数 则-2
        long count = Long.bitCount(validData) + isOdd;
        if (count % 2 != 0) {
            return -2;
        }
        // 2.3数据类型1 有效长度0 返回-3
        if (dataType == 1 && dataLen == 0) {
            return -3;
        }
        // 2.4 计算结果
        res = getRes(regId, factor, data);
        // 3.加上符号位
        return toSigned32(res);
    }
    
    static long getRes(final long regId, final long factor, final long data) {
        long res;
        if (regId == 0) {
            res = data & factor;
        } else if (regId == 1) {
            res = data | factor;
        } else if (regId == 2) {
            res = data ^ factor;
        } else {
            res = ~(data ^ factor);
        }
        return res;
    }
    
    /*把 value 的低 32 位取出，并按 32 位有符号整数（int）的规则重新解释成 long。
    也就是模拟“(int) value 再转回 long”的效果，但全程用 long 运算，避免中间 int 溢出或强转的隐式问题。*/
    private static long toSigned32(long value) {
        value &= 0xFFFFFFFFL;          // 只保留低 32 位
        if (value >= 0x80000000L) {    // 若最高位（第31位）为1
            value -= 0x100000000L;     // 减去 2^32，做符号扩展
        }
        return value;
    }
    
    static void main() {
        System.out.print(solve(343873)); // output:4
    }
    
    // // 十进制转二进制
    // static StringBuilder toTwo(long n) {
    //     StringBuilder sb = new StringBuilder();
    //     // 循环固定32次，保证一定32个字符，低位在前 sb[0]=bit0
    //     for (int i = 0; i < 32; i++) {
    //         if (n % 2 == 0) {
    //             sb.append('0');
    //         } else {
    //             sb.append('1');
    //         }
    //         n /= 2;
    //     }
    //     return sb;
    // }
    //
    //
    // // 二转十进制
    // static int toTen(StringBuilder s) {
    //     int res = 0;
    //
    //     for (int i = s.length() - 1; i >= 0; i--) {
    //         res += (s.charAt(i) - '0') * Math.pow(2, s.length() - 1 - i);
    //
    //     }
    //     return res;
    // }
    //
    // // 数据解析
    // static int solve(long packet) {
    //     StringBuilder s = toTwo(packet);
    //     if (s.charAt(0) == '0') {
    //         return -1;
    //     }
    //     int validLen = toTen(new StringBuilder(s.substring(12, 16)));
    //     boolean isVar = s.charAt(3) == '1';
    //
    //     int oneCnt = 0;
    //     int start = 16;
    //     int endBit;
    //     if (!isVar) {
    //         endBit = 30;
    //     } else {
    //         endBit = 16 + validLen - 1;
    //     }
    //     for (int i = start; i <= endBit; i++) {
    //         if (s.charAt(i) == '1') oneCnt++;
    //     }
    //     if (s.charAt(31) == '1') oneCnt++;
    //     if (oneCnt % 2 != 0) {
    //         return -2;
    //     }
    //
    //     if (isVar && validLen == 0) {
    //         return -3;
    //     }
    //
    //     int id = toTen(new StringBuilder(s.substring(1, 3)));
    //     int modify = toTen(new StringBuilder(s.substring(4, 12)));
    //     int dataVal;
    //     if (!isVar) {
    //         dataVal = toTen(new StringBuilder(s.substring(16, 31)));
    //     } else {
    //         dataVal = toTen(new StringBuilder(s.substring(16, 16 + validLen)));
    //     }
    //
    //     int ans;
    //     long ans = test3$1.getRes(id, modify, dataVal);
    //     return ans & 0xFFFFFFFF;
    // }
    
    
/*
    解题思路
    核心思想:
    把输入整数先按32位无符号形式理解，再用“右移+掩码”直接提取各个字段。
    需要注意三个校验顺序:
        1.bit0为0时，立即返回-1;
        2.按数据类型得到有效数据后，统计有效数据位和bit31中1的个数，奇数返回 -2;
        3.变长数据且有效长度为0时，返回-3。
    通过校验后，根据寄存器ID对有效数据和修正因子做位运算，并将结果按32位有符号整数输出。
    算法步骤:
        1.将 packet归一化为32位无符号值u。
        2.分别提取状态位、寄存器ID、数据类型、修正因子、有效长度、原始数据和校验位。
        3.按规则完成状态、奇偶校验和变长长度校验。
        4.根据寄存器ID执行按位与、或、异或、同或。
        5.结果与oxFFFFFFFF 取与，再转换为32位有符号整数输出。
    复杂度分析:
        ·时间复杂度:0(1)
        ·空间复杂度:0(1)
*/
}
