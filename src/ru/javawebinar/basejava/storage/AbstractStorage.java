package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage<T> implements Storage {
    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        T index = checkResumeNotExist(uuid);
        add(resume, index);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        T index = checkResumeExist(uuid);
        replace(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        T index = checkResumeExist(uuid);
        return retrieve(index);
    }

    @Override
    public void delete(String uuid) {
        T index = checkResumeExist(uuid);
        remove(index);
    }

    private T checkResumeNotExist(String uuid) {
        T index = getIndex(uuid);
        if (isValidIndex(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private T checkResumeExist(String uuid) {
        T index = getIndex(uuid);
        if (!isValidIndex(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void add(Resume resume, T index);

    protected abstract void replace(Resume resume, T index);

    protected abstract Resume retrieve(T index);

    protected abstract void remove(T index);

    protected abstract T getIndex(String uuid);

    protected abstract boolean isValidIndex(T index);
}
