package com.hewentian.jdk.c04.s11;

public class BreakLabel {
    public static void main(String args[]) throws Exception {
        outer:
        for (int i = 0; i < 10; i++) {
            System.out.println("Outer loop, i = " + i);
            inner:
            while (true) {
                int k = System.in.read();
                System.out.println("Inner Loop: " + k);

                if (k == 'b') {
                    break inner;
                }
                if (k == 'q') {
                    break outer;
                }
            }
        }
    }
}
