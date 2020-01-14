package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private Map<String, Resume> map = new HashMap<>();

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
    protected void add(Resume resume, String key) {
        map.put(key, resume);
    }

    @Override
    protected void replace(Resume resume, String key) {
        map.replace(key, resume);
    }

    @Override
    protected Resume retrieve(String key) {
        return map.get(key);
    }

    @Override
    protected void remove(String key) {
        map.remove(key);
    }

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isValidSearchKey(String key) {
        return map.containsKey(key);
    }
}