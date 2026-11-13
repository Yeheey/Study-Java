package com.itheima.obj;

/**
 * 测试类：创建学生对象，调用属性和方法
 */
public class UseStudent {
    // 程序入口：固定格式 public static void main(String[] args)
    public static void main(String[] args) {
        // 方式1：空参构造 + set赋值
        Student stu1 = new Student();
        stu1.setName("张三");
        stu1.setGender('男');
        stu1.setAge(23);
        stu1.setHeight(175.5);
        
        System.out.println("===== 学生1信息 =====");
        System.out.println(stu1);
        stu1.study();
        stu1.eat();
        stu1.sleep();
        
        System.out.println("===== 学生2信息 =====");
        // 方式2：全参构造直接赋值
        Student stu2 = new Student("小胖", '女', 24, 168.0);
        System.out.println(stu2);
        stu2.study();
        stu2.eat();
        stu2.sleep();
    }
}

/*
    1.就近原则
    -在方法当中直接使用变量查找顺序:先找局部变量，再找成员变量
    
    2.this的作用?
    -可以区别成员变量和局部变量:
    System.out.println(age);//触发就近原则
    System.out.println(this.age);//使用成员变量
    
    3.this的本质
    -代表所在方法调用者的内存地址
    
    4.把一个对象传递给方法，实际传递的是什么?
    -对象的内存地址
    
    5.当多个变量指向同一个对象的时候，会发生什么?
    -只要有一个变量修改了对象中的属性其他变量再次访问就是修改之后的结果了
    
    6.内存
    6.1 Student stu = new Student();创建对象的七步:
    -加载class字节码文件
    -申明等号左边的局部变量
    -在堆里面开辟一个空间(对象)
    -给对象中的属性进行默认初始化
    -给对象中的属性进行显示初始化
    -给对象中的属性利用构造方法进行初识化
    -把对象的内存地址赋值给等号左边的变量
    
    6.2 方法出栈之后，方法里面的变量全部消失
    6.3 如果没有任何地方使用堆里面的对象，那么对象也会从堆里面消失
    6.4 方法区里面字节码信息一般不会消失，除非关闭虚拟机
    
    7.static关键字：静态只能调用静态，非静态可以调用所有，静态方法无this
 */

/**
 * final修饰变量，此时叫做常量
 * 特点1:只能被赋值一次，一旦赋值，无法再次修改。
 * 特点2:常量名大写，多个单词之间用下划线隔开
 *
 * 细节:
 * 基本数据类型:
 * byte short int long float double char boolean
 * 变量里面记录的是真实的数据
 * finalint a=10; 此时变量里面记录的数据无法发生改变
 *
 * 引用数据类型:
 * 除了上面四类八种，其他所有的数据类型都是引用类型
 * int[] Student Teacher...
 * 而对象里面的属性值，是可以发生改变
 * stu里面的记录对象的内存地址，I环不可改变的是stu记录的内存地址
 * final Student stu = new Student();
 *
 * 综上所述:
 * final修饰哪个变量，这个变量里面记录的内容就无法再次发生改变
 */

/**
 * 每一个枚举项，都是该枚举类的对象
 * 枚举项在底层其实就是常量，默认用publicstaticfinal修饰
 * 枚举类的第一行上必须是枚举项，枚举项之间用逗号隔开，以分号作为结尾
 * 枚举类的构造方法必须是private修饰，不让外界创建本类的对象
 * 编译器会给枚举类新增两个默认存在的方法:values()，valueOf
 *
 */