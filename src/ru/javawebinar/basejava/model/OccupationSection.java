package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OccupationSection implements Section {
    private final List<Occupation> list;

    public OccupationSection(List<Occupation> list) {
        this.list = Objects.requireNonNull(list);
    }

    public List<Occupation> getList() {
        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OccupationSection that = (OccupationSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "OccupationSection{list=" + list + '}';
    }
}