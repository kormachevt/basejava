package ru.javawebinar.basejava.storage;


import org.junit.Test;
import ru.javawebinar.basejava.model.Resume;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class MapStorageTest extends AbstractStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Test
    @Override
    public void getAll() {
        Resume[] expectedResumes = {RESUME_1, RESUME_2, RESUME_3};
        Resume[] actualResumes = storage.getAll();
        HashSet<Resume> expectedResumesSet = new HashSet<>(Arrays.asList(expectedResumes));
        HashSet<Resume> actualResumesSet = new HashSet<>(Arrays.asList(actualResumes));
        assertEquals(expectedResumesSet, actualResumesSet);
    }
}