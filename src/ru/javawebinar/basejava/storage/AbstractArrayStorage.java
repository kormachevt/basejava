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

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        int i = getIndex(resume.getUuid());
        if (size() == STORAGE_LIMIT) {
            System.out.println("ERROR: The Resume storage is full");
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("ERROR: This Resume is already stored");
        } else {
            insert(i, resume);
            size++;
        }
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            storage[i] = resume;
        }
    }

    public Resume get(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
            return null;
        }
        return storage[i];
    }

    @Override
    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            replace(i);
            storage[size - 1] = null;
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    public int size() {
        return size;
    }

    protected abstract int getIndex(String uuid);

    protected abstract void replace(int i);

    protected abstract void insert(int i, Resume resume);
}
