package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storageArrayStorage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        if (size() == STORAGE_LIMIT) {
            throw new StorageException("The Resume storage is full", resume.getUuid());
        }
        super.save(resume);
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
    protected void add(Resume resume, int index) {
        insert(index, resume);
        size++;
    }

    @Override
    protected void replace(Resume resume, int index) {
        storage[index] = resume;
    }

    @Override
    protected Resume retrieve(int index) {
        return storage[index];
    }

    @Override
    protected void remove(int index) {
        deleteByIndex(index);
        storage[size - 1] = null;
        size--;
    }

    protected abstract void deleteByIndex(int index);

    protected abstract void insert(int index, Resume resume);
}
