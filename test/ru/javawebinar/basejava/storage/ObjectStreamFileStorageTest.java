package ru.javawebinar.basejava.storage;


import ru.javawebinar.basejava.serialization.ObjectStreamSerializationStrategy;

public class ObjectStreamFileStorageTest extends AbstractStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializationStrategy()));
    }
}