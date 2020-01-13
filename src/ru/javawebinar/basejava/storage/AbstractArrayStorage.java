package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storageArrayStorage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    protected void add(Resume resume, Integer index) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("The Resume storage is full", resume.getUuid());
        }
        insert(index, resume);
        size++;
    }

    @Override
    protected void replace(Resume resume, Integer index) {
        storage[index] = resume;
    }

    @Override
    protected Resume retrieve(Integer index) {
        return storage[index];
    }

    @Override
    protected void remove(Integer index) {
        deleteByIndex(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    protected boolean isValidIndex(Integer index) {
        return index >= 0;
    }

    protected abstract void deleteByIndex(Integer index);

    protected abstract void insert(Integer index, Resume resume);
}
