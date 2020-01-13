package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    protected void add(Resume resume, String index) {
        map.put(index, resume);
    }

    @Override
    protected void replace(Resume resume, String index) {
        map.replace(index, resume);
    }

    @Override
    protected Resume retrieve(String index) {
        return map.get(index);
    }

    @Override
    protected void remove(String index) {
        map.remove(index);
    }

    @Override
    protected String getIndex(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValidIndex(String index) {
        return map.containsKey(index);
    }
}