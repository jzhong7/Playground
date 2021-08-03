package com.company;

public class AddFraction {
    public static int gcd(int a, int b) {
        return a == 0 ? b : gcd(b%a, a);
    }

    public static int lcm(int a, int b) {
        // LCM * GCD == a * b
        return a * b / gcd(a,b);
    }

    public static void addFraction(int n1, int d1, int n2, int d2) {
        if (d1 == 0 || d2 == 0) throw new IllegalArgumentException("CAN NOT BE 0");

        int lcm = lcm(d1, d2);
        int n = lcm / d1 * n1 + lcm / d2 * n2;

        int commonFact = gcd(n, lcm);

        n /= commonFact;
        lcm /= commonFact;
        System.out.printf("%d / %d + %d / %d = %d / %d", n1, d1, n2, d2, n, lcm);
    }

    public static void main(String[] args) {
        addFraction(1, 0, 3, 15);
    }
}
