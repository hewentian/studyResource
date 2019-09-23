package com.hewentian.jdk.c02.s05;

public class Student {
    private int age;

    public void setAge(int age) {
        if (age > 100) {
            this.age = 100;
        } else if (age < 10) {
            this.age = 10;
        } else {
            this.age = age;
        }
    }

    public int getAge() {
        return this.age;
    }
}
