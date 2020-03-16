package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.ThrowingConsumer;
import ru.javawebinar.basejava.util.ThrowingRunnable;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import static ru.javawebinar.basejava.util.ConverterUtils.setToMap;
import static ru.javawebinar.basejava.util.DefaultUtils.getValueOrDefault;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void write(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            writeContacts(resume, dos);
            writeSections(resume, dos);
        }
    }

    @Override
    public Resume read(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(resume, dis);
            readSections(resume, dis);
            return resume;
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dos) throws IOException {
        Set<Map.Entry<ContactType, String>> contactsSet = resume.getContacts().entrySet();
        writeCollection(contactsSet, dos, (contactEntry) -> {
            dos.writeUTF(contactEntry.getKey().name());
            dos.writeUTF(contactEntry.getValue());
        });
    }

    private Resume readContacts(Resume resume, DataInputStream dis) throws IOException {
        Set<Map.Entry<ContactType, String>> contactsSet = new HashSet<>();
        readCollection(contactsSet, dis, () -> new AbstractMap.SimpleEntry<>(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
        resume.setContacts(setToMap(contactsSet));
        return resume;
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        Set<Map.Entry<SectionType, Section>> sectionsSet = resume.getSections().entrySet();
        writeCollection(sectionsSet, dos, (entry) -> {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    dos.writeUTF(((TextSection) entry.getValue()).getText());
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> stringList = ((ListSection) entry.getValue()).getList();
                    writeCollection(stringList, dos, dos::writeUTF);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> orgList = ((OrganizationSection) entry.getValue()).getList();
                    writeCollection(orgList, dos, (org) -> {
                        dos.writeUTF(org.getCompanyName());
                        dos.writeUTF(getValueOrDefault(org.getUrl(), null, ""));
                        List<Organization.PositionDetails> positionDetailsList = org.getPositionDetailsList();
                        writeCollection(positionDetailsList, dos, (positionDetails) -> {
                            dos.writeUTF(positionDetails.getTitle());
                            dos.writeUTF(getValueOrDefault(positionDetails.getDescription(), null, ""));
                            dos.writeUTF(positionDetails.getStartDate().toString());
                            dos.writeUTF(positionDetails.getEndDate().toString());
                        });
                    });
                    break;
            }
        });
    }

    private Resume readSections(Resume resume, DataInputStream dis) throws IOException {
        Set<Map.Entry<SectionType, Section>> sectionsSet = new HashSet<>();
        readCollection(sectionsSet, dis, () -> {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            Section section;
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(dis.readUTF());
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> stringList = new ArrayList<>();
                    readCollection(stringList, dis, dis::readUTF);
                    section = new ListSection(stringList);
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> organizationList = new ArrayList<>();
                    readCollection(organizationList, dis, () -> {
                        String url = dis.readUTF();
                        String companyName = dis.readUTF();
                        Organization organization = new Organization(getValueOrDefault(url, "", null),
                                                                     getValueOrDefault(companyName, "", null));
                        List<Organization.PositionDetails> positionDetailsList = new ArrayList<>();
                        readCollection(positionDetailsList, dis, () -> {
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            return new Organization.PositionDetails(getValueOrDefault(title, "", null),
                                                                    startDate,
                                                                    endDate,
                                                                    getValueOrDefault(description, "", null));
                        });
                        organization.setPositionDetailsList(positionDetailsList);
                        return organization;
                    });
                    section = new OrganizationSection(organizationList);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType.name());
            }
            return new AbstractMap.SimpleEntry<>(sectionType, section);
        });
        resume.setSections(setToMap(sectionsSet));
        return resume;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, ThrowingConsumer<T> writeElement) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            writeElement.accept(t);
        }
    }

    private <R> Collection<R> readCollection(Collection<R> collection, DataInputStream dis, ThrowingRunnable<R> readElement) throws IOException {
        int collectionSize = dis.readInt();
        for (int i = 0; i < collectionSize; i++) {
            collection.add(readElement.run());
        }
        return collection;
    }
}
