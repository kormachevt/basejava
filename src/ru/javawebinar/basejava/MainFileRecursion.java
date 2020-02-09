package ru.javawebinar.basejava;

import java.io.File;

public class MainFileRecursion {

    public static void main(String[] args) {
        printFileNamesInDir("./src/ru/javawebinar/basejava/");
    }

    public static  void printFileNamesInDir(String basePath) {
        File base = new File(basePath);
        if (base.isDirectory()) {
            System.out.println(base.getName());
            String[] list = base.list();
            if (list != null) {
                for (String name : list) {
                    printFileNamesInDir(basePath + name + "/");
                }
            }
        } else if (base.isFile()) {
            printFileName(base);
        } else {
            throw new RuntimeException("Something went wrong");
        }
    }

    private static void printFileName(File file) {
        System.out.println("    " + file.getName());
    }
}