package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Objects;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Integer index = getIndex(uuid);
        checkResumeNotExist(uuid, index);
        add(resume, index);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Integer index = getIndex(uuid);
        checkResumeExist(uuid, index);
        replace(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Integer index = getIndex(uuid);
        checkResumeExist(uuid, index);
        return retrieve(index);
    }

    @Override
    public void delete(String uuid) {
        Integer index = getIndex(uuid);
        checkResumeExist(uuid, index);
        remove(index);
    }

    protected void checkResumeNotExist(String uuid, Integer index) {
        if (!Objects.isNull(index)) {
            throw new ExistStorageException(uuid);
        }
    }

    protected void checkResumeExist(String uuid, Integer index) {
        if (Objects.isNull(index)) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract void add(Resume resume, Integer index);

    protected abstract void replace(Resume resume, Integer index);

    protected abstract Resume retrieve(Integer index);

    protected abstract void remove(Integer index);

    protected abstract Integer getIndex(String uuid);
}
