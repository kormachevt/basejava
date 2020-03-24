package ru.javawebinar.basejava;


public class MainDeadlock {
    public static final int THREADS_NUMBER = 1000;

    public static void main(String[] args) {

        String shortString = "abc";
        String longString = "ABCabc";

        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    if (Math.random() > 0.5) {
                        compare(shortString, longString);
                    } else {
                        compare(longString, shortString);
                    }
                }
            });
            thread.start();
        }
    }

    public static int compare(String alfa, String bravo) {
        synchronized (alfa) {
            synchronized (bravo) {
                return Integer.compare(alfa.length(), bravo.length());
            }
        }
    }
}