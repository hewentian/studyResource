package com.hewentian.jdk.c04.s11;

public class ContinueLabel {
    public static void main(String[] args) {
        String searchMe = "Look for a substring in me";
        String substring = "subs";
        boolean foundIt = false;

        int max = searchMe.length() - substring.length();

        test:
        for (int i = 0; i <= max; i++) {
            int n = substring.length();
            int j = i;
            int k = 0;

            while (n-- != 0) {
                if (searchMe.charAt(j++) != substring.charAt(k++)) {
                    continue test;
                }
            }

            foundIt = true;
            break test;
        }
        System.out.println(foundIt ? "Fould it" : "Didn't find it");
    }
}
