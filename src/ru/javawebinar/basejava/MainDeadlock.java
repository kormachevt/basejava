package ru.javawebinar.basejava;


public class MainDeadlock {
    final A a = new A();
    final B b = new B();


    public static void main(String[] args) {
        MainDeadlock mainDeadlock = new MainDeadlock();

        Thread thread1 = new Thread(mainDeadlock::add);

        Thread thread2 = new Thread(mainDeadlock::subtract);

        thread1.start();
        thread2.start();
    }

    public int add() {
        synchronized (a) {
            System.out.println("add got a");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            synchronized (b) {
                System.out.println("add got b");
                return a.i + b.i;
            }
        }

    }

    public int subtract() {
        synchronized (b) {
            System.out.println("subtract got b");
            synchronized (a) {
                System.out.println("subtract got a");
                return a.i - b.i;
            }
        }
    }

    private class B {
        private int i = 20;
    }

    private class A {
        private int i = 10;
    }
}