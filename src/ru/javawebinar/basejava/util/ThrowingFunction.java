package ru.javawebinar.basejava.util;

import java.io.DataInputStream;
import java.io.IOException;

@FunctionalInterface
public interface ThrowingFunction<R> {

    R apply(DataInputStream dis) throws IOException;
}