package ru.javawebinar.basejava.model;

import java.util.ArrayList;
import java.util.List;

public class ListSection extends Section {
    private List<String> content;

    public ListSection(SectionType type, List<String> list) {
        super(type);
        this.content = list;
    }

    public List<String> getContent() {
        return new ArrayList<>(content);
    }

    public void setContent(List<String> content) {
        this.content = content;
    }
}
