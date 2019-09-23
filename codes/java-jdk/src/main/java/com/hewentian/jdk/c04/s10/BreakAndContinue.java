package com.hewentian.jdk.c04.s10;

public class BreakAndContinue {
    public static void main(String[] args) {
        int n = 4;
        int sum1 = 0;
        int sum2 = 0;

        // Break
        for (int i = 1; i <= n; i++) {
            sum1 = sum1 + i;
            if (i % 2 == 0) {
                break;
            }
        }
        System.out.println(sum1);

        // continue
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {
                continue;
            }
            sum2 = sum2 + i;
        }
        System.out.println(sum2);
    }
}
