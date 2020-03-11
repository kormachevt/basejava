package ru.javawebinar.basejava.serializer;

import ru.javawebinar.basejava.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {
    void write(Resume resume, OutputStream os) throws IOException;

    Resume read(InputStream is) throws IOException;
}