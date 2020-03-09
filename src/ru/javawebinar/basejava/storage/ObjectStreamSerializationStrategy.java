package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.io.*;

public class ObjectStreamSerializationStrategy implements SerializationStrategy {
    @Override
    public void write(Resume resume, OutputStream os) {
        try(ObjectOutputStream oos = new ObjectOutputStream(os)){
            oos.writeObject(resume);
        } catch (IOException e) {
            throw new StorageException("Error write resume", null, e);
        }
    }

    @Override
    public Resume read(InputStream is) {
        try(ObjectInputStream ois = new ObjectInputStream(is)){
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
