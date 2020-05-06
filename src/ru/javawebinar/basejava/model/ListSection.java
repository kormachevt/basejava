package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<String> list;

    public ListSection(List<String> list) {
        this.list = Objects.requireNonNull(list);
    }

    public ListSection() {
    }

    public List<String> getList() {
        return list;
    }

    public void addItem(String item) {
        list.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return "ListSection{list=" + list + '}';
    }
}
