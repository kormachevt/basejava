package ru.javawebinar.basejava;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) {
        String filePath = "./.gitignore";

        File file = new File(filePath);
//        File file = new File("/home/wardruid/IdeaProjects/basejava/.gitignore");
        try {
            System.out.println(file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException("Error", e);
        }
        File dir = new File("./src/ru/javawebinar/basejava/");
        System.out.println(dir.isDirectory());
        String[] list = dir.list();
        if (list != null) {
            for (String name : list) {
//                System.out.println(name);
            }
        }
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
            System.out.println(fis.read());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
