package ru.javawebinar.basejava.util;

import java.io.DataOutputStream;
import java.io.IOException;

public interface ThrowingBiConsumer<T> {

    void accept(T t, DataOutputStream u) throws IOException;
}