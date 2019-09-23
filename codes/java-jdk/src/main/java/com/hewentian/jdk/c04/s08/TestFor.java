package com.hewentian.jdk.c04.s08;

public class TestFor {
    public static void main(String[] args) {
        int n = 5;
        int sum = 0;
        int f = 1;

        for (int i = 1; i <= n; i++) {
            sum = sum + i; // 也可以写成sum+=i;
        }
        System.out.println("n = " + n + ",sum = " + sum);

        // 注意看这个for语句，它的进步部分是空的，将进步放到的程序块中
        sum = 0;
        for (int i = 1; i <= n; ) {
            sum = sum + i;
            i++;
        }
        System.out.println("n = " + n + ",sum = " + sum);

        for (int i = 1; i <= n; i++) {
            f = f * i; // 也可以写成f*=i;
        }
        System.out.println("n = " + n + ",f = " + f);
    }
}
