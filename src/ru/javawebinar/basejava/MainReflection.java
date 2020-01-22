package ru.javawebinar.basejava;

import ru.javawebinar.basejava.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume(UUID.randomUUID().toString(), UUID.randomUUID().toString());
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        field.set(r, "new_uuid");
        Method resumeToString = r.getClass().getMethod("toString");
        System.out.println("resumeToString: " + resumeToString.invoke(r));
    }
}