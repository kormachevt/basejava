package ru.javawebinar.basejava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFileRecursion {

    public static void main(String[] args) {
        String dir = "./src";
        Path path = Paths.get(dir);
        StringBuffer sb = new StringBuffer();
        printFileNamesInDir(path, sb);
    }

    public static void printFileNamesInDir(Path path, StringBuffer sb){
        String offset = "   ";
        try {
            Files.list(path).forEachOrdered((path1) -> {
                if(Files.isDirectory(path1)){
                    System.out.println(sb + path1.getFileName().toString());
                    sb.append(offset);
                    printFileNamesInDir(path1, sb);
                } else {
                    System.out.println(sb + path1.getFileName().toString());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.delete(0,offset.length());
    }
}