package com.huawei;

import java.util.*;

public class c14_answer {
    public static void main(String[] args) {
        // 创建Scanner对象用于读取输入
        Scanner scanner = new Scanner(System.in);
        // 读取第一行的打卡记录数量N
        int n = Integer.parseInt(scanner.nextLine());
        
        // 创建字符串数组存储所有打卡记录
        String[] clockRecords = new String[n];
        // 循环读取N行打卡记录
        for (int i = 0; i < n; i++) {
            clockRecords[i] = scanner.nextLine();
        }
        
        // 调用方法检查异常打卡记录并获取结果
        String result = checkAbnormalRecords(clockRecords);
        // 输出结果
        System.out.println(result);
        
        // 关闭Scanner，释放资源
        scanner.close();
    }
    
    /**
     * 检查打卡记录中的异常情况
     * @param clockRecords 打卡记录数组
     * @return 异常记录的字符串表示，如果没有异常则返回"null"
     */
    public static String checkAbnormalRecords(String[] clockRecords) {
        // 如果打卡记录为空，直接返回"null"
        if (clockRecords == null || clockRecords.length == 0) {
            return "null";
        }
        
        // 解析所有打卡记录为Record对象
        List<Record> records = new ArrayList<>();
        for (int i = 0; i < clockRecords.length; i++) {
            // 按逗号分割打卡记录的各个部分
            String[] parts = clockRecords[i].split(",");
            String id = parts[0];             // 员工工号
            int time = Integer.parseInt(parts[1]);        // 打卡时间（分钟）
            int distance = Integer.parseInt(parts[2]);    // 打卡距离（km）
            String actualDevice = parts[3];   // 实际设备号
            String registeredDevice = parts[4]; // 注册设备号
            
            // 创建Record对象并加入列表，i表示原始输入中的索引位置
            records.add(new Record(id, time, distance, actualDevice, registeredDevice, i));
        }
        
        // 用于存储异常记录的索引
        Set<Integer> abnormalIndices = new HashSet<>();
        
        // 检查第一种异常情况：实际设备号与注册设备号不一致
        for (Record record : records) {
            if (!record.actualDevice.equals(record.registeredDevice)) {
                // 记录异常记录的索引
                abnormalIndices.add(record.index);
            }
        }
        
        // 按员工ID对打卡记录进行分组
        Map<String, List<Record>> employeeRecords = new HashMap<>();
        for (Record record : records) {
            // 如果该员工ID不存在，则创建新列表；如果已存在，则将记录添加到现有列表
            employeeRecords.computeIfAbsent(record.id, k -> new ArrayList<>()).add(record);
        }
        
        // 检查第二种异常情况：时间差小于60分钟但距离差大于5km
        for (String employeeId : employeeRecords.keySet()) {
            // 获取该员工的所有打卡记录
            List<Record> empRecords = employeeRecords.get(employeeId);
            // 对该员工的所有打卡记录进行两两比较
            for (int i = 0; i < empRecords.size(); i++) {
                for (int j = i + 1; j < empRecords.size(); j++) {
                    Record r1 = empRecords.get(i);
                    Record r2 = empRecords.get(j);
                    
                    // 计算两条记录的时间差（绝对值）
                    int timeDiff = Math.abs(r1.time - r2.time);
                    // 计算两条记录的距离差（绝对值）
                    int distanceDiff = Math.abs(r1.distance - r2.distance);
                    
                    // 如果时间差小于60分钟且距离差大于5km，则两条记录都标记为异常
                    if (timeDiff < 60 && distanceDiff > 5) {
                        abnormalIndices.add(r1.index);
                        abnormalIndices.add(r2.index);
                    }
                }
            }
        }
        
        // 如果没有找到异常记录，返回"null"
        if (abnormalIndices.isEmpty()) {
            return "null";
        }
        
        // 将异常记录的索引排序，以便按输入顺序输出
        List<Integer> sortedIndices = new ArrayList<>(abnormalIndices);
        Collections.sort(sortedIndices);
        
        // 格式化输出结果
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sortedIndices.size(); i++) {
            // 如果不是第一条记录，添加分号分隔符
            if (i > 0) {
                result.append(";");
            }
            // 添加异常记录的原始字符串
            result.append(clockRecords[sortedIndices.get(i)]);
        }
        
        return result.toString();
    }
    
    /**
     * 打卡记录类，用于存储解析后的打卡记录信息
     */
    static class Record {
        String id;                 // 员工工号
        int time;                  // 打卡时间（分钟）
        int distance;              // 打卡距离（km）
        String actualDevice;       // 实际设备号
        String registeredDevice;   // 注册设备号
        int index;                 // 在原始输入中的索引位置
        
        /**
         * 构造函数
         */
        public Record(String id, int time, int distance, String actualDevice, String registeredDevice, int index) {
            this.id = id;
            this.time = time;
            this.distance = distance;
            this.actualDevice = actualDevice;
            this.registeredDevice = registeredDevice;
            this.index = index;
        }
    }
}