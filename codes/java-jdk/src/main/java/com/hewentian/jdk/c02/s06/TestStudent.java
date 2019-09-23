package com.hewentian.jdk.c02.s06;

import com.hewentian.jdk.c02.s06.Student;

public class TestStudent {
    public static void main(String[] args) {
        Student student = new Student();
        student.setStudentId("001");

        System.out.println(student.getStudentId());
    }
}
