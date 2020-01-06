package ru.javawebinar.basejava.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("The Resume [" + uuid + "] is not exist", uuid);
    }
}