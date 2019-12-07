package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    public void save(Resume resume) {
        if (resume.getUuid() != null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("Empty resumes are not allowed!");
        }
    }

    public Resume get(String uuid) {
        int index = indexByUuid(uuid);
        if (index >= 0) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = indexByUuid(uuid);
        int leftover = size - index - 1;
        if (index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, leftover);
            size--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        System.arraycopy(storage, 0, resumes, 0, size);
        return resumes;
    }

    public int size() {
        return size;
    }

    private int indexByUuid(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        System.out.println(String.format("No com.urise.webapp.model.Resume with [uuid: %s] was found", uuid));
        return -1;
    }
}
