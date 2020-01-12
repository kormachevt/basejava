package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.exception.ExistStorageException;
import ru.javawebinar.basejava.exception.NotExistStorageException;
import ru.javawebinar.basejava.model.Resume;

public abstract class AbstractStorage implements Storage {
    @Override
    public void save(Resume resume) {
        String uuid = resume.getUuid();
        Integer index = checkResumeNotExist(uuid);
        add(resume, index);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        Integer index = checkResumeExist(uuid);
        replace(resume, index);
    }

    @Override
    public Resume get(String uuid) {
        Integer index = checkResumeExist(uuid);
        return retrieve(index);
    }

    @Override
    public void delete(String uuid) {
        Integer index = checkResumeExist(uuid);
        remove(index);
    }

    /**
     * Method for checking if the Resume with uuid IS NOT in the storage.
     *
     * @param uuid identifier of the Resume
     * @return - index of the Resume if its in the storage, otherwise null or -1 depends on the impl
     */
    protected Integer checkResumeNotExist(String uuid) {
        Integer index = getIndex(uuid);
        if (isValidIndex(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    /**
     * Method for checking if the Resume with uuid IS in the storage already.
     *
     * @param uuid identifier of the Resume
     * @return - index of the Resume if its in the storage, otherwise null or -1 depends on the impl
     */
    protected Integer checkResumeExist(String uuid) {
        Integer index = getIndex(uuid);
        if (!isValidIndex(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract void add(Resume resume, Integer index);

    protected abstract void replace(Resume resume, Integer index);

    protected abstract Resume retrieve(Integer index);

    protected abstract void remove(Integer index);

    protected abstract Integer getIndex(String uuid);

    protected abstract boolean isValidIndex(Integer index);
}
