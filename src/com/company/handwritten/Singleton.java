package com.company.handwritten;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Singleton {
    private static volatile Singleton instance = null;

    private Singleton() {};

    public static Singleton getInstance() {

        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }

        }
        return instance;
    }
}

class Test {
    public static void main(String[] args) {

        Runnable r = () -> System.out.println(Singleton.getInstance());

        ExecutorService executorService = Executors.newFixedThreadPool(40);

        for (int i = 0; i < 100; i++) {
            executorService.submit(r);
        }
        executorService.shutdown();
    }
}
