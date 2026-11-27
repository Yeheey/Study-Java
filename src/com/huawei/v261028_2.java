package com.huawei;

import java.util.*;

public class v261028_2 {
    /*
    【26.10.28-2】【社交网络消息转发】--【模拟计算+位运算】--50min--注意有环的情况 设置一个visited访问过
    题目描述
    在一个社交网络中，有 N 个用户，编号为 0 到 N-1。每个用户都有一个用非负整数表示的转发名单。
    
    数组 forwardLists 中的 forwardLists[u] 表示用户 u 的转发名单。
    如果该整数的二进制表示中，第 i 位为 1，则用户 u 收到消息后会将消息转发给用户 i。
    位编号从最低位开始，最低位为第 0 位。
    另给定一个非负整数 initialSenders，其二进制表示中第 i 位为 1，表示用户 i 是初始消息发送者。
    初始发送者也视为已经收到消息，并会按照自己的转发名单继续转发。
    用户收到消息后，会立即将消息转发给名单中的所有用户，直到没有新的用户收到消息为止。同一用户重复收到消息不会产生新的接收用户。
    
    请计算最终收到消息的用户集合，并将其编码为一个整数输出：
    结果的二进制表示中，第 i 位为 1，当且仅当用户 i 最终收到了消息。
    
    本改编题约定：
    只有低 N 位对应实际用户；转发名单和初始发送者掩码中编号大于或等于 N 的高位均忽略。initialSenders 为 0 时，没有用户发起消息，结果为 0。
    
    输入描述：
    输入共三行，所有整数均使用十进制表示。
    第一行输入一个整数 N，表示用户数量。
    第二行输入 N 个整数，以空格分隔，依次表示 forwardLists [0] 到 forwardLists [N-1]。
    第三行输入一个整数 initialSenders，表示初始消息发送者集合。
    
    数据范围:
    1≤ N ≤16
    0≤ forwardLists[i] < 65536
    0≤ initialSenders < 65536
    
    输出描述
    输出一行一个十进制整数，表示最终收到消息的用户集合的位掩码。
    例如，仅用户 0 和用户 1 收到消息时，结果的二进制表示为 11，输出十进制整数 3。
*/
    // 十进制转int[]
    static List<Integer> mes(int num, int N) {
        String s = Integer.toBinaryString(num);
        // 16位 补前导0
        String bin16 = String.format("%16s", s).replace(' ', '0');
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (bin16.charAt(i) == '1' && 16 - 1 - i < N) {
                res.add(16 - 1 - i);
            }
        }
        return res;
    }
    
    // list转int
    static int formatRes(List<Integer> list) {
        StringBuilder sb = new StringBuilder();
        
        for (int i = 15; i >= 0; i--) {
            if (i < list.size() && i == list.get(i)) {
                sb.append(1);
            } else {
                sb.append(0);
            }
        }
        
        System.out.println(sb);
        
        return Integer.parseInt(sb.toString(), 2);
    }
    
    static int solve(int[] forwardLists, int initialSenders) {
        // initialSenders 为 0 时，没有用户发起消息，结果为 0。
        if (initialSenders == 0) {
            return 0;
        }
        
        int N = forwardLists.length;
        List<Integer> contains = new ArrayList<>();
        // 找到所有收到消息的编号
        Queue<Integer> queue = new ArrayDeque<>(mes(initialSenders, N));
        // 同一个人再次收到消息不再入队。没有这个标记，互相转发会一直循环。
        boolean[] visited = new boolean[N];
        
        for (final int userId : mes(initialSenders, N)) {
            visited[userId] = true;
            contains.add(userId);
            queue.add(userId);
        }
        
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (final int next : mes(forwardLists[cur], N)) {
                // 如果访问过 跳出循环
                if (visited[next]) {
                    continue;
                }
                // 没有访问过 vis = true
                visited[next] = true;
                contains.add(next);
                queue.add(next);
            }
        }
        Collections.sort(contains);
        
        return formatRes(contains);
    }
    
    
    static void main() {
        int[] forwardLists = {2, 4, 0};
        int initialSenders = 1;
        // 7 0-1-2 初始1=001 0号 111=7
        System.out.println("---ans" + solve(forwardLists, initialSenders));
        
        int[] forwardLists1 = {2, 0, 8, 0, 0};
        int initialSenders1 = 5;
        // 15 0-1 2-3 初始5=101 0和2号 1111=15
        System.out.println("---ans" + solve(forwardLists1, initialSenders1));
        
        int[] forwardLists2 = {2, 4, 8, 0};
        int initialSenders2 = 8;
        // 0 0-1 1-2 2-3 初始8=1000 3号
        System.out.println("---ans" + solve(forwardLists2, initialSenders2));
        
        int[] forwardLists3 = {2, 1};
        int initialSenders3 = 1;
        // 3 0-1-0 有环
        System.out.println("---ans" + solve(forwardLists3, initialSenders3));
    }
}