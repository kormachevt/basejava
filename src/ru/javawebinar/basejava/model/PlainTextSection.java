package ru.javawebinar.basejava.model;

public class PlainTextSection extends Section {
    private String content;

    public PlainTextSection(SectionType type, String plainText) {
        super(type);
        this.content = plainText;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
