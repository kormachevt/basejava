package ru.javawebinar.basejava.model;

import java.io.Serializable;
import java.util.Objects;

public class TextSection implements Section, Serializable {
    private static final long serialVersionUID = 1L;

    private final String text;

    public TextSection(String text) {
        this.text = Objects.requireNonNull(text);
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "TextSection{text='" + text + '\'' + '}';
    }
}
