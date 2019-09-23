package com.hewentian.jdk.c04.s06;

public class IfTest {
    public static void main(String[] args) {
        int i = 13;

        if (i < 50) {
            System.out.println("The input number is less than 50!");
        } else if (i == 50) {
            System.out.println("The input number is equal to 50!");
        } else {
            System.out.println("The input nember is greater than 50!");
        }
    }
}
