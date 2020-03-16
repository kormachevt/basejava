package ru.javawebinar.basejava.util;

import java.io.IOException;

public interface ThrowingConsumer<T> {

    void accept(T t) throws IOException;
}