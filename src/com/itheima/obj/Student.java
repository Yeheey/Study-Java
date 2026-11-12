package com.itheima.obj;

/**
 * JavaBean：学生实体类
 * 属性：姓名、性别、年龄、身高
 * 行为：吃饭、学习、睡觉
 */
public class Student {
    // 私有成员变量
    private String name;
    private char gender;
    private int age;
    private double height;
    
    // 空参构造
    public Student() {
    }
    
    // 全参构造
    public Student(String name, char gender, int age, double height) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.height = height;
    }
    
    // getter & setter
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public char getGender() {
        return gender;
    }
    
    public void setGender(char gender) {
        this.gender = gender;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double height) {
        this.height = height;
    }
    
    // 成员方法：行为
    public void eat() {
        System.out.println(name + "正在吃饭");
    }
    
    public void study() {
        System.out.println(name + "正在学习");
    }
    
    public void sleep() {
        System.out.println(name + "正在睡觉");
    }
    
    // 重写toString，方便打印学生信息（面试高频）
    @Override
    public String toString() {
        return "Student{" +
            "name='" + name + '\'' +
            ", gender=" + gender +
            ", age=" + age +
            ", height=" + height +
            '}';
    }
}
