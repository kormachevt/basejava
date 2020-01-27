package ru.javawebinar.basejava.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume {

    private final String uuid;
    private String fullName;
    private Map<ContactType, String> contactsByType;
    private Map<SectionType, Section> sectionsByType;

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(fullName);
        this.uuid = uuid;
        this.fullName = fullName;
        this.contactsByType = Collections.emptyMap();
        this.sectionsByType = Collections.emptyMap();
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getContact(ContactType type) {
        Objects.requireNonNull(type);
        return contactsByType.getOrDefault(type, "");
    }

    public Section getSection(SectionType type) {
        Objects.requireNonNull(type);
        return sectionsByType.getOrDefault(type, null);
    }

    public void addContact(ContactType type, String value) {
        Objects.requireNonNull(type);
        contactsByType.put(type, value);
    }

    public void addSection(SectionType type, String plainTextContent) {
        Objects.requireNonNull(type);
        sectionsByType.put(type, new Section(type, plainTextContent, Collections.emptyList(), Collections.emptyList()));
    }

    public void addSection(SectionType type, List<String> listContent) {
        Objects.requireNonNull(type);
        sectionsByType.put(type, new Section(type, "", listContent, Collections.emptyList()));
    }

    public void addSection(SectionType type, List<Occupation> occupations) {
        Objects.requireNonNull(type);
        sectionsByType.put(type, new Section(type, "", Collections.emptyList(), occupations));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;

        if (!Objects.equals(uuid, resume.uuid)) return false;
        return Objects.equals(fullName, resume.fullName);
    }

    @Override
    public int hashCode() {
        int result = uuid != null ? uuid.hashCode() : 0;
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return String.format("{uuid: %s; fullName: %s}", getUuid(), getFullName());
    }
}
