package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<T> implements Storage {
    private static final Comparator<Resume> FULL_NAME_RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName)
            .thenComparing(Resume::getUuid);

    protected abstract void doSave(Resume resume, T searchKey);

    protected abstract void doUpdate(Resume resume, T searchKey);

    protected abstract Resume doGet(T searchKey);

    protected abstract void doDelete(T searchKey);

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T searchKey);

    protected abstract List<Resume> asList();

    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        T searchKey = checkResumeNotExist(uuid);
        doSave(resume, searchKey);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        T searchKey = checkResumeExist(uuid);
        doUpdate(resume, searchKey);
    }

    @Override
    public Resume get(String uuid) {
        T searchKey = checkResumeExist(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        T searchKey = checkResumeExist(uuid);
        doDelete(searchKey);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumesList = asList();
        resumesList.sort(FULL_NAME_RESUME_COMPARATOR);
        return resumesList;
    }

    private T checkResumeNotExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    private T checkResumeExist(String uuid) {
        T searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }
}
