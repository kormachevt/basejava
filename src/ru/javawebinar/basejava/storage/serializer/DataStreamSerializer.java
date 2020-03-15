package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.ThrowingBiConsumer;
import ru.javawebinar.basejava.util.ThrowingFunction;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
        writeCollection(contactsSet, dos, (contactEntry, dos1) -> {
            dos.writeUTF(contactEntry.getKey().name());
            dos.writeUTF(contactEntry.getValue());
        });
    }

    private Resume readContacts(Resume resume, DataInputStream dis) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return resume;
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        Set<Map.Entry<SectionType, Section>> sectionsSet = resume.getSections().entrySet();
        writeCollection(sectionsSet, dos, (entry, dos1) -> {
            dos.writeUTF(entry.getKey().name());
            switch (entry.getKey()) {
                case PERSONAL:
                case OBJECTIVE:
                    dos.writeUTF(((TextSection) entry.getValue()).getText());
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    List<String> stringList = ((ListSection) entry.getValue()).getList();
                    writeCollection(stringList, dos, (str, dos2) -> dos.writeUTF(str));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    List<Organization> orgList = ((OrganizationSection) entry.getValue()).getList();
                    writeCollection(orgList, dos, (org, dos3) -> {
                        dos.writeUTF(org.getCompanyName());
                        dos.writeUTF(getValueOrDefault(org.getUrl(), null, ""));
                        List<Organization.PositionDetails> positionDetailsList = org.getPositionDetailsList();
                        writeCollection(positionDetailsList, dos, (positionDetails, dos4) -> {
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
        int sectionsSize = dis.readInt();
        for (int i = 0; i < sectionsSize; i++) {
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            Section section;
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    section = new TextSection(dis.readUTF());
                    break;
                case ACHIEVEMENTS:
                case QUALIFICATIONS:
                    section = new ListSection(readList(dis, DataInput::readUTF));
                    break;
                case EDUCATION:
                case EXPERIENCE:
                    section = new OrganizationSection(readList(dis, (dis2) -> {
                        String url = dis.readUTF();
                        String companyName = dis.readUTF();
                        Organization organization = new Organization(getValueOrDefault(url, "", null),
                                                                     getValueOrDefault(companyName, "", null));
                        organization.setPositionDetailsList(readList(dis, (dis3) -> {
                            String title = dis.readUTF();
                            String description = dis.readUTF();
                            LocalDate startDate = LocalDate.parse(dis.readUTF());
                            LocalDate endDate = LocalDate.parse(dis.readUTF());
                            return new Organization.PositionDetails(getValueOrDefault(title, "", null),
                                                                    startDate,
                                                                    endDate,
                                                                    getValueOrDefault(description, "", null));
                        }));
                        return organization;
                    }));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionType.name());
            }
            resume.setSection(sectionType, section);
        }
        return resume;
    }

    private <T> void writeCollection(Collection<T> collection, DataOutputStream dos, ThrowingBiConsumer<T> printElement) throws IOException {
        dos.writeInt(collection.size());
        for (T t : collection) {
            printElement.accept(t, dos);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ThrowingFunction<T> readListElement) throws IOException {
        int listSize = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(readListElement.apply(dis));
        }
        return list;
    }
}
