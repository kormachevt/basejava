package ru.javawebinar.basejava.util;

import java.io.IOException;

@FunctionalInterface
public interface ThrowingRunnable<R> {

    R run() throws IOException;
}