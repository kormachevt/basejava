package ru.javawebinar.basejava.util;

public class StringUtils {

    public static boolean isNotEmptyString(String str) {
        return str != null && str.trim().length() != 0;
    }
}
