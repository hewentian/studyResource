package com.hewentian.jdk.c04.s07;

public class SwitchCase {
    public static void main(String[] args) {
        int n = 2;
        int result;

        switch (n + 1) {
            case 1:
                System.out.println("Block A");
                result = n;
                break;
            case 2:
                System.out.println("Block B");
                result = n * n;
                break;
            case 3:
                System.out.println("Block C");
                result = n * n * n;
                break;
            case 4:
                System.out.println("Block D");
                result = n % 4;
                break;
            default:
                result = 0;
        }

        System.out.println("n = " + n + " result = " + result);
    }
}
