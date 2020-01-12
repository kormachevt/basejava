package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Objects;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {
    @Override
    protected Integer getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }

    @Override
    protected boolean isValidIndex(Integer index) {
        return !Objects.isNull(index);
    }

    @Override
    protected void deleteByIndex(Integer index) {
        storage[index] = storage[size - 1];
    }

    @Override
    protected void insert(Integer index, Resume resume) {
        storage[size] = resume;
    }
}