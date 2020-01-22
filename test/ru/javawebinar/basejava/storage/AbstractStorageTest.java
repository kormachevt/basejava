package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public abstract class AbstractStorageTest {
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NOT_EXISTED_UUID = UUID.randomUUID().toString();
    private static final String FULL_NAME_1 = "c";
    private static final String FULL_NAME_2 = "b";
    private static final String FULL_NAME_3 = "a";
    private static final String FULL_NAME_4 = "b";
    private static final String NOT_EXISTED_FULL_NAME = UUID.randomUUID().toString();
    protected static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    protected static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    protected static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    protected static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);
    private static final Resume RESUME_NOT_EXISTED = new Resume(NOT_EXISTED_UUID, NOT_EXISTED_FULL_NAME);

    protected Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        Resume resume = RESUME_4;
        storage.save(resume);
        assertEquals(4, storage.size());
        assertSame(resume, storage.get(UUID_4));
    }


    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, FULL_NAME_1);
        storage.update(resume);
        assertSame(resume, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_NOT_EXISTED);
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        assertEquals(RESUME_1, resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(NOT_EXISTED_UUID);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
        storage.get(UUID_2);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOT_EXISTED_UUID);
    }

    @Test
    public void getAllSorted() {
        storage.save(RESUME_4);
        Resume[] expectedResumesArray = {RESUME_3, RESUME_2, RESUME_4, RESUME_1};
        List<Resume> actualResumesList = storage.getAllSorted();
        Resume[] actualResumesArray = actualResumesList.toArray(new Resume[0]);
        assertArrayEquals(expectedResumesArray, actualResumesArray);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }
}