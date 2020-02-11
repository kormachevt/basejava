package ru.javawebinar.basejava;

import java.io.File;

public class MainFileRecursion {

    public static void main(String[] args) {
        String path = "./src/ru/javawebinar/basejava/";
        File dir = new File(path);
        printFileNamesInDir(dir);
    }

    public static  void printFileNamesInDir(File dir) {
        if (dir.isDirectory()) {
            System.out.println(dir.getName());
            File[] list = dir.listFiles();
            if (list != null) {
                for (File file : list) {
                    printFileNamesInDir(file);
                }
            }
        } else if (dir.isFile()) {
            printFileName(dir);
        } else {
            throw new RuntimeException("Something went wrong");
        }
    }

    private static void printFileName(File file) {
        System.out.println("    " + file.getName());
    }
}