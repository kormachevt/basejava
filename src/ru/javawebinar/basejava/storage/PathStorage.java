package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public class PathStorage extends AbstractPathStorage {
    private SerializationContext context = new SerializationContext();

    public PathStorage(String dir, SerializationStrategy serializationStrategy) {
        super(dir);
        this.context.setStrategy(serializationStrategy);
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) {
        this.context.write(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) {
        return this.context.read(is);
    }
}
