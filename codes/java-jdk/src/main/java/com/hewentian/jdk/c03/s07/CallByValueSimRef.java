package com.hewentian.jdk.c03.s07;

import java.util.Date;

public class CallByValueSimRef {
    public static void main(String args[]) {
        // 测试简单类型是否会被改变，不会
        int i = 10;
        modifyI(i);
        System.out.println(i);

        // 测试日期是否会被改变，这里不会被改变。下面学生类中的属性就会被改变。
        Date date = new Date();
        System.out.println("before invoke modify() date = " + date);

        modifyDate(date);
        System.out.println("after invoke modify() date = " + date);

        // 测试类对象（引用类型）是否会被改变，会被改变
        Student student = new Student();
        student.setName("hewen");
        student.setAge(20);
        student.setBirthDate(new Date());

        modifyStudent(student);
        System.out.println(student.getName());
        System.out.println(student.getAge());
        System.out.println(student.getBirthDate());
    }

    public static void modifyI(int i) {
        i = 20;
    }

    public static void modifyDate(Date date) {
        date = null;
    }

    public static void modifyStudent(Student s) {
        s.setName("tian");
        s.setAge(23);
        s.setBirthDate(null);
    }
}
