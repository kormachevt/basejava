package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

/**
 * Array based storageArrayStorage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
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
        int index = getIndex(resume.getUuid());
        if (size() == STORAGE_LIMIT) {
            System.out.println("ERROR: The Resume storage is full");
        } else if (index >= 0) {
            System.out.println("ERROR: This Resume is already stored");
        } else {
            insert(index, resume);
            size++;
        }
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            storage[index] = resume;
        }
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            deleteByIndex(index);
            storage[size - 1] = null;
            size--;
        }
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

    protected abstract int getIndex(String uuid);

    protected abstract void deleteByIndex(int index);

    protected abstract void insert(int index, Resume resume);
}
