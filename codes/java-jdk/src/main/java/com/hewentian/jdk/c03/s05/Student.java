package com.hewentian.jdk.c03.s05;

public class Student {
    String name;
    String sex;
    int grade;
    int age;

    // 定义一个构造器
    public Student(String name, String sex, int grade, int age) {
        this.name = name;
        this.sex = sex;
        this.grade = grade;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
