package com.hewentian.jdk.c04.s05;

public class StringPlus {
    public static void main(String[] args) {
        double x = 9.987;
        double y = 1; // 自动将int型的数值1提升到double类型1.0
        double t = x + y;

        String s = "Price is " + x; // 得到一个字符串:"Price is 9.987"
        String st = "Total Price is " + t; // 得到一个字符串："Total Price is 10.987"

        System.out.println(s);
        System.out.println(st);
        System.out.println("" + x + y); // 打印出一个字符串："9.9871.0"
        System.out.println(x + y + ""); // 打印出一个字符串："10.987"
    }
}
