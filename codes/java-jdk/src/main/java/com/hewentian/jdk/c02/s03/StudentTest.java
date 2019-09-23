package com.hewentian.jdk.c02.s03;

class Student {
    private String studentId;
    private String name;
    private String sex;
    private int grade;
    private int age;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

public class StudentTest {
    public static void main(String[] args) {
        Student s = new Student();
        s.setAge(18);
        System.out.println("age is: " + s.getAge());
    }
}
