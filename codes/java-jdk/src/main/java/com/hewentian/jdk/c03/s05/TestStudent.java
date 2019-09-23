package com.hewentian.jdk.c03.s05;

public class TestStudent {
    public static void main(String[] args) {
        Student s1;
        s1 = new Student("Lisa", "Male", 1, 18);

        Student s2 = s1;
        s1.setName("Sophie");

        System.out.println("学生 s1 姓名：" + s1.getName()); // 学生 s1 姓名：Sophie
        System.out.println("学生 s2 姓名：" + s2.getName()); // 学生 s2 姓名：Sophie
    }
}
