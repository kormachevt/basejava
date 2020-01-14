package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        T searchKey = checkResumeNotExist(uuid);
        add(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        T searchKey = checkResumeExist(uuid);
        replace(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        T searchKey = checkResumeExist(uuid);
        return retrieve(searchKey);
    }

    @Override
    public void delete(String uuid) {
        T searchKey = checkResumeExist(uuid);
        remove(searchKey);
    }

    private T checkResumeNotExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isValidSearchKey(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private T checkResumeExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isValidSearchKey(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void add(Resume resume, T searchKey);

    protected abstract void replace(Resume resume, T searchKey);

    protected abstract Resume retrieve(T searchKey);

    protected abstract void remove(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isValidSearchKey(T searchKey);
}
