package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.exception.StorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {
    protected static final int STORAGE_LIMIT = 10_000;
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String NOT_EXISTED_UUID = UUID.randomUUID().toString();
    private Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void clearEmptyStorage() {
        storage.clear();
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertSame(resume, storage.get("uuid4"));
    }

    @Test(expected = StorageException.class)
    public void saveStorageOverflow() {
        try {
            for (int i = storage.size(); i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("The Storage should not be overflowed yet");
        }
        storage.save(new Resume());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume resume = new Resume(UUID_1);
        storage.save(resume);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1);
        assertNotSame(resume, storage.get(UUID_1));
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(NOT_EXISTED_UUID);
        storage.update(resume);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        assertEquals(new Resume(UUID_1), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(NOT_EXISTED_UUID);
    }

    @Test
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        assertEquals(new Resume(UUID_1), storage.get(UUID_1));
        assertEquals(new Resume(UUID_3), storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOT_EXISTED_UUID);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExistWhileEmpty() {
        storage.clear();
        storage.delete(NOT_EXISTED_UUID);
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(storage.size(), resumes.length);
        for (Resume resume : resumes) {
            assertNotNull(resume);
        }
    }

    @Test
    public void getAllWhileEmpty() {
        storage.clear();
        Resume[] resumes = storage.getAll();
        assertEquals(0, resumes.length);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}