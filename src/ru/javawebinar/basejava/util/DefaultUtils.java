package ru.javawebinar.basejava.util;

public class DefaultUtils {

    public static <T> T getValueOrDefault(T value, T specialCase, T defaultValue) {
        if(specialCase == null) {
            return value == null ? defaultValue : value;
        } else {
            return value.equals(specialCase) ? defaultValue : value;
        }
    }
}