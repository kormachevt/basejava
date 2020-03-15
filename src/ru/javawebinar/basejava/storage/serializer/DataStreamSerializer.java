package ru.javawebinar.basejava.storage.serializer;

import ru.javawebinar.basejava.model.*;
import ru.javawebinar.basejava.util.ThrowingBiConsumer;
import ru.javawebinar.basejava.util.ThrowingFunction;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Map<ContactType, String> contacts = resume.getContacts();
        dos.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            dos.writeUTF(entry.getKey().name());
            dos.writeUTF(entry.getValue());
        }
    }

    private Resume readContacts(Resume resume, DataInputStream dis) throws IOException {
        int contactsSize = dis.readInt();
        for (int i = 0; i < contactsSize; i++) {
            resume.setContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
        }
        return resume;
    }

    private void writeSections(Resume resume, DataOutputStream dos) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        dos.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            String sectionClassName = entry.getValue().getClass().getSimpleName();
            dos.writeUTF(sectionClassName);
            dos.writeUTF(entry.getKey().name());
            switch (sectionClassName) {
                case "TextSection":
                    dos.writeUTF(((TextSection) entry.getValue()).getText());
                    break;
                case "ListSection":
                    List<String> stringList = ((ListSection) entry.getValue()).getList();
                    writeList(stringList, dos, writeString);
                    break;
                case "OrganizationSection":
                    List<Organization> orgList = ((OrganizationSection) entry.getValue()).getList();
                    writeList(orgList, dos, writeOrganization);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionClassName);
            }
        }
    }

    private Resume readSections(Resume resume, DataInputStream dis) throws IOException {
        int sectionsSize = dis.readInt();
        for (int i = 0; i < sectionsSize; i++) {
            String sectionClassName = dis.readUTF();
            SectionType sectionType = SectionType.valueOf(dis.readUTF());
            Section section;
            switch (sectionClassName) {
                case "TextSection":
                    section = new TextSection(dis.readUTF());
                    break;
                case "ListSection":
                    section = new ListSection(readList(dis, readString));
                    break;
                case "OrganizationSection":
                    section = new OrganizationSection(readList(dis, readOrganization));
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + sectionClassName);
            }
            resume.setSection(sectionType, section);
        }
        return resume;
    }

    private <T> void writeList(List<T> list, DataOutputStream dos, ThrowingBiConsumer<T, DataOutputStream, IOException> printListElement) throws IOException {
        dos.writeInt(list.size());
        for (T t : list) {
            printListElement.accept(t, dos);
        }
    }

    private <T> List<T> readList(DataInputStream dis, ThrowingFunction<DataInputStream, T, IOException> writeListElement) throws IOException {
        int listSize = dis.readInt();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(writeListElement.apply(dis));
        }
        return list;
    }


    ThrowingBiConsumer<String, DataOutputStream, IOException> writeString = (str, dos) -> dos.writeUTF(str);
    ThrowingFunction<DataInputStream, String, IOException> readString = DataInput::readUTF;

    ThrowingBiConsumer<Organization.PositionDetails, DataOutputStream, IOException> writePositionDetails = (positionDetails, dos) -> {
        dos.writeUTF(positionDetails.getTitle());
        dos.writeUTF(getValueOrDefault(positionDetails.getDescription(), null, ""));
        dos.writeUTF(positionDetails.getStartDate().toString());
        dos.writeUTF(positionDetails.getEndDate().toString());
    };
    ThrowingFunction<DataInputStream, Organization.PositionDetails, IOException> readPositionDetails = (dis) -> {
        String title = dis.readUTF();
        String description = dis.readUTF();
        LocalDate startDate = LocalDate.parse(dis.readUTF());
        LocalDate endDate = LocalDate.parse(dis.readUTF());
        return new Organization.PositionDetails(getValueOrDefault(title, "", null),
                                                startDate,
                                                endDate,
                                                getValueOrDefault(description, "", null));
    };

    ThrowingBiConsumer<Organization, DataOutputStream, IOException> writeOrganization = (org, dos) -> {
        dos.writeUTF(org.getCompanyName());
        dos.writeUTF(getValueOrDefault(org.getUrl(), null, ""));
        List<Organization.PositionDetails> positionDetailsList = org.getPositionDetailsList();
        writeList(positionDetailsList, dos, writePositionDetails);
    };
    ThrowingFunction<DataInputStream, Organization, IOException> readOrganization = (dis) -> {
        String url = dis.readUTF();
        String companyName = dis.readUTF();
        Organization organization = new Organization(getValueOrDefault(url, "", null),
                                                     getValueOrDefault(companyName, "", null));
        organization.setPositionDetailsList(readList(dis, readPositionDetails));
        return organization;
    };
}
