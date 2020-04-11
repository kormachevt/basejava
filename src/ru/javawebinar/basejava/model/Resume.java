package ru.javawebinar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;

    private String uuid;
    private String fullName;
    private Map<ContactType, String> contactsByType = new EnumMap<>(ContactType.class);
    private Map<SectionType, Section> sectionsByType = new EnumMap<>(SectionType.class);

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid);
        this.fullName = Objects.requireNonNull(fullName);
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume() {
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contactsByType;
    }

    public void addContact(ContactType type, String value) {
        contactsByType.put(type, value);
    }

    public void setContacts(Map<ContactType, String> contactsByType) {
        this.contactsByType = contactsByType;
    }

    public void setSection(SectionType type, Section section) {
        sectionsByType.put(type, section);
    }

    public void setSections(Map<SectionType, Section> sectionsByType) {
        this.sectionsByType = sectionsByType;
    }

    public Map<SectionType, Section> getSections() {
        return sectionsByType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) &&
                Objects.equals(fullName, resume.fullName) &&
                Objects.equals(contactsByType, resume.contactsByType) &&
                Objects.equals(sectionsByType, resume.sectionsByType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contactsByType, sectionsByType);
    }

    @Override
    public String toString() {
        return "Resume{uuid='" + uuid + '\'' + ", fullName='" + fullName + '\'' + ", contactsByType=" + contactsByType + ", sectionsByType=" + sectionsByType + '}';
    }
}
