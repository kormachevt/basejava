package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size() == storage.length) {
            System.out.println("ERROR: The Resume storage is full");
        } else if (getIndex(resume.getUuid()) >= 0) {
            System.out.println("ERROR: This Resume is already stored");
        } else {
            storage[size] = resume;
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

    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            storage[i] = storage[size - 1];
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

    private int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
