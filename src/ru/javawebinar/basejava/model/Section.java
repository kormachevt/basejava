package ru.javawebinar.basejava.model;

import java.util.List;

public class Section {
    private SectionType type;
    private String plainTextContent;
    private List<String> listContent;
    private List<Occupation> occupations;

    public Section(SectionType type, String plainTextContent, List<String> listContent, List<Occupation> occupations) {
        this.type = type;
        this.plainTextContent = plainTextContent;
        this.listContent = listContent;
        this.occupations = occupations;
    }
}
