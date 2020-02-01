package ru.javawebinar.basejava.model;

import java.util.List;
import java.util.Objects;

public class OccupationSection implements Section {
    private List<Occupation> list;

    public OccupationSection(List<Occupation> list) {
        this.list = list;
    }

    public List<Occupation> getList() {
        return list;
    }

    public void setList(List<Occupation> list) {
        this.list = list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OccupationSection that = (OccupationSection) o;
        return Objects.equals(list,
                              that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "OccupationSection{" +
                "list=" + list +
                '}';
    }
}