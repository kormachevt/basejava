package ru.javawebinar.basejava.model;

import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume {
    private final String uuid;
    private String fullName;
    private final Map<ContactType, String> contactsByType = new EnumMap<>(ContactType.class);
    private final Map<SectionType, Section> sectionsByType = new EnumMap<>(SectionType.class);

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid);
        this.fullName = Objects.requireNonNull(fullName);
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
        return contactsByType.get(type);
    }

    public void setContact(ContactType type, String value) {
        contactsByType.put(type, value);
    }

    public Section getSection(SectionType type) {
        return sectionsByType.get(type);
    }

    public void setSection(SectionType type, Section section) {
        sectionsByType.put(type, section);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName);
    }

    @Override
    public String toString() {
        return "Resume{uuid='" + uuid + '\'' + ", fullName='" + fullName + '\'' + ", contactsByType=" + contactsByType + ", sectionsByType=" + sectionsByType + '}';
    }
}
