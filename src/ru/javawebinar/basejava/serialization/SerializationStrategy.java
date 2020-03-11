package ru.javawebinar.basejava.serialization;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializationStrategy {
    void write(Resume resume, OutputStream os) throws IOException;

    Resume read(InputStream is) throws IOException;
}