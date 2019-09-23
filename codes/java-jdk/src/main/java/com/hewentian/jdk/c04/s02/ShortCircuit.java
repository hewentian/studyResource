package com.hewentian.jdk.c04.s02;

public class ShortCircuit {
    public static void main(String[] args) {
        ShortCircuit sc = new ShortCircuit();

        System.out.println("短路或运算");
        System.out.println(sc.ma() || sc.mb() || sc.mc());

        System.out.println("短路与运算");
        System.out.println(sc.ma() && sc.mb() && sc.mc());
    }

    boolean ma() {
        System.out.println("ma()被调用！");
        return 1 < 2; // ture
    }

    boolean mb() {
        System.out.println("mb()被调用！");
        return 1 == 2; // false
    }

    boolean mc() {
        System.out.println("mc()被调用！");
        return 1 > 2; // false
    }
}
