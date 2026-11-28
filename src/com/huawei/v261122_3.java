package com.huawei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class v261122_3 {
    /*
        【商品计税金额计算】--【模拟计算+排序】
        题目描述
        发票系统中商品计税后的最终金额为：商品单价 × 数量 + 税费；其中税费包括价内税和价外税，价内税已在商用价格中包含，无需再计算，价外税需要单独计算且税额有上限。
        
        现给定一件商品信息，包括 unitPrice、数量 quantity、税种列表 taxes（taxName、withinPriceFlag、accountingFlag、taxAmount），请按如下规则计算最终金额：
        - 去重：taxName 同名税种仅保留最后一个。
        - 税种分类：当 withinPriceFlag 和 accountingFlag 组合为 (N,Y) 时，为价外税。
        - 价外税计算：累计金额有上限，不能超过商品总价的一半 (商品单价 × 数量)/2；每项价外税只能整体计入或不计入，需要在不超过上限的前提下使计入总金额最大。
        
        输入描述
        输入依次为整数 unitPrice、整数 quantity、二维字符串数组 taxes，按样例在同一行用英文逗号分隔。
        - unitPrice：商品单价，整数，1 ≤ unitPrice ≤ 10000。
        - quantity：商品数量，整数，1 ≤ quantity ≤ 1000。
        - taxes：商品税费列表，每项格式为 [taxName,withinPriceFlag,accountingFlag,taxAmount]，这四个字段均为字符串。
        - taxName: 税种名称 同名税种仅保留最后一个 区分大小写
        - withinPriceFlag、accountingFlag 取字符串 "Y" 或 "N"。
        - taxAmount 的字符串表示一个整数，0 ≤ taxAmount ≤ 10000。
        - taxes 的长度为 0 ~ 100；taxName 为长度 1 ~ 50 的非空字符串，同名比较区分大小写。
       
        去重按原列表顺序保留每个名称最后出现的整条记录，随后再按标志分类；不满足 (N,Y) 的记录不额外增加最终金额。taxAmount 是该税种本次可计入的总额，不再乘以商品数量。
    */
    
    /**
     * unitPrice 商品单价
     * quantity 商品数量
     * taxes 商品税费列表 = [taxName 商品名称 - 去重 只保留最后一个 , withinPriceFlag, accountingFlag, taxAmount]
     * 当 withinPriceFlag 和 accountingFlag 组合为 (N,Y) 时，为价外税 ; 不满足 (N,Y) 的记录不额外增加最终金额
     * taxAmount 是该税种本次可计入的总额，不再乘以商品数量
     * */
    public static int solve(int unitPrice, int quantity, String[][] taxes) {
        // 最终金额为：商品单价 × 数量 - (base) + 税费 - tax；
        // 价外税 不能超过商品总价的一半
        int base = unitPrice * quantity; // 商品单价 × 数量
        int tax = 0; // 税费 -> 价外税
        int limit = (base) / 2; // tax 上限
        
        // 去重 key-taxName value-taxAmount
        HashMap<String, String> map = new HashMap<>();
        // 排顺序 [[taxName,withinPriceFlag,accountingFlag,taxAmount]]
        List<List<String>> list = new ArrayList<>();
        for (final String[] t : taxes) {
            String taxName = t[0];
            String withinPriceFlag = t[1];
            String accountingFlag = t[2];
            String taxAmount = t[3];
            
            if (!map.containsKey(taxName)) {
                // 还没有这条税
                List<String> temp = new ArrayList<>();
                temp.add(taxName);
                temp.add(withinPriceFlag);
                temp.add(accountingFlag);
                temp.add(taxAmount);
                list.add(temp);
            } else {
                // 已经有这条税 修改list里对应税费
                list.forEach(item -> {
                    // [taxName,withinPriceFlag,accountingFlag,taxAmount]
                    if (item.get(0).equals(taxName)) {
                        item.set(1, withinPriceFlag);
                        item.set(2, accountingFlag);
                        item.set(3, taxAmount);
                    }
                });
            }
            // map直接覆盖
            map.put(taxName, taxAmount);
        }
        
        for (List<String> item : list) {
            if (item.get(1).equals("N") && item.get(2).equals("Y")) {
                if (tax + Integer.parseInt(item.get(3)) <= limit) {
                    tax += Integer.parseInt(item.get(3));
                }
            }
        }
        
        System.out.println("map" + map);
        System.out.println("list" + list);
        return base + Math.min(limit, tax);
    }
    
    static void main() {
        int unitPrice = 50;
        int quantity = 3;
        String[][] taxes = {{"ICMS", "N", "Y", "15"}, {"ICMS", "Y", "Y", "9"}, {"IPI", "N", "Y", "8"}};
        // 158
        System.out.println(solve(unitPrice, quantity, taxes));
        
        int unitPrice1 = 100;
        int quantity1 = 2;
        String[][] taxes1 = {{"ICMS", "Y", "Y", "18"}, {"IPI", "N", "Y", "60"}, {"COFINS", "N", "Y", "50"}, {"ISS", "N", "Y", "40"}, {"PIS", "N", "N", "5"}};
        // 300
        System.out.println(solve(unitPrice1, quantity1, taxes1));
        
        int unitPrice2 = 100;
        int quantity2 = 1;
        String[][] taxes2 = {{"IPI", "N", "Y", "80"}, {"IPI", "N", "Y", "40"}, {"COFINS", "N", "Y", "30"}};
        // 140
        System.out.println(solve(unitPrice2, quantity2, taxes2));
    }
}

