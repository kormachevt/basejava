package ru.javawebinar.basejava.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("The Resume [" + uuid + "] is already exist", uuid);
    }
}
