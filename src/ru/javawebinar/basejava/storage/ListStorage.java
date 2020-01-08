package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


public class ListStorage extends AbstractStorage {
    private List<Resume> list = new ArrayList<>();

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Resume[] getAll() {
        return list.toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    protected void add(Resume resume, Integer index) {
        list.add(resume);
    }

    @Override
    protected void replace(Resume resume, Integer index) {
        list.set(index, resume);
    }

    @Override
    protected Resume retrieve(Integer index) {
        return list.get(index);
    }

    @Override
    protected void remove(Integer index) {
        int i = index;
        list.remove(i);
    }

    @Override
    protected Integer getIndex(String uuid) {
        ListIterator<Resume> iterator = list.listIterator();
        while (iterator.hasNext()) {
            Resume resume = iterator.next();
            if (resume.getUuid().equals(uuid)) {
                return iterator.nextIndex() - 1;
            }
        }
        return null;
    }
}
