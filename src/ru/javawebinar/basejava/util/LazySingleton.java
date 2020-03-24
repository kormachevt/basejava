package ru.javawebinar.basejava.util;

public class LazySingleton {
    int i;
    volatile private static LazySingleton INSTANCE;

    private LazySingleton() {

    }
    //Initialization-on-demand holder idiom
    private static class LazySingletonHolder{
        private static final LazySingleton INSTANCE = new LazySingleton();
    }

//    double sin = Math.sin(13.);
    public static LazySingleton getInstance() {
        return LazySingletonHolder.INSTANCE;

//Double checked locking
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    int i = 13;
//                    INSTANCE = new LazySingleton();
//
//                }
//            }
//        }
//        return INSTANCE;
    }
}
