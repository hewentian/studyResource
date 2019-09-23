package com.hewentian.jdk.c04.s03;

public class Assignment {
    public static void main(String[] args) {
        // 简单数据类型
        int a;
        int b = 100;
        a = b;
        b = 20;
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // 引用数据类型
        Person p1 = new Person(100);
        Person p2 = p1;
        p1.setId(111);

        // p1和p2的id是相等的，都是111
        System.out.println("p1的id = " + p1.getId());
        System.out.println("p2的id = " + p2.getId());
    }
}

class Person {
    private int id;

    public Person(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
