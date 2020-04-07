package ru.javawebinar.basejava.storage;

import org.junit.Before;
import org.junit.Test;
import ru.javawebinar.basejava.Config;
import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static ru.javawebinar.basejava.storage.ResumeTestData.*;

public abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = Config.get().getStorageDir();

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
        assertEquals(resume, storage.get(UUID_4));
    }


    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_1);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, FULL_NAME_2);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_1));
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