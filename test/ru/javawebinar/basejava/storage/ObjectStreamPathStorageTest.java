package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.serialization.ObjectStreamSerializationStrategy;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}