package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (isFullStorage()) {
            System.out.println("ERROR: The Resume storage is full");
            return;
        } else if (isInStorage(resume)) {
            System.out.println("ERROR: This Resume is already stored");
            return;
        } else if (isUuidNull(resume)) {
            System.out.println("ERROR: Resumes without uuid not allowed!");
            return;
        }
        storage[size] = resume;
        size++;
    }

    public void update(Resume resume) {
        String uuid = resume.getUuid();
        int i = getIndex(uuid);
        if (isUuidNull(resume)) {
            System.out.println("ERROR: Resumes without uuid not allowed!");
            return;
        } else if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
            return;
        }
        storage[i] = resume;
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
            return;
        }
        System.arraycopy(storage, i + 1, storage, i, size - i - 1);
        size--;
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

    private boolean isInStorage(Resume resume) {
        return (getIndex(resume.getUuid()) >= 0);
    }

    private boolean isFullStorage() {
        return (size() == storage.length);
    }

    private boolean isUuidNull(Resume resume) {
        return (resume.getUuid() == null);
    }
}
