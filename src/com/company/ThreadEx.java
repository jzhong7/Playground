package com.company;

class A extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            try {
                // thread to sleep for 100 milliseconds
                Thread.sleep(100);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            System.out.printf("Thread A %d %s in control %n", i, Thread.currentThread().getName());
        }
    }
}

class B extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 4; i++) {
            try {
                // thread to sleep for 100 milliseconds
                Thread.sleep(100);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            System.out.printf("Thread B %d %s in control %n", i, Thread.currentThread().getName());
        }
    }
}
public class ThreadEx {

    public static void main(String args[]) {
        A a1 = new A();
        A a2 = new A();
        B b1 = new B();


        a1.start();
        System.out.println(a1.getName() + " alive? " + a1.isAlive());
        try {
            // starts second thread after when
            // first thread a1 is died.
            // after thread a1 execution finished
            // then b1 thread start
            a1.join();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(a1.getName() + " alive? " + a1.isAlive());

        b1.start();
        for (int i = 1; i <= 4; i++) {
            try {
                // thread to sleep for 100 milliseconds
                Thread.sleep(100);
            }
            catch (Exception e) {
                System.out.println(e);
            }
            // running state to runnable state
            Thread.yield();

            System.out.println(Thread.currentThread().getName()
                    + " in control");
        }
    }
}
