package com.hewentian.jdk.c04.s09;

public class WhileAndDo {
    // 对比while和do...while的区别
    public static void main(String[] args) {
        int n = 0;
        int result1 = 0;
        int result2 = 0;

        // while，可能一次也不执行
        int i = 1;
        while (i <= n) {
            result1 = result1 + i;
            i = i + 1;
        }
        System.out.println("After the While Loop,result1 is " + result1);

        // do...while，至少执行一次
        int j = 1;
        do {
            result2 = result2 + j;
            j = j + 1;
        } while (j <= n);
        System.out.println("After the Do Loop,result2 is " + result2);
    }
}
