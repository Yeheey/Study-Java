package com.itheima.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class ArrayListDemo1 {
    static void main() {
        /*
        需求:定义一个集合，添加一些学生对象学生类的属性为:id，姓名，年龄。
        要求:
        1.遍历集合，将所有学生的属性打印在控制台上，每个学生一行
        2.定义一个方法，根据id查找学生的信息。存在:返回索引
        不存在:返回-1
        */
        
        // 1.创建集合对象
        ArrayList<Student> list = new ArrayList<>();
        
        // 2.创建对象
        Student s1 = new Student("001", "33", 18);
        Student s2 = new Student("002", "三1", 17);
        Student s3 = new Student("003", "三2", 19);
        
        list.add(s1);
        list.add(s2);
        list.add(s3);
        
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getId());
            if (list.get(i).getName().equals("三")) System.out.println("找到小三");
            if (list.get(i).getName() == "三") System.out.println("找到小三");
            if (list.get(i).getName().equalsIgnoreCase("JACK")) System.out.println("找到JACK");
            
            
        }
        
        ArrayList<Integer> tmp = new ArrayList<>(Arrays.asList(11, 23, 3, 0, 99, 156, 221, 93));
        // tmp.sort(null);
        tmp.sort(Comparator.reverseOrder());
        
        for (int i = 0; i < tmp.size(); i++) {
            System.out.println(tmp.get(i));
        }
        
        // 按年龄从小到大排序 按名字 从大到小排序 最后按照id从小到大排序
        list.sort(Comparator.comparingInt((Student p) -> p.getAge())
            .thenComparing(p -> p.getName()).reversed()
            .thenComparing(p -> p.getId()));
        
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
        }
        
    }
}

