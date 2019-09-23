package com.hewentian.jdk.c04.s04;

public class Implicit {
    public static void main(String[] args) {
        short s = 10;
        long l = 100;
        System.out.println(l * s);// 将会得到一个long类型的数值

        byte b = 2;
        char c = 'a';
        int d = b + c;
        System.out.println(d);

        // byte f=b+d;//将会报错，因为计算得出的结果应该是int类型
        int i = 1234567809;
        float f = i;// 将会损失精度，得到的结果是1.23456781E9
        System.out.println(f);
    }
}

