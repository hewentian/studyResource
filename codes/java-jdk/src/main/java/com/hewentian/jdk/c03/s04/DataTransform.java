package com.hewentian.jdk.c03.s04;

public class DataTransform {
    public static void main(String[] args) {
        // int类型数据将会自动转换成double类型
        int i1 = 123;
        double d1 = i1;
        System.out.println("d1 = " + d1);

        // double 类型数据转换成int时，将会损失精度
        double d2 = 1.234;
        int i2 = (int) d2;
        System.out.println("i2 = " + i2);
    }
}
