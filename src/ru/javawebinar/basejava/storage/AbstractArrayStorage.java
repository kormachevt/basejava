package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

/**
 * Array based storageArrayStorage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
            return null;
        }
        return storage[i];
    }

    protected abstract int getIndex(String uuid);
}
