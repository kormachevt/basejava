package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OccupationSection implements Section {
    private final List<Organization> list;

    public OccupationSection(List<Organization> list) {
        this.list = Objects.requireNonNull(list);
    }

    public List<Organization> getList() {
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