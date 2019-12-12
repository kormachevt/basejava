package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        int i = getIndex(resume.getUuid());
        if (size() == STORAGE_LIMIT) {
            System.out.println("ERROR: The Resume storage is full");
        } else if (i >= 0) {
            System.out.println("ERROR: This Resume is already stored");
        } else {
            int insertIndex = -1 * (i + 1);
            System.arraycopy(storage, insertIndex, storage, insertIndex + 1, size - insertIndex);
            storage[insertIndex] = resume;
            size++;
        }
    }

    @Override
    public void delete(String uuid) {
        int i = getIndex(uuid);
        if (i < 0) {
            System.out.println(String.format("ERROR: No Resume with [uuid: %s] was found", uuid));
        } else {
            System.arraycopy(storage, i + 1, storage, i, size - i - 1);
            storage[size - 1] = null;
            size--;
        }
    }

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }
}
