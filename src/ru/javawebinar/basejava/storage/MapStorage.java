package ru.javawebinar.basejava.storage;

import ru.javawebinar.basejava.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
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
    protected void add(Resume resume, Integer index) {
        map.put(resume.getUuid(), resume);
    }

    @Override
    protected void replace(Resume resume, Integer index) {
        map.replace(resume.getUuid(), resume);
    }

    @Override
    protected Resume retrieve(Integer index) {
        String[] keyArray = map.keySet().toArray(new String[0]);
        return map.get(keyArray[index]);
    }

    @Override
    protected void remove(Integer index) {
        String[] keyArray = map.keySet().toArray(new String[0]);
        map.remove(keyArray[index]);
    }

    @Override
    protected Integer getIndex(String uuid) {
        String[] keyArray = map.keySet().toArray(new String[0]);
        for (int i = 0; i < keyArray.length; i++) {
            if (keyArray[i].equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isValidIndex(Integer index) {
        return index >= 0;
    }
}