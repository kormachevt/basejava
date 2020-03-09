package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public class SerializationContext {
    private SerializationStrategy strategy;

    public void setStrategy(SerializationStrategy strategy) {
        this.strategy = strategy;
    }

    public void write(Resume resume, OutputStream os) {
        this.strategy.write(resume, os);
    }

    public Resume read(InputStream is) {
        return this.strategy.read(is);
    }
}