package com.company;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Time {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        boolean repeat = true;

        try {
            do {
                System.out.println("Enter a positive integer: ");
                String s = sc.next();

                if (!"q".equals(s)) {
                    int x = Integer.parseInt(s);

                    if (x <= 0) {
                        System.out.println("Error! Your number should be greater than 0!");
                    } else {
                        for (int i = 1; i <= x; i++) {
                            String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                            System.out.println(timeStamp);
                        }
                    }
                } else {
                    repeat = false;
                }
            } while (repeat);

        } catch (Exception e) {
            System.out.println("Invalid input! Integer ONLY!");
        }

        System.out.println("Exit!");
        sc.close();
        System.exit(0);
    }

}
